<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.GetVisitRemindersAction"%>

<%@page import="edu.ncsu.csc.itrust.action.*"%>
<%@page import="edu.ncsu.csc.itrust.action.GetVisitRemindersAction.ReminderType"%>
<%@page import="edu.ncsu.csc.itrust.beans.VisitFlag"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.VisitReminderReturnForm"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Immunization Reminders";
%>


<%@include file="/header.jsp" %>

<%
loggingAction.logEvent(TransactionType.PATIENT_REMINDERS_VIEW, loggedInMID.longValue(), 0, "");

GetVisitRemindersAction action = new GetVisitRemindersAction(prodDAO, loggedInMID.longValue());
%>
<div align="center">
<h2>Patients Needing Immunizations</h2>



<%

	List<VisitReminderReturnForm> reminders = action.getVisitReminders(ReminderType.IMMUNIZATION_NEEDERS);
	for (VisitReminderReturnForm reminder : reminders) {
%>
		<table class="fTable">
			<tr>
				<th colspan="2">Patient Information</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Name:</td>
				<td >
					<a href="sendEmailNotification.jsp?mid=<%= StringEscapeUtils.escapeHtml("" + (reminder.getPatientID())) %>">
					<%= StringEscapeUtils.escapeHtml("" + (reminder.getFirstName()+" "+reminder.getLastName())) %>
					</a>
				</td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Phone Number:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (reminder.getPhoneNumber())) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Reasons:</td>
				<td>
<%
			for(VisitFlag vf : reminder.getVisitFlags()) {
%>
					<%= StringEscapeUtils.escapeHtml("" + (vf.getType() )) %>: &nbsp;&nbsp; <%= StringEscapeUtils.escapeHtml("" + (vf.getValue() )) %><br />
<%
			}
%>
			 	</td>
			</tr>
		</table>

			 	</td>
			</tr>
		</table>
-->
		<br />
<%
	} 
	SendVisitRemindersAction action2 = new SendVisitRemindersAction(prodDAO, loggedInMID.longValue());
	if("true".equals(request.getParameter("formIsFilled"))){
		try{
			action2.sendVisitReminders(SendVisitRemindersAction.ReminderType.IMMUNIZATION_NEEDERS, Integer.valueOf(request.getParameter("days")));
			%><span>Reminder-in-advance days set. Reminders within range sent. </span><%
		} catch(FormValidationException e){
			e.printHTML(pageContext.getOut());
		}
	}

%>
<form action="sendImmunizationReminders.jsp" method="post">
<input type=hidden name="formIsFilled" value="true">

Set reminder-in-advance days to 
<input name="days" value="1" size="3"> days (minimum 1).<br /><br />
<input type=submit value="Send Immunization Reminders">
</form>

</div>

<%@include file="/footer.jsp" %>
		