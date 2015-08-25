package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test all office visit document
 */
public class DocumentObstetricOfficeVisitTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	
	public void testDocumentObstetricOfficeVisit() throws Exception {
		// login UAP
		WebConversation wc = login("9800000022", "pw");
		WebResponse wr = wc.getCurrentPage();
		// click Document Office Visit
		wr = wr.getLinkWith("Document Obstetrics Office Visit").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("Weight", "100.3");
		form.setParameter("SBloodPressure", "85");
		form.setParameter("DBloodPressure", "130");
		form.setParameter("FHR", "122");
		form.setParameter("FHU", "155");
		
		form.getButtonWithID("update").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
	}

}
