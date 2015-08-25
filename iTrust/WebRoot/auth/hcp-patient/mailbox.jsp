
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.MessageFilterDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.MessageFilterBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>

			<script src="/iTrust/DataTables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
			<script type="text/javascript">
				jQuery.fn.dataTableExt.oSort['lname-asc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? -1 : ((a[1] > b[1]) ?  1 : 0));
				};
				
				jQuery.fn.dataTableExt.oSort['lname-desc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? 1 : ((a[1] > b[1]) ?  -1 : 0));
				};
			</script>
			<script type="text/javascript">	
   				$(document).ready(function() {
       				$("#mailbox").dataTable( {
       					"aaColumns": [ [2,'dsc'] ],
       					"aoColumns": [ { "sType": "lname" }, null, null, {"bSortable": false} ],
       					"sPaginationType": "full_numbers"
       				});
   				});
			</script>
			<style type="text/css" title="currentStyle">
				@import "/iTrust/DataTables/media/css/demo_table.css";		
			</style>

<%

boolean outbox=(Boolean)session.getAttribute("outbox");
boolean isHCP=(Boolean)session.getAttribute("isHCP");

String pageName="messageInbox.jsp";
if(outbox){
	pageName="messageOutbox.jsp";
}
	
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PatientDAO patientDAO = new PatientDAO(prodDAO);

DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, loggedInMID.longValue());

List<MessageBean> messages = outbox?action.getAllMySentMessages():action.getAllMyMessages();
session.setAttribute("messages", messages);
		
