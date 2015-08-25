package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.InitRecord;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.InitRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewInitRecordsHistoryActionTest extends TestCase{
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewInitRecordsHistoryAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		//action = new ViewInitRecordsHistoryAction(factory, "102", 1L);
	}
	
	public void testGetPatientID() throws Exception{			
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		long pid = action.getPatientID();
		assertEquals(1, pid);		
	}
	
	public void testGetPatientName() throws Exception{
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		String patientName = action.getPatientName();
		assertEquals("Random Person", patientName);		
		
	}
	
	public void testgetPatientLastName() throws Exception{
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		String patientName = action.getPatientLastName();
		assertEquals("Person", patientName);		
		
	}
	
	public void testgetPatientFirstName() throws Exception{
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		String patientName = action.getPatientFirstName();
		assertEquals("Random", patientName);		
		
	}
	
	public void testgetPatientGender() throws Exception{
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		String gender = action.getPatientGender();
		assertEquals("Female", gender);		
		
	}
	
	public void testgetAllPatientInitRecords() throws Exception{
		action = new ViewInitRecordsHistoryAction(factory, "1", 1L);
		List<InitRecord> testIR = action.getAllPatientInitRecords();
		assertEquals(1, testIR.get(0).getMid());		
		assertEquals("Person", testIR.get(0).getLastname());
		assertEquals("Random", testIR.get(0).getFirstname());
		assertEquals("10/16/2014", testIR.get(0).getLMPstr());
	}
	
	public void testInitRecordGetAndSet() throws Exception{
		InitRecordsDAO dao = new InitRecordsDAO(factory);
		InitRecord record = new InitRecord();
		record.setFirstname("Princess");
		record.setLastname("Peach");
		record.setMid(21);
		record.setLMP("2014-05-04");
		record.setEDD("2014-12-14");
		record.setNotes("Some Notes");
		record.setWeeksPreg("4 Week(s) 1 Day(s)");
		dao.add(record);
		
		String firstname = "Princess";
		String lastname = "Peach";
		int mid = 21;
		Date LMP_date = new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-04");
		Date EDD_date = new SimpleDateFormat("yyyy-MM-dd").parse("2014-12-14");
		String LMP = "2014-05-04";
		String EDD = "2014-12-14";
		String notes = "Some Notes";
		String weekspreg = "4 Week(s) 1 Day(s)";
		
		assertEquals(firstname, record.getFirstname());
		assertEquals(lastname, record.getLastname());
		assertEquals(mid, record.getMid());
		assertEquals(LMP_date, record.getLMP());
		assertEquals(LMP, record.getLMPstr());
		assertEquals(EDD_date, record.getEDD());
		assertEquals(EDD, record.getEDDstr());
		assertEquals(notes, record.getNotes());
		assertEquals(weekspreg, record.getWeeksPreg());
		
	}
	
	public void testAddInitRecord() throws Exception{
		InitRecordsDAO dao = new InitRecordsDAO(factory);
		InitRecord goodRecord = new InitRecord();
		
		goodRecord.setFirstname("Princess");
		goodRecord.setLastname("Peach");
		goodRecord.setMid(21);
		goodRecord.setLMP("2014-05-04");
		goodRecord.setEDD("2014-12-14");
		goodRecord.setNotes("Some Notes");
		goodRecord.setWeeksPreg("4 Week(s) 1 Day(s)");
		
		assertEquals(true, dao.add(goodRecord));
	}
}




