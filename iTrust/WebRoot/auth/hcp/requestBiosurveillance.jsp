<%@page import="edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.net.URLEncoder" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.HospitalBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EpidemicCheckerAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO" %>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.WardBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.WardRoomBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.WardDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>

<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.action.PatientRoomAssignmentAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Request Biosurveillance";
%>

<%@include file="/header.jsp" %>

<body>
<%
String malaria = request.getParameter("analyze_malaria");
String influenza = request.getParameter("analyze_influenza");
String recent = request.getParameter("analyze_recent");

String dCode = "";
String zipCode = "";
String date = "";
String threshold = "";
boolean codeError = false;
boolean zipError = false;
boolean dateError = false;
boolean thresholdError = false;
boolean Error = false;
boolean epidemic = false;
if(malaria!=null || influenza!=null || recent!=null){
	loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE, loggedInMID.longValue(), 0, "");
if(malaria!=null){
	dCode = "84."+request.getParameter("malariaDiagnosis");
	zipCode = request.getParameter("malariaZipCode");
	date = request.getParameter("malariaDate");
	threshold = request.getParameter("malariaThreshold");
	
	if(!(threshold.matches("100") || threshold.matches("[0-9]{2}"))){
		thresholdError = true;
		Error = true;
	}
}
else if(influenza!=null){
	dCode = "487."+request.getParameter("influenzaDiagnosis");
	zipCode = request.getParameter("influenzaZipCode");
	date = request.getParameter("influenzaDate");
}
else if(recent!=null){
	dCode = request.getParameter("recentDiagnosis");
	zipCode = request.getParameter("recentZipCode");
	date = request.getParameter("recentDate");
}
	if(!(dCode.matches("[0-9]+.[0-9]{2}") && dCode.length()>4 && dCode.length()<7)){
		codeError = true;
		Error = true;
	}
	if(!(zipCode.matches("[0-9]{5}") || zipCode.matches("[0-9]{5}-[0-9]{4}"))){
		zipError = true;
		Error = true;
	}
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    dateFormat.setLenient(false);
    try {
      dateFormat.parse(date);
    } catch (ParseException pe) {
      dateError = true;
      Error = true;
    }
}


