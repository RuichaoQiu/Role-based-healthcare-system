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
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/InitRecords.jsp");
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
			<p style="font-size:20px"><i>The patient is not eligible for obstetric care.</i></p>
		<%
	}
	else
	if(records.isEmpty()){
		%>
			<p style="font-size:20px"><i>No init records available</i></p>
		<%
	}
	else{
		if(!records.isEmpty()){
			%>
			<!-- 
			Create a Initial history table with height, weight, bmi, blood pressure, patient smoking status, household smoking status, 
			cholesterol, last recorded date, and recorded by personnel fields.
			 -->
			<tr>
				<th colspan="14" style="text-align: center;"><%= patientName %>'s Init Record History</th>
			</tr>
			<tr class = "subHeader">
				<td>Mid</td>
				<td>Last Name</td>
				<td>First Name</td>
				<td>LMP</td>
				<td>EDD</td>
				<td>Weeks Pregnant</td>
				<td>Prior Pregnancies</td>
				<td>Notes</td>
			</tr>
			<%
		}
		for(InitRecord hr : records){
			
			if(!records.isEmpty()){
				%>
				<!-- 
				Get the height, weight, bmi, blood pressure, patient smoking status, household smoking status, cholesterol, date recorded, 
				and recorded by personnel for each Initial record
				-->
				<tr>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getMid())) %></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getLastname())) %></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getFirstname())) %></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getLMPstr())) %></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getEDDstr())) %></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getWeeksPreg())) %></td>
					<td align=center><a href="viewPriorPregnancies.jsp">View</a></td>
					<td align=center><%= StringEscapeUtils.escapeHtml("" + (hr.getNotes())) %></td>
				</tr>
				<%
			}
			
		}
	}
	%>
	</table>
	<br />
	<br />
	<%
	PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
	PersonnelBean personnelBean = personnelDAO.getPersonnel(loggedInMID.longValue());
	if (gender.equals("Female") && personnelBean.getSpecialty().equals("OB/GYN")){
		%>
			<a href="addInitRecord.jsp"><button id="createInitRecordButton">Create New Record</button></a>
		<%
	}
	%>
</div>
<br />

<br />
<br />
<br />

<%@include file="/footer.jsp" %>



