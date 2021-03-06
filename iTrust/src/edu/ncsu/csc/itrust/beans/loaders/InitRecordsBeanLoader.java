package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.InitRecord;

/**
 * A loader for initialRecords.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class InitRecordsBeanLoader implements BeanLoader<InitRecord> {
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadList(java.sql.ResultSet)
	 */
	public List<InitRecord> loadList(ResultSet rs) throws SQLException {
		ArrayList<InitRecord> list = new ArrayList<InitRecord>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadParameters(java.sql.PreparedStatement, java.lang.Object)
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, InitRecord bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getMid());
		ps.setString(i++, bean.getLastname());
		ps.setString(i++, bean.getFirstname());
		ps.setDate(i++, new java.sql.Date(bean.getLMP().getTime()));
		ps.setDate(i++, new java.sql.Date(bean.getEDD().getTime()));
		ps.setString(i++, bean.getWeeksPreg());
		ps.setString(i++, bean.getPriorPregnancies());
		ps.setString(i++, bean.getNotes());
		
		return ps;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadSingle(java.sql.ResultSet)
	 */
	public InitRecord loadSingle(ResultSet rs) throws SQLException {
		InitRecord ir = new InitRecord();
		ir.setMid(rs.getInt("mid"));
		ir.setLastname(rs.getString("lastName"));
		ir.setFirstname(rs.getString("firstName"));
		ir.setLMP(new SimpleDateFormat("yyyy-MM-dd").format(new Date(rs.getDate("LMP").getTime())));
		ir.setEDD(new SimpleDateFormat("yyyy-MM-dd").format(new Date(rs.getDate("EDD").getTime())));
		ir.setWeeksPreg(rs.getString("weeksPreg"));
		ir.setPriorPregnancies(rs.getString("PriorPregnancies"));
		ir.setNotes(rs.getString("Notes"));
		return ir;
	}
}