if(messages.size() > 0) { %>

<% if (!outbox) { %>

<%= formError %>

<br />
<div align=center id="general">
<form action="messageInbox.jsp?filter=true" method="post" id="messageFilterForm">
<input type="hidden" name="filterIsFilled" value="true" />
<input type="hidden" name="formName" value="messageFilterForm" />

<input type="hidden" id="messageFilterButtonClicked" name="buttonClicked" value="" />


<% if (messageFilterIsOn.equals("true")) { %>
<a href="messageInbox.jsp?filter=false" style="display:none">Turn Filter Off</a>
<% } %>
<% if (messageFilterIsOn.equals("false")) { %>
<a href="messageInbox.jsp?filter=true" style="display:none">Turn Filter On</a>
<% } %>

<table class="fTable display" align="center" id="messageFilterTable">
	<thead>
		<tr id="messageFilterToggleButton">
			<th>Message Filter</th>
			<th style='text-align:right'>
				<span id="messageFilterToggleIndicator"><%= messageFilterIsOn.equals("true")? "On" : "Off" %></span>
			</th>
		</tr>
	</thead>
	<% if (messageFilterIsOn.equals("true")) { %>
	<tbody>
		<tr>
			<td class="subHeaderVertical">Sender:</td>
			<td>
			  <input name="sender" type="text" value="<%= messageFilter.getSender()  %>"/>
			</td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Subject:</td>
			<td>
			  <input name="subject" type="text" value="<%= messageFilter.getSubject()  %>"/>
			</td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Message Contains:</td>
			<td>
			  <input name="contains" type="text" value="<%= messageFilter.getContains()  %>"/>
			</td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Message Does Not Contain:</td>
			<td>
			  <input name="notContains" type="text" value="<%= messageFilter.getNotContains()  %>"/>
			</td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Received After: (Start Date)</td>
			<td>
			  <input name="startDate" type="text" value="<%= messageFilter.getStartDate_forUser()  %>"/>
			  <input type="button" value="Select Date" onclick="displayDatePicker('startDate');"/>
			</td>
		</tr>
		<tr>
			<td class="subHeaderVertical">Received Before: (End Date)</td>
			<td>
			  <input name="endDate" type="text" value="<%= messageFilter.getEndDate_forUser()  %>"/>
			  <input type="button" value="Select Date" onclick="displayDatePicker('endDate');"/>
			</td>
		</tr>
		<tr>
		   <td colspan="2" align="center">
		   		<input type="button" id="messageFilterFormCancelButton" name="cancel" value="Cancel" > &nbsp; &nbsp;
				<input type="button" id="messageFilterFormTestButton" name="testSearch" value="Test Search" > &nbsp; &nbsp;
				<input type="button" id="messageFilterFormSaveButton" name="save" value="Save" >
			</td>
	    </tr>
    </tbody>
    <% } %>
</table>
</form>
</div>
<br />

<script>

	//handle message filter toggle on and off
	var messageFilterIsOn = false;
	$("#messageFilterToggleButton").click(function() {
		if ( $("#messageFilterToggleIndicator").html() == "Off" ) {
			window.location = "messageInbox.jsp?filter=true";
		}
		else {
			window.location = "messageInbox.jsp?filter=false";
		}
	});



	// handle message filter - cancel button clicks
	$("#messageFilterFormCancelButton").click(function () {
		$("#messageFilterButtonClicked").val("cancelButton");
		$("#messageFilterForm").submit();
		
	});
	
	// handle message filter - test search button clicks
	$("#messageFilterFormTestButton").click(function () {
		$("#messageFilterButtonClicked").val("testButton");
		$("#messageFilterForm").submit();
	});
	
	// handle message filter - save button clicks
	$("#messageFilterFormSaveButton").click(function () {
		$("#messageFilterButtonClicked").val("saveButton");
		$("#messageFilterForm").submit();
	});
</script>

<% } %>


<table id="mailbox" class="display fTable">
	<thead>		
		<tr>
			<th><%= outbox?"Receiver":"Sender" %></th>
			<th>Subject</th>
			<th><%= outbox?"Sent":"Received" %></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<% 
	int index=-1;
	for(MessageBean message : messages) {
		
		//START MESSAGE FILTERING
		boolean messageMatchesFilter = true;
		if (!outbox) {
			
			//check sender
			if (messageMatchesFilter && !(messageFilter.getSender().equals(""))) {
				String filterSender = messageFilter.getSender();
				String messageSender = action.getName(message.getFrom());
				if (!(filterSender.equals(messageSender))) {
					messageMatchesFilter = false;
				}
			}
			
			//check subject
			if (messageMatchesFilter && !(messageFilter.getSubject().equals(""))) {
				String filterSubject = messageFilter.getSubject();
				String messageSubject = message.getSubject();
				if (!(filterSubject.equals(messageSubject))) {
					messageMatchesFilter = false;
				}
			}
			
			//check if message contains
			if (messageMatchesFilter && !(messageFilter.getContains().equals(""))) {
				String filterContains = messageFilter.getContains();
				String messageContains = message.getBody();
				if (!(messageContains.contains(filterContains))) {
					messageMatchesFilter = false;
				}
			}
			
			//check if message does not contain
			if (messageMatchesFilter && !(messageFilter.getNotContains().equals(""))) {
				String filterNotContains = messageFilter.getNotContains();
				String messageNotContains = message.getBody();
				if (messageNotContains.contains(filterNotContains)) {
					messageMatchesFilter = false;
				}
			}
			
			//check if message does not fall before start date
			if (messageMatchesFilter && !(messageFilter.getStartDate_forUser().equals(""))) {
				Date filterStartDate = messageFilter.getStartDate_asDate();
				Date messageDate = message.getSentDate();
				if (messageDate.before(filterStartDate)) {
					messageMatchesFilter = false;
				}
			}
			
			//check if message does not fall after end date
			if (messageMatchesFilter && !(messageFilter.getEndDate_forUser().equals(""))) {
				Date filterEndDate = messageFilter.getEndDate_asDate();
				Date messageDate = message.getSentDate();
				if (messageDate.after(filterEndDate)) {
					messageMatchesFilter = false;
				}
			}
			
			
		}
		//END MESSAGE FILTERING
		
		//message clean up
		String style = "";
		if(message.getRead() == 0) {
			style = "style=\"font-weight: bold;\"";
		}

		if(!outbox || message.getOriginalMessageId()==0){
			index ++; 
			String primaryName = action.getName(outbox?message.getTo():message.getFrom());
			List<MessageBean> ccs = action.getCCdMessages(message.getMessageId());
			String ccNames = "";
			int ccCount = 0;
			for(MessageBean cc:ccs){
				ccCount++;
				long ccMID = cc.getTo();
				ccNames += action.getPersonnelName(ccMID) + ", ";
			}
			ccNames = ccNames.length() > 0?ccNames.substring(0, ccNames.length()-2):ccNames;
			String toString = primaryName;
			if(ccCount>0){
				String ccNameParts[] = ccNames.split(",");
				toString = toString + " (CC'd: ";
				for(int i = 0; i < ccNameParts.length-1; i++) {
					toString += ccNameParts[i] + ", ";
				}
				toString += ccNameParts[ccNameParts.length - 1] + ")";
			}
			if (messageFilterIsOn.equals("false") || messageMatchesFilter) {
			%>					
				<tr <%=style%>>
					<td><%= StringEscapeUtils.escapeHtml("" + ( toString)) %></td>
					<td><%= StringEscapeUtils.escapeHtml("" + ( message.getSubject() )) %></td>
					<td><%= StringEscapeUtils.escapeHtml("" + ( dateFormat.format(message.getSentDate()) )) %></td>
					<td><a href="<%= outbox?"viewMessageOutbox.jsp?msg=" + StringEscapeUtils.escapeHtml("" + ( index )):"viewMessageInbox.jsp?msg=" + StringEscapeUtils.escapeHtml("" + ( index )) %>">Read</a></td>
				</tr>			
			<%
			}
		}
		
	}	
	%>
	</tbody>
</table>
<%} else { %>
	<div>
		<i>You have no messages</i>
	</div>
<%	} %>