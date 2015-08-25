package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.ObstericsBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.loaders.DiagnosisBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.ObstericsLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for doing tasks related to office visits. Use this for linking diagnoses to office visits, and similar
 * tasks.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *  
 * 
 */
@SuppressWarnings("unused")
public class ObstericVisitDAO {
	private DAOFactory factory;
	private ObstericsLoader ObLoader = new ObstericsLoader();
	/*private PrescriptionBeanLoader prescriptionLoader = new PrescriptionBeanLoader();
	private PrescriptionReportBeanLoader prescriptionReportBeanLoader = new PrescriptionReportBeanLoader();
	private ProcedureBeanLoader procedureBeanLoader = new ProcedureBeanLoader(true);*/

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public ObstericVisitDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Adds an visit and return its ID
	 * 
	 * @param ov The OfficeVisitBean to be added.
	 * @return A long indicating the unique ID for the office visit.
	 * @throws DBException
	 */
	public long add(ObstericsBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO Obsterics (visitDate, daysPreg, weeksPreg, weight, SBloodPressure,DBloodPressure, FHR, FHU, PatientID) VALUES (?,?,?,?,?,?,?,?,?)");
			setValues(ps, ov);
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Sets the values of a given PreparedStatement according to the given ObstericsBean.
	 * 
	 * @param ps The PreparedStatement we are to manipulate.
	 * @param ov The ObstericsBean containg the information we want to read.
	 * @throws SQLException
	 */
	private void setValues(PreparedStatement ps, ObstericsBean ov) throws SQLException {
		ps.setDate(1, new java.sql.Date(ov.getvisitDate().getTime()));
		ps.setString(2, ov.getDaysPreg());
		ps.setString(3, ov.getWeeksPreg());
		ps.setString(4, ov.getWeight());
		ps.setString(5, ov.getSBloodPressure());
		ps.setString(6, ov.getDBloodPressure());
		ps.setString(7, ov.getFHR());
		ps.setString(8, ov.getFHU());
		ps.setLong(9, ov.getPatientID());
	}

	/**
	 * Updates the information in a particular office visit.
	 * 
	 * @param ov The Office Visit bean representing the changes.
	 * @throws DBException
	 */
	public void update(ObstericsBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE Obsterics SET visitDate=?,daysPreg=?, weeksPreg=?, weight=?, SBloodPressure=?, DBloodPressure=?, FHR=?,FHU=?,PatientID=? WHERE ID=?");
			setValues(ps, ov);
			ps.setLong(10, ov.getID());
			System.out.print(ov.getID());
			System.out.print(ov.getID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a particular office visit given an ID
	 * 
	 * @param visitID The unique ID of the office visit.
	 * @return An OfficeVisitBean with the specifics for that office visit.
	 * @throws DBException
	 */
	public ObstericsBean getOfficeVisit(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From Obsterics Where ID = ?");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				ObstericsBean result = ObLoader.loadSingle(rs); 
				rs.close();
				ps.close();
				return result;
			}
			else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of all office visits for a given patient
	 * 
	 * @param pid The MID of the patient in question.
	 * @return A java.util.List of OfficeVisitBeans.
	 * @throws DBException
	 */
	public List<ObstericsBean> getAllOfficeVisits(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM Obsterics WHERE PatientID=? ORDER BY visitDate DESC");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ObstericsBean> loadlist = ObLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns whether or not an office visit actually exists
	 * 
	 * @param ovID The ID of the office visit to be checked.
	 * @param pid The MID of the patient associated with this transaction.
	 * @return A boolean indicating its existence.
	 * @throws DBException
	 */
	public boolean checkOfficeVisitExists(long ovID, long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Obsterics WHERE ID=? AND PatientID=?");
			ps.setLong(1, ovID);
			ps.setLong(2, pid);
			ResultSet rs = ps.executeQuery();
			boolean check = rs.next();
			rs.close();
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
}
