package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

/**
 * 
 * @author Suchat Jiranuntarat
 * The following is the HttpTest for requestBiosurveillance.jsp
 * 
 */

//************************************Test Cases:********************************************
	/*
	 * 1) HCP performs an analysis on Malaria (Both Yes and No)
	 * 2) HCP performs an analysis on Influenza
	 * 3) HCP performs an analysis on Recent Trends
	 * 4) HCP enters invalid fields
	 */
	//*******************************************************************************************
public class RequestBiosurveillanceTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
		gen.patient88();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data2();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		gen.clearAllTables();
	}
	/*
	 * testHCPMalaria()
	 * HCP requests analysis on a Malaria diagnosis. One search returns a YES while the other returns NO
	 */
	public void testHCPMalaria() throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		WebForm form = wr.getForms()[0];
		form.setParameter("malariaDiagnosis", "50");
		form.setParameter("malariaZipCode", "27689");
		form.setParameter("malariaDate", "01/15/2008");
		form.setParameter("malariaThreshold", "50");
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Yes"));	
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		form = wr.getForms()[0];
		form.setParameter("malariaDiagnosis", "50");
		form.setParameter("malariaZipCode", "27689");
		form.setParameter("malariaDate", "08/02/2008");
		form.setParameter("malariaThreshold", "50");
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("No"));	
	}
	/*
	 * testHCPInfluenza()
	 * HCP requests analysis on a Influenza diagnosis. One search returns a YES while the other returns NO
	 */
	public void testHCPInfluenza() throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		WebForm form = wr.getForms()[0];
		form.setParameter("influenzaDiagnosis", "00");
		form.setParameter("influenzaZipCode", "27689");
		form.setParameter("influenzaDate", "08/08/2011");
		form.getSubmitButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Yes"));	
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		form = wr.getForms()[0];
		form.setParameter("influenzaDiagnosis", "00");
		form.setParameter("influenzaZipCode", "27689");
		form.setParameter("influenzaDate", "01/01/2011");
		form.getSubmitButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("No"));	
	}
	/*
	 * testHCPRecentTrends()
	 * HCP requests analysis on recent trends and view the graphs that it shows in the new page
	 */
	public void testHCPRecentTrends() throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		WebForm form = wr.getForms()[0];
		form.setParameter("recentDiagnosis", "72.00");
		form.setParameter("recentZipCode", "27700");
		form.setParameter("recentDate", "09/13/2011");
		form.getSubmitButtons()[2].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Recent Diagnosis Trends"));
		
	}
	/*
	 * testHCPInvalid()
	 * HCP inputs several invalid fields into the table and fails to run the analysis
	 */
	public void testHCPInvalid() throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		WebForm form = wr.getForms()[0];
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Please enter a valid"));
		
		wr = wr.getLinkWith("Request Biosurveillance").click();
		assertEquals(ADDRESS + "auth/hcp/requestBiosurveillance.jsp", wr.getURL().toString());
		
		wr = wc.getCurrentPage();
		assertTrue(wr.getTitle().contains("Request Biosurveillance"));
		
		form = wr.getForms()[0];
		form.setParameter("malariaDiagnosis", "02");
		form.setParameter("malariaZipCode", "abcde");
		form.setParameter("malariaDate", "02/02/2011");
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Please enter a valid Zip Code"));		
		
		
		
	}
	
}
