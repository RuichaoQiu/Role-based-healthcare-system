<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Reminder Message Outbox";


%>

<%@include file="/header.jsp" %>

<div align=center>
	<h2>Visit Reminder Outbox</h2>
	<a href="/iTrust/auth/admin/sendReminders.jsp">Send Reminders</a><br /><br />
<%
	long MID = 9000000009L;
loggingAction.logEvent(TransactionType.OUTBOX_VIEW, MID, MID, "");
	ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO,MID);
	List<MessageBean> messages = null;
	
		messages = action.getAllMySentMessages();
	session.setAttribute("messages", messages);
	if (messages.size() > 0) { %>
	<form method="post" action="messageOutbox.jsp">	
	<table>
	
	</table>
	</form>
	<br />
	<table class="fancyTable">
		<tr>
			<th>To</th>
			<th>Subject</th>
			<th>Received</th>
			<th></th>
		</tr>
<%		int index = 0; %>
<%		for(MessageBean message : messages) { %>
		<tr <%=(index%2 == 1)?"class=\"alt\"":"" %>>
			<td><%= StringEscapeUtils.escapeHtml("" + ( action.getName(message.getTo()) )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( message.getSubject() )) %></td>
			<td><%= StringEscapeUtils.escapeHtml("" + ( message.getSentDate() )) %></td>
			<td><a href="viewReminder.jsp?msg=<%= StringEscapeUtils.escapeHtml("" + ( index )) %>">Read</a></td>
		</tr>
<%			index ++; %>
<%		} %>
	</table>
<%	} else { %>
	<div>
		<i>You have no messages</i>
	</div>
<%	} %>	
	<br />
</div>

<%@include file="/footer.jsp" %>
