package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObstericsBean {
	private long visitID = 0;
	private String visitDate = "0000-00-00";
	private String weeksPreg = "";
	private String daysPreg = "";
	private String SBloodPressure = "";
	private String DBloodPressure = "";
	private String weight = "";
	private String FHR = "";
	private String FHU = "";
	private long PatientID = 0;
	
	public ObstericsBean() {
	}
	
	/**
	 * For use ONLY by DAOs
	 * setters and getters method
	 * 
	 * @param visitID
	 */
	public ObstericsBean(long visitID) {
		this.visitID = visitID;
	}

	/**
	 * Returns the office visit's ID assigned to the bean.
	 * 
	 * @return The ID assigned ot the bean.
	 */
	public long getID() {
		return visitID;
	}
	
	/**
	 * Returns a Date object containing the bean's office visit date.
	 * 
	 * @return The Date object containing the date of the office visit. 
	 */
	public Date getvisitDate(){
		Date d = null; 
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(visitDate);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return d;
	}
	
	/**
	 * Returns a String containing the bean's assigned visit date.
	 * 
	 * @return A String with the bean's assigned office visit date.
	 */
	public String getvisitDateStr(){
		return visitDate;
	}
	
	/**
	 * Sets the bean's office visit date.
	 * 
	 * @param visitDate 
	 */
	public void setvisitDate(String visitDate){
		this.visitDate = visitDate;
	}

	/**
	 * Returns a String containing the number of weeks the patient has been pregnant as of the office visit.
	 * 
	 * @return The number of weeks the bean's patient has been pregnant.
	 */
	public String getWeeksPreg() {
		return weeksPreg;
	}

	/**
	 * Sets the number of weeks pregnant.
	 * 
	 * @param weeksPreg The value to which we want to set the number of weeks pregnant.
	 */
	public void setWeeksPreg(String weeksPreg) {
		this.weeksPreg = weeksPreg;
	}

	public String getDaysPreg() {
		return daysPreg;
	}

	public void setDaysPreg(String daysPreg) {
		this.daysPreg = daysPreg;
	}

	public String getSBloodPressure() {
		return SBloodPressure;
	}

	public void setSBloodPressure(String SBloodPressure) {
		this.SBloodPressure = SBloodPressure;
	}
	
	public String getDBloodPressure() {
		return DBloodPressure;
	}

	public void setDBloodPressure(String DBloodPressure) {
		this.DBloodPressure = DBloodPressure;
	}


	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFHR() {
		return FHR;
	}

	public void setFHR(String fHR) {
		this.FHR = fHR;
	}

	public String getFHU() {
		return FHU;
	}

	public void setFHU(String FHU) {
		this.FHU = FHU;
	}
	
	public long getPatientID(){
		return PatientID;
	}
	
	public void setPatientID(long PatientID){
		this.PatientID = PatientID;
	}
	
}
