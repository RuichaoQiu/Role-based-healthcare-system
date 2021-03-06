package edu.ncsu.csc.itrust.action;


import edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.exception.DBException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EpidemicCheckerAction {
	private DiagnosesDAO dDao = null;
	private int[] weekMap = new int[52]	;
	private int yearsCounted = 0;
	
	
	public EpidemicCheckerAction (DiagnosesDAO dao)
	{
		dDao = dao;
	}
	
	/**
	 * Method that checks the diagnoses data base and determines whether there is a malaria epidemic happening 
	 * on the day provided.
	 * 
	 * @param zip_code - zip code used to check a certain region for an epidemic
	 * @param today - The date used to check for an epidemic
	 * @param code - ICD code used to check for the epidemic
	 * @return - boolean representing whether there is an epidemic or not
	 * @throws DBException
	 */
	public boolean checkInfluenzaEpidemic (String zip_code, Date today, String code) throws DBException
	{
		boolean epidemic = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
    
		
		// Find the beginning and end dates of the two previous weeks
		cal.add(Calendar.DAY_OF_YEAR, -14);
		Date beginningOfFirstWeek = cal.getTime();

		cal.add(Calendar.DAY_OF_YEAR, 6);
		Date endOfFirstWeek = cal.getTime();
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date beginningOfSecondWeek = cal.getTime();
		
		cal.add(Calendar.DAY_OF_YEAR, 6);
		Date endOfSecondWeek = cal.getTime();
		
		// Check the first week
		double threshold = calculateInfluenzaThreshold(beginningOfFirstWeek);
		DiagnosisStatisticsBean dsBean = dDao.getDiagnosisCounts(code, zip_code, beginningOfFirstWeek, endOfFirstWeek);
		
		if (dsBean.getRegionStats() > threshold)
		{
			// If the first week exceeds the threshold, then check the second week
			threshold = calculateInfluenzaThreshold(beginningOfSecondWeek);
			dsBean = dDao.getDiagnosisCounts(code, zip_code, beginningOfSecondWeek, endOfSecondWeek);
			
			epidemic = dsBean.getRegionStats() > threshold;
		}
		return epidemic;
	}
	
	private double calculateInfluenzaThreshold (Date firstDayOfWeek)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(firstDayOfWeek);
		int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
		return 5.34 + 0.271 * weekNumber + 3.45 * Math.sin(2 * Math.PI * weekNumber / 52.0)
                + 8.41 * Math.cos(2 * Math.PI * weekNumber / 52.0);
	}
	
	/**
	 * Method that checks the diagnoses data base and determines whether there is a malaria epidemic happening 
	 * on the day provided.
	 * 
	 * @param zip_code - Zip code used to check for regional stats
	 * @param threshold - Threshold provided by user to check against diagnoses data
	 * @param today - Current day's date set by the user
	 * @param code - ICD code we are checking
	 * @return - boolean representing whether there is an epidemic or not
	 * @throws DBException
	 */
	public boolean checkMalariaEpidemic (String zip_code, int threshold, Date today, String code) throws DBException
	{
		boolean epidemic = false;
		Calendar cal = Calendar.getInstance();
		
		
		Date startingDate = dDao.findEarliestIncident(code);
		if (startingDate != null)
		{
			//First, find the starting year
			cal.setTime(startingDate);
			int startingYear = cal.get(Calendar.YEAR);
			
			// Find the beginning and end dates of the two previous weeks
			cal.setTime(today);
	    
			cal.add(Calendar.DAY_OF_YEAR, -14);
			Date beginningOfFirstWeek = cal.getTime();
	
			cal.add(Calendar.DAY_OF_YEAR, 6);
			Date endOfFirstWeek = cal.getTime();
			
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date beginningOfSecondWeek = cal.getTime();
			
			cal.add(Calendar.DAY_OF_YEAR, 6);
			Date endOfSecondWeek = cal.getTime();
			
			// Check the average number of occurrences in previous years for the first week.
			DiagnosisStatisticsBean dsBean =  dDao.getDiagnosisCounts(code, zip_code, beginningOfFirstWeek, endOfFirstWeek);
			double firstWeekCases = dsBean.getRegionStats();
			double firstWeekAvg = calcluateMalariaOccurrenceAvg(beginningOfFirstWeek,endOfFirstWeek, startingYear, zip_code, code );
			
					
			if (passThreshold (firstWeekCases, firstWeekAvg, threshold))
			{
				dsBean =  dDao.getDiagnosisCounts(code, zip_code, beginningOfSecondWeek, endOfSecondWeek);
				double secondWeekCases = dsBean.getRegionStats();
				double secondWeekAvg = calcluateMalariaOccurrenceAvg(beginningOfSecondWeek,endOfSecondWeek, startingYear, zip_code, code );
				
				// Check the average number of occurrences in previous years for the first week.
				epidemic = passThreshold (secondWeekCases, secondWeekAvg, threshold);
			}

			
		
		}
		return epidemic;
	}

	private boolean passThreshold (double weekCases, double weekAvg, int threshold)
	{
		boolean noAvg = false;
		double firstWeekPerc = 0;
		if (weekAvg == 0 && weekCases != 0 )
		{
			noAvg = true;
		}
		else
		{
			firstWeekPerc = 100.0 * weekCases / weekAvg;
		}
		
		return noAvg || (firstWeekPerc > threshold);
	}
	
	/**
	 * Calculates the avg occurrence based on previous year data. Counts back from the given date
	 * 
	 * @param beginningOfWeek 
	 * @param endOfWeek
	 * @param cutoffYear - The year we should stop counting back from
	 * @param zip - zipcode
	 * @param code - ICD code of disease
	 * @return The average number of occurrences per year
	 * @throws DBException 
	 */
	private double calcluateMalariaOccurrenceAvg(Date beginningOfWeek, Date endOfWeek, int cutoffYear, String zip, String code ) throws DBException
	{
		double sum = 0;
		double yearCount = 0;
		Date first = beginningOfWeek;
		Date second = endOfWeek;
		
		Calendar cal = Calendar.getInstance();
		
		// Start a year before the give dates
		cal.setTime(first);
		cal.add(Calendar.YEAR, -1);
		first = cal.getTime();
		
		cal.setTime(second);
		cal.add(Calendar.YEAR, -1);
		second = cal.getTime();
		
		cal.setTime(first);
		while (cal.get(Calendar.YEAR) >= cutoffYear)
		{
			DiagnosisStatisticsBean dsBean =  dDao.getDiagnosisCounts(code, zip, first, second);
			sum += dsBean.getRegionStats();
			
			//Subtract a year from both dates
			cal.setTime(first);
			cal.add(Calendar.YEAR, -1);
			first = cal.getTime();
			
			cal.setTime(second);
			cal.add(Calendar.YEAR, -1);
			second = cal.getTime();
			
			// Note that we've counted a year
			yearCount++;
		}
		
		// Check for zero years counted
		if (0 == yearCount){
			return 0;
		}
		// Otherwise return the total number of cases divided by the total years counted
		else {
			return sum/yearCount;
		}
	}


}
 