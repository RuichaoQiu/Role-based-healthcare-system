<%@page import="edu.ncsu.csc.itrust.action.SendVisitRemindersAction"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>


<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Send Reminders";
%>

<%@include file="/header.jsp" %>

<%

	SendVisitRemindersAction action = new SendVisitRemindersAction(prodDAO, loggedInMID.longValue());
	if("true".equals(request.getParameter("formIsFilled"))){
		try{
			action.sendVisitReminders(SendVisitRemindersAction.ReminderType.DIAGNOSED_CARE_NEEDERS, Integer.valueOf(request.getParameter("days")));
			%><span>Reminder-in-advance days set. Reminders within range sent. </span><%
		} catch(FormValidationException e){
			e.printHTML(pageContext.getOut());
		}
	}
	
%>
<br /><br />
<form action="sendReminders.jsp" method="post">
<input type=hidden name="formIsFilled" value="true">

Set reminder-in-advance days to 
<input name="days" value="1" size="3"> days (minimum 1).<br /><br />
<input type=submit value="Send Appointment Reminders">
</form>
<br /><br />
The value set determines how many days in advance a patient should be reminded of an upcoming appointment. 
<br /><br />

<%@include file="/footer.jsp" %>