package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.DeathStatsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class viewDeathStatsActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewDeathStatsAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
	/**
	 * testing normal action Initialization
	 * @throws Exception
	 */
	public void testNormalInitialization() throws Exception {
		gen.standardData();
		action = new ViewDeathStatsAction(factory);
	}
	/**
	 * test getting list of all claims
	 */
	public void testGetClaims() throws Exception{
		gen.standardData();
		action = new ViewDeathStatsAction(factory);
		
		List<DeathStatsBean> list = action.getStats(9000000003L, Gender.Male, 1900, 2014, 100);
		assertEquals(2, list.size());

	}
	/**
	 * test output on specific query
	 */
	public void testHCP1() throws Exception{
		gen.standardData();
		action = new ViewDeathStatsAction(factory);
		
		List<DeathStatsBean> list = action.getStats(9000000003L, Gender.Male, 1900, 2014, 100);
		assertEquals(list.get(0).getDescription(), "Acute Lycanthropy");
		assertEquals(list.get(1).getDescription(), "Diabetes with ketoacidosis");
	}
}