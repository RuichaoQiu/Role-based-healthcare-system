package edu.ncsu.csc.itrust.http;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;
public class ViewRemindersOutboxTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	public void testNavigate() throws Exception {
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		// click on Send Reminders
		wr = wr.getLinkWith("View Reminders Outbox").click();
		// view the inbox
		assertEquals("iTrust - View Reminder Message Outbox", wr.getTitle());
		wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Send Reminders").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Send Reminders", wr.getTitle());

	}

}
