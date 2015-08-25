<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewInitRecordsHistoryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.InitRecord"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Edit Basic Init Record";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="Basic Init History" />
<%
// Require a Patient ID first
String pidString = (String)session.getAttribute("pid");
if (pidString == null || 1 > pidString.length()) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewPriorPregnancies.jsp");
   	return;
}

//Create ViewInitialRecordsHistoryAction object to interact with patient's historical Initial metric history
ViewInitRecordsHistoryAction historyAction = new ViewInitRecordsHistoryAction(prodDAO,pidString,loggedInMID.longValue());


//Get the patient's name
String patientName = historyAction.getPatientName();
String gender = historyAction.getPatientGender();

//Get all of the patient's Initial records
List<InitRecord> records = historyAction.getAllPatientInitRecords();
//Save the list of initial records in the session
session.setAttribute("InitRecordsList", records);



%>

<br />
<div align=center>
	<table id="InitRecordsTable" align="center" class="fTable">

	<%
	if (!gender.equals("Female")){
		%>
			<p style="font-size:20px"><i>guys cannot have babies</i></p>
		<%
	}
	else
	if(records.isEmpty()){
		%>
			<p style="font-size:20px"><i>There are no prior pregnancices to show.</i></p>
		<%
	}
	else{
		if(!records.isEmpty()){
			%>
			<!-- 
			Create table containing obsterics data about the current patient
			 -->
			<tr>
				<th colspan="14" style="text-align: center;"><%= patientName %>'s Prior Pregnancies</th>
			</tr>
			<tr class = "subHeader">
				<td>Year of Conception</td>
				<td>Weeks Pregnant</td>
				<td>Days Pregnant</td>
				<td>Hours in Labor</td>
				<td>Delivery Type</td>
			</tr>
			<%
		}
		String priorPregs = "";
		String[] desires = null;
		for(InitRecord hr : records){
			priorPregs = hr.getPriorPregnancies();
			
				desires = priorPregs.split(":", -1);
			if(priorPregs != null){
				
				if (desires.length > 1){
				%><tr><%
				for(int i = 0; i < desires.length; i++) {
				%>
				<!-- 
				Retrieve relevant information for prior pregnancies, if any
				-->
					
					<td align=center><%= StringEscapeUtils.escapeHtml("" + desires[i]) %></td>

				<%
				}%></tr><%}
			}
			
		}
	}
	%>
	</table>
	<br />
	<br />
</div>
<br />

<br />
<br />
<br />

<%@include file="/footer.jsp" %>



