package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import edu.ncsu.csc.itrust.exception.DBException;

/**
 * A bean for storing message filter data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class MessageFilterBean {
	private long mid = 0;
	private String sender = "";
	private String subject = "";
	private String contains = "";
	private String notContains = "";
	private String startDate = "";
	private String endDate = "";

	
	public MessageFilterBean() {
	}

	/**
	 * Returns the MID designating this Message Filter's user.
	 * 
	 * @return A long representing the MID of this Message Filter's user.
	 */
	public long getMid() {
		return mid;
	}

	/**
	 * Set this Message Filter's user MID
	 * 
	 * @param mid A long representing the MID which we want to designate to this Message Filter.
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	
	/**
	 * Returns the sender of this Message Filter.
	 * 
	 * @return A string representing the sender of this Message Filter.
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Set this Message Filter's sender
	 * 
	 * @param sender A String representing the sender of this Message Filter.
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Returns the subject of this Message Filter.
	 * 
	 * @return A string representing the subject of this Message Filter.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Set this Message Filter's subject
	 * 
	 * @param subject A String representing the subject of this Message Filter.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Returns the "contains" content of this Message Filter.
	 * 
	 * @return A string representing the "contains" content of this Message Filter.
	 */
	public String getContains() {
		return contains;
	}

	/**
	 * Set this Message Filter's "contains" content
	 * 
	 * @param contains A String representing the "contains" content of this Message Filter.
	 */
	public void setContains(String contains) {
		this.contains = contains;
	}

	/**
	 * Returns the "not contains" content of this Message Filter.
	 * 
	 * @return A string representing the "not contains" content of this Message Filter.
	 */
	public String getNotContains() {
		return notContains;
	}

	/**
	 * Set this Message Filter's "not contains" content
	 * 
	 * @param notContains A String representing the "not contains" content of this Message Filter.
	 */
	public void setNotContains(String notContains) {
		this.notContains = notContains;
	}
	
	/**
	 * Returns true if the start date of the filter is set, false otherwise
	 * 
	 * @return a boolean: true if the start date of the filter is set, false otherwise
	 */
	public boolean getIfStartDateIsSet() {
		return !(startDate.equals("01/01/1970"));
	}

	/**
	 * Returns the start date as a string of the form MM/dd/yyyy
	 * 
	 * @return A string representing the start date of the message filter
	 */
	public String getStartDate() {
		return startDate;
	}
	
	/**
	 * Returns the start date formatted for the user
	 * If start date is not set, it returns a blank string 
	 * 
	 * @return A string representing the start date of the message filter formatted for the user
	 *         or a blank string if no start date is set
	 */
	public String getStartDate_forUser() {
		if (getIfStartDateIsSet()) {
			return startDate;
		}
		else {
			return "";
		}
	}
	
	
	/**
	 * Returns the start date of the message filter as a Date object
	 * 
	 * @return A Date object representation of the start date of the message filter
	 */
	public Date getStartDate_asDate() {
		Date d = null;
		if (!startDate.equals("")) {
			try {
				d = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
			} catch (ParseException e) {
				System.out.println(e.toString());
			}
		}
		else {
			try {
				d = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970");
			} catch (ParseException e) {
				System.out.println(e.toString());
			}
		}
		
		return d;
	}

	/**
	 * Set this Message Filter's start date
	 * 
	 * @param startDate A String representing the message filter's start date in the form MM/dd/yyyy
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Returns true if the end date of the filter is set, false otherwise
	 * 
	 * @return a boolean: true if the end date of the filter is set, false otherwise
	 */
	public boolean getIfEndDateIsSet() {
		return !(endDate.equals("01/01/1970"));
	}

	/**
	 * Returns the end date as a string of the form MM/dd/yyyy
	 * 
	 * @return A string representing the end date of the message filter
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * Returns the end date formatted for the user
	 * If end date is not set, it returns a blank string 
	 * 
	 * @return A string representing the end date of the message filter formatted for the user
	 *         or a blank string if no end date is set
	 */
	public String getEndDate_forUser() {
		if (getIfEndDateIsSet()) {
			return endDate;
		}
		else {
			return "";
		}
	}
	
	/**
	 * Returns the end date of the message filter as a Date object
	 * 
	 * @return A Date object representation of the end date of the message filter
	 */
	public Date getEndDate_asDate() {
		Date d = null;
		if (!endDate.equals("")) {
			try {
				d = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			} catch (ParseException e) {
				System.out.println(e.toString());
			}
		}
		else {
			try {
				d = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970");
			} catch (ParseException e) {
				System.out.println(e.toString());
			}
		}
		return d;
	}

	/**
	 * Set this Message Filter's end date
	 * 
	 * @param endDate A String representing the message filter's end date in the form MM/dd/yyyy
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
