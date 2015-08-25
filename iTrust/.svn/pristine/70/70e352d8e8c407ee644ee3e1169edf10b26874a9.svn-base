package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;
public class ViewDeathStatsTest extends iTrustHTTPTest{

	@Override
	protected void setUp() throws Exception{
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();		
	}
	public void testViewDeaths() throws Exception{
		WebConversation wc = login("9000000003", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		wr = wr.getLinkWith("View cause-of-death Reports").click();
		assertEquals("iTrust - View Cause-of-death reports", wr.getTitle());
		
		WebForm	form = wr.getFormWithID("formMain");
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("The top two causes of death are "));
	}
}
