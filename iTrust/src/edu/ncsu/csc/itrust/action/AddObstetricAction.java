package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ObstericVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for Document Office Visit page (documentOfficeVisit.jsp). This just adds an empty office visit, and
 * provides a list of office visits in case you want to edit an old office visit.
 * 
 * Very similar to {@link AddPatientAction}
 * 
 * 
 */
public class AddObstetricAction extends PatientBaseAction {
	private DAOFactory factory;
	private ObstericVisitDAO ovDAO;

	/**
	 * Sets up the defaults for the class
	 * @param factory
	 * @param pidString
	 *            Patient ID to be validated by the superclass, {@link PatientBaseAction}
	 * @throws ITrustException
	 */
	public AddObstetricAction(DAOFactory factory, String pidString) throws ITrustException {
		super(factory, pidString);
		this.factory = factory;
		ovDAO = factory.getObstericVisitDAO();
	}

	

	/**
	 * Lists all office visits for a particular patient, regardless of who originally documented the office
	 * visit.
	 * 
	 * @return List of office visits,
	 * @throws ITrustException
	 */
	public List<ObstericsBean> getAllOfficeVisits() throws ITrustException {
		return ovDAO.getAllOfficeVisits(pid);
	}

	/**
	 * Returns the full name of the patient with this MID
	 * 
	 * @return name in the form of a string
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getUserName() throws DBException, ITrustException {
		return factory.getAuthDAO().getUserName(pid);
	}
}
