package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Message Filter HTTP Tests
 */
public class MessageFilterTest extends iTrustHTTPTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testToggleMessageFilter
	 * @throws Exception
	 */
	public void testToggleMessageFilter() throws Exception{
	
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals(ADDRESS + "auth/hcp/home.jsp", wr.getURL().toString());

		//Click Inbox link
		wr = wr.getLinkWith("Message Inbox").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp", wr.getURL().toString());
		
		//Click Toggle Message Filter link
		wr = wr.getLinkWith("Turn Filter On").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Click Toggle Message Filter link
		wr = wr.getLinkWith("Turn Filter Off").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=false", wr.getURL().toString());
	}
	
	/**
	 * testFilterCancel
	 * @throws Exception
	 */
	public void testFilterCancel() throws Exception{
	
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals(ADDRESS + "auth/hcp/home.jsp", wr.getURL().toString());

		//Click Inbox link
		wr = wr.getLinkWith("Message Inbox").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp", wr.getURL().toString());
		
		//Click Toggle Message Filter link
		wr = wr.getLinkWith("Turn Filter On").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Enter into the field
		WebForm wf = wr.getFormWithID("messageFilterForm");
		wf.getScriptableObject().setParameterValue("subject", "Office Visit");
		wf.getScriptableObject().setParameterValue("buttonClicked", "cancelButton");
		
		//Click Cancel Button
		wf.submit();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Check results
		WebTable messagesTable = wr.getTableWithID("mailbox");
		assertEquals(15, messagesTable.getRowCount());
	}
	
	/**
	 * testFilterTestSearch
	 * @throws Exception
	 */
	public void testFilterTestSearch() throws Exception{
	
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals(ADDRESS + "auth/hcp/home.jsp", wr.getURL().toString());

		//Click Inbox link
		wr = wr.getLinkWith("Message Inbox").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp", wr.getURL().toString());
		
		//Click Toggle Message Filter link
		wr = wr.getLinkWith("Turn Filter On").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Enter into the field
		WebForm wf = wr.getFormWithID("messageFilterForm");
		wf.getScriptableObject().setParameterValue("subject", "Office Visit");
		wf.getScriptableObject().setParameterValue("buttonClicked", "testButton");
		
		//Click Cancel Button
		wf.submit();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Check results
		WebTable messagesTable = wr.getTableWithID("mailbox");
		assertEquals(2, messagesTable.getRowCount());
		
		//Check parameters still there
		WebForm wf2 = wr.getFormWithID("messageFilterForm");
		assertEquals(wf2.getParameterValue("subject"), "Office Visit");
	}
	
	/**
	 * testFilterSave
	 * @throws Exception
	 */
	public void testFilterSave() throws Exception{
	
		//Login as HCP Doctor Kelly
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals(ADDRESS + "auth/hcp/home.jsp", wr.getURL().toString());

		//Click Inbox link
		wr = wr.getLinkWith("Message Inbox").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp", wr.getURL().toString());
		
		//Click Toggle Message Filter link
		wr = wr.getLinkWith("Turn Filter On").click();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Enter into the field
		WebForm wf = wr.getFormWithID("messageFilterForm");
		wf.getScriptableObject().setParameterValue("subject", "Office Visit");
		wf.getScriptableObject().setParameterValue("buttonClicked", "saveButton");
		
		//Click Cancel Button
		wf.submit();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-patient/messageInbox.jsp?filter=true", wr.getURL().toString());
		
		//Check results
		WebTable messagesTable = wr.getTableWithID("mailbox");
		assertEquals(2, messagesTable.getRowCount());
		
		//Check parameters still there
		WebForm wf2 = wr.getFormWithID("messageFilterForm");
		assertEquals(wf2.getParameterValue("subject"), "Office Visit");
		
		//Click Cancel and check still there
		wf.getScriptableObject().setParameterValue("subject", "");
		wf.getScriptableObject().setParameterValue("buttonClicked", "cancelButton");
		wf.submit();
		wr = wc.getCurrentPage();
		
		//Check results
		WebTable messagesTable2 = wr.getTableWithID("mailbox");
		assertEquals(2, messagesTable.getRowCount());
				
		//Check parameters still there
		WebForm wf3 = wr.getFormWithID("messageFilterForm");
		assertEquals(wf3.getParameterValue("subject"), "Office Visit");
	}
}