package edu.ncsu.csc.itrust.dao.officevisit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditObstetricOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricForm;
import edu.ncsu.csc.itrust.dao.mysql.ObstericVisitDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import java.util.List;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.exception.DBException;

public class ObstericVisitDAOTest extends TestCase {
	private ObstericVisitDAO ovDAO = TestDAOFactory.getTestInstance().getObstericVisitDAO();
	private ObstericVisitDAO evilDAO = EvilDAOFactory.getEvilInstance().getObstericVisitDAO();
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc64testdata1();
	}

	public void testAddNewOfficeVisit() throws Exception {
		ObstericsBean frm = new ObstericsBean();
		frm.setvisitDate("2014-11-25");
		frm.setDaysPreg("5");
		frm.setWeeksPreg("5");
		frm.setWeight("200.8");
		frm.setSBloodPressure("99");
		frm.setDBloodPressure("99");
		frm.setFHR("101");
		frm.setFHU("101");
		frm.setPatientID(1);
		assertEquals(2,ovDAO.add(frm));
		try {
			evilDAO.add(frm);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testUpdateNewOfficeVisit() throws Exception {
		ObstericsBean frm = new ObstericsBean(1);
		frm.setvisitDate("2014-11-25");
		frm.setDaysPreg("5");
		frm.setWeeksPreg("5");
		frm.setWeight("200.8");
		frm.setSBloodPressure("99");
		frm.setDBloodPressure("99");
		frm.setFHR("101");
		frm.setFHU("101");
		frm.setPatientID(1);
		ovDAO.update(frm);
		ObstericsBean ov = ovDAO.getOfficeVisit(1);
		assertEquals("2014-11-25",ov.getvisitDateStr());
		try {
			evilDAO.update(frm);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testgetOfficeVisit() throws Exception {
		ObstericsBean ov = ovDAO.getOfficeVisit(1);
		assertEquals("2014-11-11",ov.getvisitDateStr());
		try {
			evilDAO.getOfficeVisit(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testgetAllOfficeVisit() throws Exception {
		List<ObstericsBean> ov = ovDAO.getAllOfficeVisits(1);
		assertEquals(1,ov.size());
		try {
			evilDAO.getAllOfficeVisits(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testcheckOfficeVisitexist() throws Exception {
		assertEquals(true,ovDAO.checkOfficeVisitExists(1,1));
		try {
			evilDAO.checkOfficeVisitExists(1,1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	
}
