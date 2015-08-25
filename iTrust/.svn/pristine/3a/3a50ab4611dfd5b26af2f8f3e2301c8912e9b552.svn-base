package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 * A bean for storing initial record data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class InitRecord {
	private long Mid = 0;
	private String Lastname = "";
	private String Firstname = "";
	private String LMP = "0000-00-00";
	private String EDD = "0000-00-00";
	private String WeeksPreg = "";
	private String PriorPregnancies = "";
	private String Notes = "";

	public InitRecord() {
	}
	
	/**
	 * Returns the MID designating this InitRecord's patient.
	 * 
	 * @return A long representing the MID of this InitRecord's patient.
	 */
	public long getMid(){
		return Mid;
	}
	
	/**
	 * Set this InitRecord's patient MID.
	 * 
	 * @param Mid A long representing the MID which we want to designate to this InitRecord.
	 */
	public void setMid(long Mid){
		this.Mid = Mid;
	}
	
	/**
	 * Retrieve the last name of the patient who is attached to this InitRecord.
	 * 
	 * @return A string containing the last name of the patient who is attached to this InitRecord.
	 */
	public String getLastname(){
		return Lastname;
	}
	
	/**
	 * Set the last name of this InitRecord's patient.
	 * 
	 * @param Lastname The desired last name we want to set.
	 */
	public void setLastname(String Lastname){
		this.Lastname = Lastname;
	}
	
	/**
	 * Retrieve the first name of the patient who is attached to this InitRecord.
	 * 
	 * @return A string containing the first name of the patient who is attached to this InitRecord.
	 */
	public String getFirstname(){
		return Firstname;
	}
	
	/**
	 * Set the first name of this InitRecord's patient.
	 * 
	 * @param Firstname The desired first name we want to set.
	 */
	public void setFirstname(String Firstname){
		this.Firstname = Firstname;
	}
	
	/**
	 * Retrieves the Last Menstural Period of this InitRecord's patient.
	 * 
	 * @return A Date object containing the date of the LMP.
	 */
	public Date getLMP() {
		Date d = null; 
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(LMP);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return d;
	}
	
	/**
	 * Retrieves the Last Menstural Period of this InitRecord's patient as a String.
	 * 
	 * @return A String containing the date of the LMP.
	 */
	public String getLMPstr() {
		return LMP;
	}
	
	/**
	 * Sets the Last Menstural Period of this InitRecord's patient.
	 * 
	 * @param LMP The desired LMP we want to set.
	 */
	public void setLMP(String LMP){
		this.LMP = LMP;
	}
	
	/**
	 * Retrieves a Date object containing the patient's estimated delivery date.
	 * 
	 * @return A Date object containing the patient's estimated delivery date.
	 */
	public Date getEDD() {
		Date d = null; 
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(EDD);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return d;
	}
	
	/**
	 * Retrieves a String containing the patient's estimated delivery date.
	 * 
	 * @return A String containing the patient's estimated delivery date.
	 */
	public String getEDDstr() {
		return EDD;
	}
	
	/**
	 * Sets the patient's estimated Delivery date.
	 * 
	 * @param EDD A String with the desired estimated delivery date we want to set.
	 */
	public void setEDD(String EDD){
		this.EDD = EDD;
	}
	
	/**
	 * Retrieves the number of weeks pregnant for the designated patient.
	 * 
	 * @return A String containing the number of weeks that the patient was pregnant.
	 */
	public String getWeeksPreg(){
		return WeeksPreg;
	}
	
	/**
	 * Sets the number of weeks a designated patient has been pregnant.
	 * 
	 * @param WeeksPreg The number of weeks pregnant that we want to set.
	 */
	public void setWeeksPreg(String WeeksPreg){
		this.WeeksPreg = WeeksPreg;
	}
	
	/**
	 * Retrieves a String coded with information regarding the patient's prior pregnancies. All information
	 * fields are separated with a ":".
	 * 
	 * @return The String coded with information regarding the patient's prior pregnancies. 
	 */
	public String getPriorPregnancies(){
		return this.PriorPregnancies;
	}
	
	/**
	 * Sets the information regarding a patient's prior pregnancies.
	 * 
	 * @param PriorPregnancies A String coded with information which we want to set.
	 */
	public void setPriorPregnancies(String PriorPregnancies){
		if(this.PriorPregnancies.equals(""))
			this.PriorPregnancies += PriorPregnancies;
		else
			this.PriorPregnancies += ":" + PriorPregnancies;
	}
	
	/**
	 * Retrieves any notes regarding the patient's InitRecord.
	 * 
	 * @return A String containing notes regarding the patient.
	 */
	public String getNotes(){
		return Notes;
	}
	
	/**
	 * Replaces the notes contained within this patient's InitRecord with a given String.
	 * 
	 * @param Notes A String containing notes we want to use as replaced.
	 */
	public void setNotes(String Notes){
		this.Notes = Notes;
	}
	

}
