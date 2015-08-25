package edu.ncsu.csc.itrust.beans.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A form to contain data coming from editing an office visit.
 * 
 * A form is a bean, kinda. You could say that it's a form of a bean :) 
 * Think of a form as a real-life administrative form that you would fill out to get 
 * something done, not necessarily making sense by itself.
 */
public class EditObstetricForm {
	private long visitID = -1;
	private String visitDate;
	private String weeksPreg;
	private String daysPreg;
	private String SBloodPressure;
	private String DBloodPressure;
	private String weight;
	private String FHR;
	private String FHU;
	private String PatientID;
	
	public long getID() {
		return visitID;
	}

	
	public void setID(long mid) {
		this.visitID = mid;
	}
	
	public String getvisitDateStr(){
		return visitDate;
	}
	
	public void setvisitDate(String visitDate){
		this.visitDate = visitDate;
	}

	public String getWeeksPreg() {
		return weeksPreg;
	}

	public void setWeeksPreg(String weeksPreg) {
		this.weeksPreg = weeksPreg;
	}

	public String getDaysPreg() {
		return daysPreg;
	}

	public void setDaysPreg(String daysPreg) {
		this.daysPreg = daysPreg;
	}

	public String getSBloodPressure() {
		return SBloodPressure;
	}

	public void setSBloodPressure(String SBloodPressure) {
		this.SBloodPressure = SBloodPressure;
	}
	
	public String getDBloodPressure() {
		return DBloodPressure;
	}

	public void setDBloodPressure(String DBloodPressure) {
		this.DBloodPressure = DBloodPressure;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFHR() {
		return FHR;
	}

	public void setFHR(String fHR) {
		this.FHR = fHR;
	}

	public String getFHU() {
		return FHU;
	}

	public void setFHU(String FHU) {
		this.FHU = FHU;
	}
	
	public String getPatientID(){
		return PatientID;
	}
	
	public void setPatientID(String PatientID){
		this.PatientID = PatientID;
	}
	
}
