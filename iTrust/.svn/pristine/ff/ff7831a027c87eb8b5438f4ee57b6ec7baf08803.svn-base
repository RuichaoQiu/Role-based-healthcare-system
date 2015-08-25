package edu.ncsu.csc.itrust.http;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;
public class SendRemindersTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	public void testSetValidDays() throws Exception {
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		// click on Send Reminders
		wr = wr.getLinkWith("Send Reminders").click();
		// set the Reminder day
		assertEquals("iTrust - Send Reminders", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.setParameter("days", "5");
		wr = form.submit(); 
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Reminder-in-advance days set. Changes will take place on re-authentication."));
	}
	public void testSetInvalidDays() throws Exception {
		// login admin
				WebConversation wc = login("9000000001", "pw");
				WebResponse wr = wc.getCurrentPage();
				assertEquals("iTrust - Admin Home", wr.getTitle());
				assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
				// click on Send Reminders
				wr = wr.getLinkWith("Send Reminders").click();
				// set the Reminder day
				assertEquals("iTrust - Send Reminders", wr.getTitle());
				WebForm form = wr.getForms()[0];
				form.setParameter("days", "0");
				wr = form.submit();
				wr = wc.getCurrentPage();
				assertTrue(wr.getText().contains("Must be a number greater than 0"));
	}
}
