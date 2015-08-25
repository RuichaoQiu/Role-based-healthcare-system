package edu.ncsu.csc.itrust.dao.deathstats;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DeathStatsDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class DeathStatsDAOTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DeathStatsDAO deathStatsDAO = factory.getDeathStatsDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.deadRecurringPatients();	
	}
	
	public void testGetTopCause() throws Exception{
		deathStatsDAO.getTopCauses(9000000003L, Gender.Male, 2004, 2011, 2);
		deathStatsDAO.getTopCauses(9000000003L, Gender.Female, 2008, 2011, 2);
		deathStatsDAO.getTopCauses(9000000003L, Gender.NotSpecified, 2011, 2010, 2);
	}
	
	public void testInvalidLimit() throws Exception{
		deathStatsDAO.getTopCauses(9000000003L, Gender.Male, 2004, 2010, -1);
	}
}
