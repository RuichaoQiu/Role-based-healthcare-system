<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.ObstericsBean"%>
<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Document Obstetrics Office Visit";
%>

<%@include file="/header.jsp"%>
<itrust:patientNav thisTitle="Document Office Visit" />
<%
pageTitle = "iTrust - Document Obstetric Record";
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnelBean = personnelDAO.getPersonnel(loggedInMID.longValue());
if (!personnelBean.getSpecialty().equals("OB/GYN")){
%>
	<p style="font-size:20px"><i>Only An OB/GYN HCP can document or edit Obstetrics Patient Office Visit! </i></p>
	<p style="font-size:20px"><i>Document a regular office visit, please go to <a href="/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp">here</a> </i></p>
<%
}
else{
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentObstetrics.jsp");
		return;
	}

	AddObstetricAction action = new AddObstetricAction(prodDAO,
			pidString);
	long pid = action.getPid();
	List<ObstericsBean> visits = action.getAllOfficeVisits();
	if ("true".equals(request.getParameter("formIsFilled"))) {
		response.sendRedirect("/iTrust/auth/hcp-uap/editObsterics.jsp");
		return;
	}
%>

<div align=center>
	<form action="documentObstetrics.jsp" method="post" id="formMain">

		<input type="hidden" name="formIsFilled" value="true" /> <br /> <br />
		Are you sure you want to document a <em>new</em> visit for <b><%=StringEscapeUtils.escapeHtml("" + (action.getUserName()))%></b>?<br />
		<br /> <input style="font-size: 150%; font-weight: bold;" type=submit
			value="Yes, Document Obstetric Office Visit">
	</form>
	<br /> Click on an old office visit to modify:<br />
	<%
		for (ObstericsBean ov : visits) {
	%>
	
	<a
		href="/iTrust/auth/hcp-uap/editObsterics.jsp?ovID=<%=StringEscapeUtils.escapeHtml(""
								+ (ov.getID()))%>"><%=StringEscapeUtils.escapeHtml(""
								+ (ov.getvisitDateStr()))%></a><br />
	<%
		}
}
	%>

	<br /> <br /> <br />
</div>
<%@include file="/footer.jsp"%>
