package edu.ncsu.csc.itrust.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EpidemicCheckerActionTest extends TestCase {
	
	private TestDataGenerator gen;
	private EpidemicCheckerAction eChecker;
	private final String MALARIA_ICD = "84.50";
	private final String INFLUENZA_ICD = "487.00";
	
	protected void setUp() throws Exception {	
			
			//Set up user database
			gen = new TestDataGenerator();
			gen.clearAllTables();
			gen.standardData();
			gen.patient_hcp_vists();
			gen.hcp_diagnosis_data2();
			
			
			eChecker = new EpidemicCheckerAction(TestDAOFactory.getTestInstance().getDiagnosesDAO());
		}
	
	public void testMalariaEpidemicPositiveCheck() throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("15/01/2008");
		
		assertTrue(eChecker.checkMalariaEpidemic("27689", 50, date, MALARIA_ICD));
	}
	
	public void testMalariaEpidemicNegativeCheck() throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("20/03/2008");
		
		assertFalse(eChecker.checkMalariaEpidemic("27689", 50, date, MALARIA_ICD));
	}
	
	public void testMalariaEpidemicCheckDivideByZero() throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("02/08/2008");
		
		assertFalse(eChecker.checkMalariaEpidemic("27689", 50, date, MALARIA_ICD));
	}
	
	public void testInfleunzaEpidemicPositiveCheck () throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("08/08/2011");
		
		assertTrue(eChecker.checkInfluenzaEpidemic ("27689",date, INFLUENZA_ICD));
	}
	public void testInfleunzaEpidemicNegativeCheckOneWeekRight () throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("08/01/2011");
		
		assertFalse(eChecker.checkInfluenzaEpidemic ("27689",date, INFLUENZA_ICD));
	}
	public void testInfleunzaEpidemicNegativeCheck () throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("01/01/2011");
		
		assertFalse(eChecker.checkInfluenzaEpidemic ("27689",date, INFLUENZA_ICD));
	}

}
