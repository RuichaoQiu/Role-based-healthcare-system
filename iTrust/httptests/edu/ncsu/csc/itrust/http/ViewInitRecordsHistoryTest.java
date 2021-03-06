package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * ViewHealthRecordsHistoryTest
 */
public class ViewInitRecordsHistoryTest extends iTrustHTTPTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testViewHealthMetricsByHCP
	 * @throws Exception
	 */
	public void testShowInitRecords() throws Exception{
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Initialization Records link
		wr = wr.getLinkWith("Initialization Records").click();
		//Search for Random Person (MID 1)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		//Click on first MID button
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		//Verify Init Records Information page
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());

		//Verify init  record table displays
		WebTable hrTable = wr.getTableWithID("InitRecordsTable");
		//Verify the table has the provided information: Header, field descriptions, 3 rows of health records
		assertEquals(3, hrTable.getRowCount());
		//Verify table contents
		
		//Row 1 values
		assertEquals("1", hrTable.getCellAsText(2, 0));			
	}
	
	public void testEmptyInitRecords() throws Exception{
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Initialization Records link
		wr = wr.getLinkWith("Initialization Records").click();
		//Search for Programmer Baby (MID 5)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "5");
		//Click on first MID button
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		//Verify Init Records Information page
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());
		//Verify No record showing
		assertTrue(wr.getText().contains("No init records available"));
				
	}
	
	public void testMaleCheckInitRecords() throws Exception{
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Initialization Records link
		wr = wr.getLinkWith("Initialization Records").click();
		//Search for Programmer Andy (MID 2)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		//Click on first MID button
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		//Verify Init Records Information page
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());
		//Verify not male patient does not have init record.
		assertTrue(wr.getText().contains("The patient is not eligible for obstetric care."));
				
	}
	
	public void testAddInitRecords() throws Exception{
		//Login as HCP with OGBYN specialty
		WebConversation wc = login("9800000022", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9800000022L, 0L, "");
		
		//Click Initialization Records link
		wr = wr.getLinkWith("Initialization Records").click();
		//Search for Random Person (MID 1)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "21");
		//Click on first MID button
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		//Verify Init Records Information page
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());

		//Click on Create Record button
		wr = wr.getLinkWith("Create New Record").click();
		wr = wc.getCurrentPage();
		//Verify Add Init Records page
		assertEquals(ADDRESS + "auth/hcp-uap/addInitRecord.jsp", wr.getURL().toString());
		
		//Add values into form
		WebForm createForm = wr.getForms()[0];
		createForm.getScriptableObject().setParameterValue("LMP", "11/05/2014");
		createForm.getScriptableObject().setParameterValue("notes", "Here are some notes about the patient.");
		createForm.getSubmitButtons()[0].click();

		//Verify correct page
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());
		
		//Verify the record was created
		WebTable hrTable = wr.getTableWithID("InitRecordsTable");
		assertEquals(3, hrTable.getRowCount());
		assertEquals("21", hrTable.getCellAsText(2, 0));
		
	}
	
	public void testAddInitRecordsWithPastPregnancy() throws Exception{
		//Login as HCP with OGBYN specialty
		WebConversation wc = login("9800000022", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9800000022L, 0L, "");
		
		//Click Initialization Records link
		wr = wr.getLinkWith("Initialization Records").click();
		//Search for Random Person (MID 1)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "21");
		//Click on first MID button
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		//Verify Init Records Information page
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());

		//Click on Create Record button
		wr = wr.getLinkWith("Create New Record").click();
		wr = wc.getCurrentPage();
		//Verify Add Init Records page
		assertEquals(ADDRESS + "auth/hcp-uap/addInitRecord.jsp", wr.getURL().toString());
		
		//Add values into form
		WebForm createForm = wr.getForms()[0];
		createForm.getScriptableObject().setParameterValue("LMP", "11/05/2014");
		createForm.getScriptableObject().setParameterValue("notes", "Here are some notes about the patient.");
		
		//Add values regarding prior pregnancies
		createForm.getScriptableObject().setParameterValue("addPriorPreg", "on");
		createForm.getScriptableObject().setParameterValue("yearConceived", "2011");
		createForm.getScriptableObject().setParameterValue("weeksPreg", "10");
		createForm.getScriptableObject().setParameterValue("daysPreg", "4");
		createForm.getScriptableObject().setParameterValue("laborHours", "5");
		createForm.getSubmitButtons()[0].click();

		//Verify correct page
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/InitRecords.jsp", wr.getURL().toString());
		
		//Verify the record was created
		WebTable hrTable = wr.getTableWithID("InitRecordsTable");
		assertEquals(3, hrTable.getRowCount());
		assertEquals("21", hrTable.getCellAsText(2, 0));
		
	}

}