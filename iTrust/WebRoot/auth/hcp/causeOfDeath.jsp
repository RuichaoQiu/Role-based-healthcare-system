<%@page import="edu.ncsu.csc.itrust.action.*"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.beans.DeathStatsBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.*"%>
<%@page import="java.util.List"%>
<%@page import= "edu.ncsu.csc.itrust.enums.Gender"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Cause-of-death reports";
%>

<%@include file="/header.jsp"%>

<h1>View Cause-of-death reports</h1>


<br />
<br />
<form action="causeOfDeath.jsp" method="post" id="formMain">
<input type=hidden name="formIsFilled" value="true">
<input type="radio" name="sex" value="Not Specified"checked>All
<br>
<input type="radio" name="sex" value="Male" >Male
<br>
<input type="radio" name="sex" value="Female">Female
<br>
Died between the year of <input name="start" value="1000" size="4"> 

and <input name="end" value="2015" size="4">
<br>
<input type=submit value="View top two causes of death">
</form>

<%
ViewDeathStatsAction action = new ViewDeathStatsAction(prodDAO);
List<DeathStatsBean> list;
if("true".equals(request.getParameter("formIsFilled"))){
	
		list = action.getStats(loggedInMID.longValue(), Gender.parse(request.getParameter("sex")), Integer.valueOf(request.getParameter("start")),Integer.valueOf(request.getParameter("end")), 100);
		if(list ==null){
			%><span>You have no dead patients of this gender.</span><%
			list = action.getStats(0, Gender.parse(request.getParameter("sex")), Integer.valueOf(request.getParameter("start")),Integer.valueOf(request.getParameter("end")), 100);
			%><span>The top two causes of death amongst all patients are </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths
		 	and </span><% out.print(list.get(1).getDescription());%><span>(</span><%out.print(list.get(1).getCode());%><span>) with </span><% out.print(list.get(1).getCount());%><span> deaths.</span><%
		}
		else if(list.size()>=2){
			%><span>The top two causes of death amongst your patients are </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths
		 	and </span><% out.print(list.get(1).getDescription());%><span>(</span><%out.print(list.get(1).getCode());%><span>) with </span><% out.print(list.get(1).getCount());%><span> deaths.</span><%
			list = action.getStats(0, Gender.parse(request.getParameter("sex")), Integer.valueOf(request.getParameter("start")),Integer.valueOf(request.getParameter("end")), 100);
		 	%><span>The top two causes of death amongst all patients are </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths
		 	and </span><% out.print(list.get(1).getDescription());%><span>(</span><%out.print(list.get(1).getCode());%><span>) with </span><% out.print(list.get(1).getCount());%><span> deaths.</span><%

		}
		else if(list.size()==1){
			%><span>The top cause of death amongst your patients is </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths.</span><%
			list = action.getStats(0, Gender.parse(request.getParameter("sex")), Integer.valueOf(request.getParameter("start")),Integer.valueOf(request.getParameter("end")), 100);
			%><span>The top two causes of death amongst all patients are </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths
		 	and </span><% out.print(list.get(1).getDescription());%><span>(</span><%out.print(list.get(1).getCode());%><span>) with </span><% out.print(list.get(1).getCount());%><span> deaths.</span><%
		}
		else{
			%><span>You have no dead patients of this gender.</span><%
			list = action.getStats(0, Gender.parse(request.getParameter("sex")), Integer.valueOf(request.getParameter("start")),Integer.valueOf(request.getParameter("end")), 100);
			%><span>The top two causes of death amongst all patients are </span><% out.print(list.get(0).getDescription());%><span>(</span><%out.print(list.get(0).getCode());%><span>) with </span><% out.print(list.get(0).getCount());%><span> deaths
		 	and </span><% out.print(list.get(1).getDescription());%><span>(</span><%out.print(list.get(1).getCode());%><span>) with </span><% out.print(list.get(1).getCount());%><span> deaths.</span><%
		}

}
%>
</table>
</form>

<%@include file="/footer.jsp" %>

