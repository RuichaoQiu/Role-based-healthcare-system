package edu.ncsu.csc.itrust.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.dao.mysql.ObstericVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Test all office visit by doctors
 */
public class EditObstetricActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditObstetricOfficeVisitAction action;
	private EditObstetricOfficeVisitAction Evilaction;
	//private EditOfficeVisitAction actionUC60; //UC60

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.uc64testdata1();
		
		action = new EditObstetricOfficeVisitAction(factory, 9800000022L, "1", "1");
		
		//actionUC60 = new EditOfficeVisitAction(factory, 9000000011L, "311", "3"); //UC60
	}

	
	/**
	 * testOVID
	 */
	public void testOVID() {
		try {
			action = new EditObstetricOfficeVisitAction(factory, 0L, "1", "NaN");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Office Visit ID is not a number: For input string: \"NaN\"", e.getMessage());
		}
	}

	/**
	 * testEvilDatabase
	 */
	public void testEvilDatabase() {
		try {
			action = new EditObstetricOfficeVisitAction(EvilDAOFactory.getEvilInstance(), 0L, "1", "1");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", e
							.getMessage());
			DBException dbe = (DBException) e;
			assertEquals(EvilDAOFactory.MESSAGE, dbe.getSQLException().getMessage());
		}
	}
	
	/**
	 * testOVDoesntExist
	 
	public void testOVDoesntExist() {
		try {
			action = new EditObstetricOfficeVisitAction(TestDAOFactory.getTestInstance(), 0L, "1", "158");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Office Visit 158 with Patient MID 1 does not exist", e.getMessage());
		}
	}

	*/
	/**
	 * Test patient office visit
	 * @throws ITrustException
	 */
	public void testGetOfficeVisit() throws ITrustException {
		ObstericsBean ovb = action.getOfficeVisit();
		assertEquals(1L, action.getOvID());
		
		assertEquals("2014-11-11", ovb.getvisitDateStr());
		
	}

	

	/**
	 * Test if patient information is updated
	 * @throws FormValidationException
	 */
	public void testUpdateInformation() throws ITrustException, FormValidationException {
		EditObstetricForm frm = new EditObstetricForm();
		ObstericsBean ovb = action.getOfficeVisit();
		frm.setID(ovb.getID());
		frm.setvisitDate(ovb.getvisitDateStr());
		frm.setDaysPreg(ovb.getDaysPreg());
		frm.setWeeksPreg(ovb.getWeeksPreg());
		frm.setWeight(ovb.getWeight());
		frm.setSBloodPressure(ovb.getSBloodPressure());
		frm.setDBloodPressure(ovb.getDBloodPressure());
		frm.setFHR(ovb.getFHR());
		frm.setFHU("200");
		frm.setPatientID(Long.toString(ovb.getPatientID()));
		try {
			action.updateInformation(frm);
			action.logOfficeVisitEvent(TransactionType.OBSTETRIC_OFFICE_VISIT_EDIT);
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		ObstericsBean ovbNew = action.getOfficeVisit();
		assertEquals("200", ovbNew.getFHU());
	}
	
	/**
	 * testUpdateInformationNewOfficeVisit
	 * @throws Exception
	 */
	public void testUpdateInformationNewOfficeVisit() throws Exception {
		action = new EditObstetricOfficeVisitAction(factory, 9800000022L, "1");
		assertEquals(true, action.isUnsaved());
		assertEquals(-1, action.getOvID());
		EditObstetricForm frm = new EditObstetricForm();
		frm.setvisitDate("2014-11-25");
		frm.setDaysPreg("5");
		frm.setWeeksPreg("5");
		frm.setWeight("200.8");
		frm.setSBloodPressure("99");
		frm.setDBloodPressure("99");
		frm.setFHR("101");
		frm.setFHU("101");
		frm.setPatientID("1");
		try {
			action.updateInformation(frm);
			action.logOfficeVisitEvent(TransactionType.OBSTETRIC_OFFICE_VISIT_CREATE);
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		assertEquals(false, action.isUnsaved());
		assertFalse(-1 == action.getOvID());
	}
	
	public void testCheckValid() throws ITrustException {
		assertEquals(true, action.checkvalid());
	}
	
	
}
