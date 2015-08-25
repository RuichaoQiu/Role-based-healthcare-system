package edu.ncsu.csc.itrust.dao.transaction;

import java.sql.Timestamp;
import java.util.Date;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class TransactionDAOExceptionTest extends TestCase {
	private TransactionDAO evilDAO = EvilDAOFactory.getEvilInstance().getTransactionDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testGetAllAccessException() throws Exception {
		try {
			evilDAO.getAllRecordAccesses(0L, -1, false);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAllTransactionsException() throws Exception {
		try {
			evilDAO.getAllTransactions();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testgetTransactionsBetweenOfTypeException() throws Exception {
		try {
			evilDAO.getTransactionsBetweenOfType(new Timestamp(0), new Timestamp(1), TransactionType.ACCESS_LOG_VIEW);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}


	public void testRecordAccessesException() throws Exception {
		try {
			evilDAO.getRecordAccesses(0L, -1, new Date(), new Date(), false);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
