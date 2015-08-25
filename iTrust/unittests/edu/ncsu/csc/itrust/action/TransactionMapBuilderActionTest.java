package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;
	
public class TransactionMapBuilderActionTest extends TestCase{
	private List <TransactionBean> list;
	private TransactionMapBuilderAction mapper;
	private TestDataGenerator gen;
	
	protected void setUp() throws Exception {	
		
		//Set up user database
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog2();
		gen.patient2();
		gen.hcp0();
		gen.er4();
		gen.hcp3();
		
		list = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		mapper = new TransactionMapBuilderAction(list, TestDAOFactory.getTestInstance().getAuthDAO());
	}
	
	public void testHashForType() throws Exception{
		HashMap <String, Integer> map = mapper.mapTypes();
		assertEquals(1 , map.get(TransactionType.RISK_FACTOR_VIEW.name()).intValue());
		assertEquals(1 , map.get(TransactionType.OFFICE_VISIT_EDIT.name()).intValue());
		assertEquals(1 , map.get(TransactionType.PRESCRIPTION_REPORT_VIEW.name()).intValue());
		assertEquals(1 , map.get(TransactionType.EMERGENCY_REPORT_VIEW.name()).intValue());
	}
	
	public void testHashForLogMID() throws Exception{
		HashMap <String, Integer> map = mapper.mapLoggedIn();
		assertEquals(3 , map.get("hcp").intValue());
		assertEquals(1 , map.get("er").intValue());	 	
	}
	
	public void testHashForSecondaryMID() throws Exception{
		HashMap <String, Integer> map = mapper.mapSecondary();
		assertEquals(4 , map.get("patient").intValue());
	}
	
	public void testHashForDate() throws Exception{
		HashMap <String, Integer> map = mapper.mapDates();
		assertEquals(1 , map.get("3/2008").intValue());
		assertEquals(1 , map.get("9/2008").intValue());
		assertEquals(1 , map.get("7/2008").intValue());
		assertEquals(1 , map.get("11/2008").intValue());
	}
	


}

