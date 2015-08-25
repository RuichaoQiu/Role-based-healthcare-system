package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.MessageFilterBean;
import edu.ncsu.csc.itrust.beans.loaders.MessageFilterBeanLoader;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageFilterDAO;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class MessageFilterTest extends TestCase{
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private MessageFilterDAO dao = new MessageFilterDAO(factory);

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		//action = new ViewInitRecordsHistoryAction(factory, "102", 1L);
	}
	
	
	public void testSaveMessageFilter() throws Exception {
		
		MessageFilterBean bean = new MessageFilterBean();
		bean.setMid(9000000000L);
		bean.setSender("Random Person");
		bean.setSubject("Appointment");
		bean.setContains("");
		bean.setNotContains("");
		bean.setStartDate("");
		bean.setEndDate("");
		
		boolean success = dao.saveMessageFilter(9000000000L, bean);
		assertEquals(true, success);	
	}
	
	public void testGetSavedMessageFilter() throws Exception {
		
		MessageFilterBean bean1 = new MessageFilterBean();
		bean1.setMid(9000000000L);
		bean1.setSender("Random Person");
		bean1.setSubject("Appointment");
		bean1.setContains("");
		bean1.setNotContains("");
		bean1.setStartDate("");
		bean1.setEndDate("");
		
		boolean success = dao.saveMessageFilter(9000000000L, bean1);
		assertEquals(true, success);	
		
		MessageFilterBean bean2 = dao.getSavedMessageFilter(9000000000L);
		assertEquals(bean2.getMid(), 9000000000L);	
		assertEquals(bean2.getSender(), "Random Person");
		assertEquals(bean2.getSubject(), "Appointment");
		assertEquals(bean2.getContains(), "");
		assertEquals(bean2.getNotContains(), "");	
		assertEquals(bean2.getStartDate(), "01/01/1970");
		assertEquals(bean2.getStartDate_forUser(), "");
		assertEquals(bean2.getEndDate(), "01/01/1970");
		assertEquals(bean2.getEndDate_forUser(), "");	
	}
	
public void testGetSavedMessageFilterWithContains() throws Exception {
		
		MessageFilterBean bean1 = new MessageFilterBean();
		bean1.setMid(9000000000L);
		bean1.setSender("");
		bean1.setSubject("");
		bean1.setContains("collecting");
		bean1.setNotContains("happiness");
		bean1.setStartDate("");
		bean1.setEndDate("01/01/2008");
		
		boolean success = dao.saveMessageFilter(9000000000L, bean1);
		assertEquals(true, success);	
		
		MessageFilterBean bean2 = dao.getSavedMessageFilter(9000000000L);
		assertEquals(bean2.getMid(), 9000000000L);	
		assertEquals(bean2.getSender(), "");
		assertEquals(bean2.getSubject(), "");
		assertEquals(bean2.getContains(), "collecting");
		assertEquals(bean2.getNotContains(), "happiness");	
		assertEquals(bean2.getStartDate(), "01/01/1970");
		assertEquals(bean2.getStartDate_forUser(), "");
		assertEquals(bean2.getEndDate(), "01/01/2008");
		assertEquals(bean2.getEndDate_forUser(), "01/01/2008");	
	}
	
	public void testGetSavedMessageFilterWithDate() throws Exception {
		
		MessageFilterBean bean1 = new MessageFilterBean();
		bean1.setMid(9000000000L);
		bean1.setSender("Bill Paterson");
		bean1.setSubject("");
		bean1.setContains("");
		bean1.setNotContains("");
		bean1.setStartDate("01/01/2010");
		bean1.setEndDate("01/01/2012");
		
		boolean success = dao.saveMessageFilter(9000000000L, bean1);
		assertEquals(true, success);	
		
		MessageFilterBean bean2 = dao.getSavedMessageFilter(9000000000L);
		assertEquals(bean2.getMid(), 9000000000L);	
		assertEquals(bean2.getSender(), "Bill Paterson");
		assertEquals(bean2.getSubject(), "");
		assertEquals(bean2.getContains(), "");
		assertEquals(bean2.getNotContains(), "");	
		assertEquals(bean2.getStartDate(), "01/01/2010");
		assertEquals(bean2.getStartDate_forUser(), "01/01/2010");
		assertEquals(bean2.getEndDate(), "01/01/2012");
		assertEquals(bean2.getEndDate_forUser(), "01/01/2012");	
	}
	
}