%>
<%
if(recent==null||Error==true){
%>
<h1>Request Biosurveillance</h1>
<%
	if(codeError == true)
		out.println("<font color = \"red\">Please enter a valid Diagnosis Code.</font><br>");
	if(zipError == true)
		out.println("<font color = \"red\">Please enter a valid Zip Code.</font><br>");
	if(dateError == true)
		out.println("<font color = \"red\">Please enter a valid Date.</font><br>");
	if(thresholdError == true)
		out.println("<font color = \"red\">Please enter a valid Threshold.</font><br>");
%>
<form name = "request_bio"> 
<input type="hidden" name="request_bio"> 
<table class="fTable" align=center">
	<tr>
		<th align=center colspan = '4'>
			Malaria
		</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">
			Diagnosis:
		</td>
		<td>
			84.<input name="malariaDiagnosis" type="text">
		</td>
		<td class="subHeaderVertical">
			Zip Code:
		</td>
		<td>
			<input type=text name="malariaZipCode">
		</td>
	</tr>
	<tr>
		<td>Date:</td>
		<td>
			<input name="malariaDate">
			<input type=button value="Select Date" onclick="displayDatePicker('malariaDate');">
		</td>
		<td>Threshold:</td>
		<td>
			<input name="malariaThreshold" type="text">%
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<input type="submit" align="right" name="analyze_malaria" id="analyze_malaria"
			style="font-size: 10pt; font-weight: bold;"
			value="Analyze" />
		</td>
	</tr>
</table>
<%
	if(malaria!=null && Error==false){
		DiagnosesDAO diagDAO = DAOFactory.getProductionInstance().getDiagnosesDAO();
		EpidemicCheckerAction epic = new EpidemicCheckerAction(diagDAO);
		Date upper = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		int thres = Integer.parseInt(threshold);
		epidemic = epic.checkMalariaEpidemic(zipCode, thres, upper, dCode);
		out.println("Epidemic Occur in Past Two Weeks:");
		if(epidemic)
			out.println("Yes<br>");
		else
			out.println("No<br>");
				
	}
%>
<br> <br>
<table class="fTable" align=center">
	<tr>
		<th align=center colspan = '4'>
			Influenza
		</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">
			Diagnosis:
		</td>
		<td>
			487.<input name="influenzaDiagnosis" type="text">
		</td>
		<td class="subHeaderVertical">
			Zip Code:
		</td>
		<td>
			<input type=text name="influenzaZipCode">
		</td>
	</tr>
	<tr>
		<td>Date:</td>
		<td>
			<input name="influenzaDate">
			<input type=button value="Select Date" onclick="displayDatePicker('influenzaDate');">
		</td>
		<td colspan="2">
			<input type="submit" align="right" name="analyze_influenza" id="analyze_influenza"
			style="font-size: 10pt; font-weight: bold;"
			value="Analyze" />
		</td>
	</tr>
</table>
<%
	if(influenza!=null && Error==false){
		DiagnosesDAO diagDAO = DAOFactory.getProductionInstance().getDiagnosesDAO();
		EpidemicCheckerAction epic = new EpidemicCheckerAction(diagDAO);
		Date upper = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		epidemic = epic.checkInfluenzaEpidemic(zipCode, upper, dCode);
		out.println("Epidemic Occur in Past Two Weeks:");
		if(epidemic)
			out.println("Yes<br>");
		else
			out.println("No<br>");
				
	}
%>
<br> <br>
<table class="fTable" align=center">
	<tr>
		<th align=center colspan = '4'>
			Recent Trends
		</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">
			Diagnosis Code:
		</td>
		<td>
			<input name="recentDiagnosis" type="text">
		</td>
		<td class="subHeaderVertical">
			Zip Code:
		</td>
		<td>
			<input type=text name="recentZipCode">
		</td>
	</tr>
	<tr>
		<td>Date:</td>
		<td>
			<input name="recentDate">
			<input type=button value="Select Date" onclick="displayDatePicker('recentDate');">
		</td>
		<td colspan="2">
			<input type="submit" align="right" name="analyze_recent" id="analyze_recent"
			style="font-size: 10pt; font-weight: bold;"
			value="Analyze" />
		</td>
	</tr>
</table>

</form>

<%}
else if(recent!=null){
	DiagnosesDAO diagDAO = DAOFactory.getProductionInstance().getDiagnosesDAO();
	Date upper = new SimpleDateFormat("MM/dd/yyyy").parse(date);
	Calendar cal = Calendar.getInstance();
	cal.setTime(upper);
	cal.add(Calendar.DATE, -56);
	Date lower = cal.getTime();
	List<DiagnosisStatisticsBean> db = diagDAO.getWeeklyCountsRSN(dCode , zipCode, lower, upper);
%>
    <h1>Recent Diagnosis Trends</h1>
    <a href="/iTrust/auth/hcp/requestBiosurveillance.jsp">Request another Biosurveillance</a>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

        var logRole = google.visualization.arrayToDataTable([
          ['Week', 'Region', 'State', 'All'],
  	<%
  		for(int i = 0; i<db.size(); i++)
			out.println("['"+db.get(i).getStartDate()+"-"+db.get(i).getEndDate()+"', "+db.get(i).getRegionStats()+", "+db.get(i).getStateStats()+", "+db.get(i).getNationStats()+"],");
  	%>
  	]);
        
        var options1 = {
          title: 'Recent Trends'
        };


        var chart1 = new google.visualization.ColumnChart(document.getElementById('chart1'));
        chart1.draw(logRole, options1);      
      }
    </script>
    <div id="chart1" style="width: 1000px; height: 600px;"></div>


<%
}
%>

</body>
<%@include file="/footer.jsp" %>
