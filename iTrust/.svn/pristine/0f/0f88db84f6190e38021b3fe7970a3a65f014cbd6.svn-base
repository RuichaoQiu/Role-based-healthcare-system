package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DeathStatsBean;
import edu.ncsu.csc.itrust.beans.loaders.DeathStatsBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.exception.DBException;
/**
 * This DAO retrieves death statics from the database
 * @author bllevy2,dxchen3 
 *
 */
public class DeathStatsDAO {
	private DAOFactory factory;
	private DeathStatsBeanLoader deathStatsBeanLoader;

/**
 *  Constructor for the dao
 * @param factory
 */
	public DeathStatsDAO(DAOFactory factory) {
		this.factory = factory;
		deathStatsBeanLoader = new DeathStatsBeanLoader();
	}

	private String makeTopCausesString(String gender, String limit, String hcp) {
		String statement = "Select COD.CauseOfDeath, icdcodes.Description, COD.numberDead "
				+ "from (Select CauseOfDeath, count(*) as numberDead "
				+ "from patients "
				+ "join declaredhcp on declaredhcp.PatientID = patients.MID "
				+ "where DateOfDeath is not null "
				+ "and DateOfDeath > date(?) and DateOfDeath < date(?) "
				+ hcp
				+ gender
				+ "group by CauseOfDeath) as COD "
				+ "join icdcodes on COD.CauseOfDeath = icdcodes.Code "
				+ "order by numberDead Desc " + limit;
		return statement;
	}
	/**
	 * Builds the SQL query and runs it
 	 * @param hcpMID This is the mid of the hcp to find patients from, if 
	 * @param gender of the patient, this is an Enum
	 * @param startDate the year to start from 01/01/<startDate>
	 * @param endDate the year to end the search on 12/31/<endDate>
	 * @param limit the number of results to return
	 * @return the data from the db in the from of a List<DeathStatsBean>
	 * @throws DBException
	 */
	public List<DeathStatsBean> getTopCauses(long hcpMID, Gender gender, int startDate,
			int endDate, int limit) throws DBException {
		List<DeathStatsBean> deathStats;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			String genderString = "";
			if(gender == Gender.Female )
				genderString = "and patients.Gender = \"Female\" ";
			else if(gender == Gender.Male)
				genderString = "and patients.Gender = \"Male\" ";
			String limitString = ";";
			if(limit > 0)
				limitString = "limit ?;";
			String hcp = "";
			if(hcpMID > 0L)
				hcp = "and HCPID = ? ";
			String statement = makeTopCausesString(genderString, limitString, hcp);
			ps = conn.prepareStatement(statement);
			int i=1;
			ps.setString(i++, startDate + "-1-1");
			ps.setString(i++, (endDate) + "-12-31");
			if(hcpMID > 0L)
				ps.setLong(i++, hcpMID);
			if (limit > 0)
				ps.setInt(i++, limit);
			rs = ps.executeQuery();
			deathStats = deathStatsBeanLoader.loadList(rs);
			rs.close();
			return deathStats;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
