package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.VisitFlag;
import edu.ncsu.csc.itrust.beans.forms.VisitReminderReturnForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Sends visit reminders to patients
 * @author - bllevy2, dxchen3
 * 
 */
public class SendVisitRemindersAction {

	private static final long SYSTEM_REMINDER_MID = 9000000009L;
	private static final int WEEK_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;

	/**
	 * Reminder Type enumeration.
	 */
	public static enum ReminderType {
		DIAGNOSED_CARE_NEEDERS("Diagnosed Care Needers"),
		IMMUNIZATION_NEEDERS("Immunization Needers");

		private String typeName;

		private ReminderType(String typeName) {
			this.typeName = typeName;
		}

		private static final Map<String, ReminderType> map = new HashMap<String, ReminderType>();
		static {
			for (ReminderType rt : ReminderType.values()) {
				map.put(rt.getTypeName(), rt);
			}
		}

		/**
		 * Gets the ReminderType for the name passed as a param
		 * 
		 * @param name
		 * @return the ReminderType associated with the name
		 */
		public static ReminderType getReminderType(String name) {
			return map.get(name);
		}

		/**
		 * Returns the type name as a string
		 * 
		 * @return
		 */
		public String getTypeName() {
			return typeName;
		}
	}

	/**
	 * 
	 * Begin SendVisitRemindersAction code
	 * 
	 */
	private ApptDAO apptDAO;
	private PersonnelDAO personnelDAO;
	private PatientDAO patientDAO;
	private DAOFactory factory;
	private long loggedInMID;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID MID of the person who is logged in
	 * @throws ITrustException
	 */
	public SendVisitRemindersAction(DAOFactory factory, long loggedInMID) throws ITrustException {
		this.loggedInMID = loggedInMID;
		apptDAO = factory.getApptDAO();
		patientDAO = factory.getPatientDAO();
		personnelDAO = factory.getPersonnelDAO();
		this.factory = factory;
	}

	/**
	 * Sends out a reminder to each of the people with an appointment in the next year
	 * 
	 * @param type
	 *            the ReminderType
	 * @return the list of VisitReminderReturnForms
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void sendVisitReminders(ReminderType type) throws ITrustException, FormValidationException {
		sendVisitReminders(type,365);
	}
	
	
	/**
	 * Makes a message bean for the reminder message based on the given
	 * appointment bean
	 * @param appt the appointment bean to make the message for
	 * @return The messageBean that corresponds to the appointment
	 * @throws ITrustException
	 */
	public MessageBean makeReminderMessage(ApptBean appt) throws ITrustException{
		if(appt== null)
			throw new ITrustException("Invalid Visit Needer");
		MessageBean reminder = new MessageBean();
		reminder.setFrom(SYSTEM_REMINDER_MID);
		reminder.setTo(appt.getPatient());
		reminder.setSubject("Reminder: upcoming appointment in "+ getDaysUntilAppt(appt) +" day(s)");
		Date date = new Date(appt.getDate().getTime());
		DateFormat time = new SimpleDateFormat("HH:mm");
		DateFormat day = new SimpleDateFormat("MM/dd/yy");
		String name =  personnelDAO.getName(appt.getHcp());
		reminder.setBody("You have an appointment on "+ time.format(date)  + ", "+ day.format(date) +" with Dr. "+ name);
		reminder.setRead(0);
		return reminder;
	}
	
	public MessageBean makeImmunizationMessage(VisitReminderReturnForm reminder) throws ITrustException{
		MessageBean bean = new MessageBean();
		bean.setFrom(SYSTEM_REMINDER_MID);
		bean.setTo(reminder.getPatientID());
		bean.setSubject("Reminder: You are missing neccessary immunizations");
		String dr = personnelDAO.getName(reminder.getHcpID());
		String body = "You are in need of immunizations, please set up an appointment with Dr. " + dr 
						+ ".\n You require the following immunization(s):\n\n";
		VisitFlag[] flags  = reminder.getVisitFlags();
		for(VisitFlag flag : flags){
			body += flag.getValue();
			body += "\n";
		}
		bean.setBody(body);
		return bean;
	}

	private long getDaysUntilAppt(ApptBean appt) {
		long milliseconds_per_day = 24 /*hours*/ * 60 /*minutes*/ * 60 /*seconds*/ * 1000;
		return (appt.getDate().getTime() - (new Date()).getTime())/milliseconds_per_day;
	}
	 
	/**
	 * Makes a reminder email that tells a user they have a system reminder message
	 * @param to the email of the recipient
	 * @return the email object to send using EmailUtil
	 */
	public Email makeReminderEmail(String to) {
		Email reminder = new Email();
		reminder.setFrom("SystemReminder@itrust.com");
		reminder.setSubject("New System Reminder");
		reminder.setBody("You have a new message from System Reminder");
		reminder.setTimeAdded(new Timestamp(new Date().getTime()));
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		reminder.setToList(toList);
		return reminder;
	}
	
	/**
	 * Sends out a reminder to each of the people within a time frame
	 * 
	 * @param type
	 *            the ReminderType
	 * @param within
	 * 			  the days until next appointment
	 * 
	 * @throws ITrustException
	 */
	public void sendVisitReminders(ReminderType type, int within) throws ITrustException, FormValidationException {
		EventLoggingAction ela = new EventLoggingAction(factory);
		if (null == type)
			throw new ITrustException("Reminder Type DNE");
		SendMessageAction ma = new SendMessageAction(factory, loggedInMID);
		switch (type) {
			case DIAGNOSED_CARE_NEEDERS:
				List<ApptBean> appts = apptDAO.getApptWithin(loggedInMID, within);
				for(int i = 0; i< appts.size();i++){
					ApptBean appt = appts.get(i);
					MessageBean reminder = makeReminderMessage(appt);
					EmailUtil eu = new EmailUtil(factory);
					PatientBean patient = patientDAO.getPatient(appt.getPatient());
					try{
						ma.sendMessage(reminder);
						eu.sendEmail(makeReminderEmail(patient.getEmail()));
					}
					catch(SQLException e){
						System.err.println(e.getMessage());
					}
				}
				break;
			case IMMUNIZATION_NEEDERS:
				List<VisitReminderReturnForm> needers;
				GetVisitRemindersAction getReminders = new GetVisitRemindersAction(this.factory,loggedInMID);
				needers = getReminders.getVisitReminders(GetVisitRemindersAction.ReminderType.IMMUNIZATION_NEEDERS);  
				for(int i = 0; i < needers.size();i++){
					VisitReminderReturnForm reminder = needers.get(i);
					/*TODO
					 * 	Send a message to the user telling them that they need
					 *  to get an immunization and that they should contact their
					 *  Doctor
					 */
					MessageBean message = makeImmunizationMessage(reminder);
					try{
						ma.sendMessage(message);
					}
					catch(SQLException e){
						System.err.println(e.getMessage());
					}
				}
				break;
			default:
				throw new ITrustException("Reminder Type DNE");
		}
		ela.logEvent(TransactionType.SEND_APPOINTMENT_REMINDER_MESSAGE, loggedInMID, SYSTEM_REMINDER_MID, "");
	}
	

	
}
