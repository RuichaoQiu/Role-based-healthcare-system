/**
 * 
 */
package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Base class for actions used to edit office visits.  This stores the HCP id, 
 * patient id, and office visit id.
 * 
 *  
 *  
 */

public class EditObstetricBaseAction extends ObstetricBaseAction {
		
	private long hcpid;

	/**
	 * @param factory
	 * @param pidString
	 * @param ovIDString
	 * @throws ITrustException
	 */
	
	public EditObstetricBaseAction(DAOFactory factory, long hcpid, String pidString, String ovIDString)
			throws ITrustException {
		super(factory, pidString, ovIDString);
		this.hcpid = hcpid;
	}
	
	/**
	 * An office visit that is initially unsaved.
	 * 
	 * @param factory
	 * @param hcpid
	 * @param pidString
	 * @throws ITrustException
	 */
	public EditObstetricBaseAction(DAOFactory factory, long hcpid, String pidString)
			throws ITrustException {
		super(factory, pidString);
		this.hcpid = hcpid;
	}
	
	

}
