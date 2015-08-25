package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class FilterTransactionAction {
	private AuthDAO authDAO;
	private TransactionDAO transactionDAO;
	
	public FilterTransactionAction (DAOFactory factory){
		this.authDAO = factory.getAuthDAO();
		this.transactionDAO = factory.getTransactionDAO();
	}
	
	/**
	 * 
	 * @param loggedInRole - String of the wanted role for the logged in user, use "All" for any role
	 * @param secondaryRole - String of the wanted role for the secondary user, use "All" for any role
	 * @param start - Timestamp of the start time to search for TODO create a protocol for handling any start time
	 * @param end - Timestamp of the end time to search for TODO create a protocol for handling any start time
	 * @param type - TransactionType - type to be searched for TODO create a protocol for handling any TransactionType
	 * @return A List <TransactionBean> of all of the beans that pass the filter parameters
	 * @throws ITrustException  
	 */
	public List <TransactionBean> FilterAll (String loggedInRole, String secondaryRole, Timestamp start, Timestamp end, TransactionType type) throws ITrustException{
		List <TransactionBean> unfiltered = transactionDAO.getTransactionsBetweenOfType(start, end, type);
		List <TransactionBean> filtered = new ArrayList <TransactionBean>();
		for(TransactionBean t: unfiltered){
			// Check the logged in role
			if ( loggedInRole.equals("all") || (authDAO.getUserRole(t.getLoggedInMID()).getUserRolesString().equals(loggedInRole))){
				try{
					Role actualSecondaryRole = authDAO.getUserRole(t.getSecondaryMID());
					if (  (secondaryRole.equals("all")) || ((actualSecondaryRole != null) && (actualSecondaryRole.getUserRolesString().equals(secondaryRole)))){
						filtered.add(t);
					}
				}
				// Only allow the bean to be added if all secondary roles are allowed.
				catch (Exception e){
					if(secondaryRole.equals("all"))
						filtered.add(t);
				}
			} 
		}
		 
		return filtered;
	}

}
