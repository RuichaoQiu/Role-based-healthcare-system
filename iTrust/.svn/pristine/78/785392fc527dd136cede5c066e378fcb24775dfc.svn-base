package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.MessageFilterBean;

/**
 * A loader for messageFilterBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
/**
 * @author Nick
 *
 */
public class MessageFilterBeanLoader implements BeanLoader<MessageFilterBean> {
	
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadList(java.sql.ResultSet)
	 */
	public List<MessageFilterBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<MessageFilterBean> list = new ArrayList<MessageFilterBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadParameters(java.sql.PreparedStatement, java.lang.Object)
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, MessageFilterBean bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getMid());
		ps.setString(i++, bean.getSender());
		ps.setString(i++, bean.getSubject());
		ps.setString(i++, bean.getContains());
		ps.setString(i++, bean.getNotContains());
		ps.setDate(i++, new java.sql.Date(bean.getStartDate_asDate().getTime()));
		ps.setDate(i++, new java.sql.Date(bean.getEndDate_asDate().getTime()));
		
		return ps;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.loaders.BeanLoader#loadSingle(java.sql.ResultSet)
	 */
	public MessageFilterBean loadSingle(ResultSet rs) throws SQLException {
		MessageFilterBean bean = new MessageFilterBean();
		bean.setMid(rs.getLong("mid"));
		bean.setSender(rs.getString("sender"));
		bean.setSubject(rs.getString("subject"));
		bean.setContains(rs.getString("contains"));
		bean.setNotContains(rs.getString("notContains"));
		
		SimpleDateFormat inputFormatter = new SimpleDateFormat("MM/dd/yyyy"); //format for reading in from the form

		
		bean.setStartDate(inputFormatter.format(rs.getDate("startDate")));
		bean.setEndDate(inputFormatter.format(rs.getDate("endDate")));

		return bean;
	}
}
