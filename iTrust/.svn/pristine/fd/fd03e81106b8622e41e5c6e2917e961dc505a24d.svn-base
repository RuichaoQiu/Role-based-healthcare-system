package edu.ncsu.csc.itrust.action;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddObstetricActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddObstetricAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.uc64testdata1();
		action = new AddObstetricAction(factory, "1");
	}

	public void testGetOfficeVisits() throws Exception {
		
		List<ObstericsBean> ovs = action.getAllOfficeVisits();
		assertEquals(ovs.size(), 1);
	}
	
	public void testGetUserName() throws Exception {
		assertEquals("Random Person", action.getUserName());
	}
}
