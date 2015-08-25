package edu.ncsu.csc.itrust.action;

import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import edu.ncsu.csc.itrust.action.base.EditObstetricBaseAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.InitRecord;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.InitRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstericVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EditObstetricValidator;

/**
 * Edits the office visits of a patient Used by editOfficeVisit.jsp.  This 
 * exists in two states: saved and unsaved.  If unsaved, data cannot be saved 
 * to sub actions (if this is attempted, exceptions will be raised).  Once it 
 * is saved, however, the sub actions can be saved.  
 * 
 *  
 * 
 */
public class EditObstetricOfficeVisitAction extends EditObstetricBaseAction {
	
	private EditObstetricValidator validator = new EditObstetricValidator();
	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalDAO;
	private ObstericVisitDAO ovDAO;
	private PatientDAO patDAO;
	private InitRecordsDAO irDAO;
	private long weeksPreg;
	private long daysInWeekPreg;
	
	private EventLoggingAction loggingAction;
	
	private long loggedInMID;


	/**
	 * Patient id and office visit id validated by super class
	 * 
	 * @param factory The DAOFactory to be used in creating the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The patient who this action is performed on.
	 * @param ovIDString The ID of the office visit in play.
	 * @throws ITrustException
	 */
	public EditObstetricOfficeVisitAction(DAOFactory factory, long loggedInMID, String pidString, String ovIDString)
			throws ITrustException {
		super(factory, loggedInMID, pidString, ovIDString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getObstericVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.patDAO = factory.getPatientDAO();
		this.irDAO = factory.getInitRecordsDAO();
		
		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Create an OfficeVisitAction that is not yet associated with an actual 
	 * office visit.  When update() is called, it will be saved.  Until then, 
	 * any attempt to save health records, prescriptions, procedures, lab procedures, 
	 * immunizations, or diagnoses will raise an exception.
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 * @throws ITrustException
	 */
	public EditObstetricOfficeVisitAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, loggedInMID, pidString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getObstericVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.patDAO = factory.getPatientDAO();
		this.irDAO = factory.getInitRecordsDAO();
		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	public boolean checkvalid() throws ITrustException {
		List<InitRecord> ir = irDAO.getAllInitRecords(pid);
		if (ir.isEmpty()) return false;
		Date now_date = new Date();
		long daysPreg = 0;
		daysPreg = now_date.getTime() - ir.get(0).getLMP().getTime();
		daysPreg = daysPreg / 86400000; //number of milliseconds in a day
		
		//calculate weeks pregnant
		weeksPreg = daysPreg / 7;
		
		//calculate days in week pregnant (ex. 3 weeks 4 days). We are calculating the days
		daysInWeekPreg = daysPreg % 7;
		if (weeksPreg < 49 || (weeksPreg == 49 && daysInWeekPreg == 0)){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Returns the obstetrics office visit bean for the obstetrics office visit
	 * 
	 * @return the ObstericsBean of the obstetrics office visit
	 * @throws ITrustException
	 */
	public ObstericsBean getOfficeVisit() throws ITrustException {
		return getBean();
	}
	
	/**
	 * Used to update the sub actions once a office visit is saved.  Until this 
	 * is called, attempting to save sub actions will raise an exception.
	 * @throws ITrustException
	 */
	private void reinitializeSubActions() throws ITrustException {
		if (isUnsaved()) {
			throw new ITrustException("Cannot initalize EditObstetric sub actions.  No ovID is present.");
		}
		DAOFactory factory = getFactory();
		String pidString = ""+getPid();
		String ovIDString = ""+getOvID();
		
	}

	
	
	
	

	/**
	 * Updates the office visit with information from the form passed in.  If 
	 * the office visit has not yet been saved, calling this method will save 
	 * it as well as make the sub actions able to be saved.
	 * 
	 * @param form
	 *            information to update
	 * @return "success" or exception's message
	 * @throws FormValidationException
	 */
	public String updateInformation(EditObstetricForm form) throws FormValidationException {
		String confirm = "";
		try {
			updateOv(form);
			confirm = "success";
			return confirm;
		} catch (ITrustException e) {
			
			return e.getMessage();
		}
	}
	
	
	/**
	 * Helper that logs an office visit event.  The associated patient id, HCP 
	 * id, and office visit id are automatically included.
	 * @param trans Transaction type for the log.
	 * @throws DBException
	 */
	public void logOfficeVisitEvent(TransactionType trans) throws DBException {
		loggingAction.logEvent(trans, loggedInMID, getPid(), "Office visit ID: " + getOvID());
	}
	
	
	
	

	/**
	 * Updates the office visit.
	 * 
	 * @param form form with all the information
	 * @throws DBException
	 * @throws FormValidationException
	 */
	
	private void updateOv(EditObstetricForm form) throws DBException, FormValidationException, ITrustException {
		validator.validate(form);
		ObstericsBean ov = getBean();
		ov.setvisitDate(form.getvisitDateStr());
		ov.setDaysPreg(form.getDaysPreg());
		ov.setWeeksPreg(form.getWeeksPreg());
		ov.setWeight(form.getWeight());
		ov.setSBloodPressure(form.getSBloodPressure());
		ov.setDBloodPressure(form.getDBloodPressure());
		ov.setFHR(form.getFHR());
		ov.setFHU(form.getFHU());
		ov.setPatientID(pid);
		updateBean(ov);
	}
	
	
	
	/**
	 * @return The OfficeVisitBean associated with this office visit, or if it 
	 * has not been saved, a default OfficeVisitBean with the HCP id and 
	 * patient id filled in.
	 * @throws DBException
	 */
	
	private ObstericsBean getBean() throws DBException {
		if (isUnsaved()) {
			ObstericsBean b = new ObstericsBean();
			Date d = new Date();
			b.setvisitDate(new SimpleDateFormat("yyyy-MM-dd").format(d.getTime()));
			b.setDaysPreg(Long.toString(weeksPreg));
			b.setWeeksPreg(Long.toString(daysInWeekPreg));
			return b;
		} else {
			return ovDAO.getOfficeVisit(ovID);
		}
	}
	
	
	/**
	 * Update the office visit with the given data.  If the office visit has 
	 * not yet been saved, this will save it and reinitialize the sub actions.
	 * @param bean The data with which to update the office visit.
	 * @throws DBException
	 * @throws ITrustException
	 */
	
	private void updateBean(ObstericsBean bean) throws DBException, ITrustException {
		if (isUnsaved()) {
			// bean.getID() == -1
			ovID = ovDAO.add(bean);
			reinitializeSubActions();
		} else {
			ovDAO.update(bean);
		}
	}
	

}
