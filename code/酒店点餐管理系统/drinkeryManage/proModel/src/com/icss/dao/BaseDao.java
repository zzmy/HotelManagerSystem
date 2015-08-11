package com.icss.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 * 对BaseDao做了进一步的封装，将查询的结果集直接封装成数据对象
 * @author Administrator
 *
 */
public class BaseDao {
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
	private static final String USER = "hoteladm";
	private static final String PASSWORD = "123456";
	
	private Connection conn;
	private PreparedStatement pstm;
	private ResultSet rs;
	
	static{
		// 1 加载数据库驱动
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//2 连接
	private Connection getConnection()
	{	
		try {
			return DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 关闭全部
	 */
	private void closeAll()
	{
		try {
			if(rs!=null)
			{
				rs.close();
			}
			if(pstm!=null)
			{
				pstm.close();
			}
			if(conn!=null && !conn.isClosed())
			{
				conn.close();
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 执行INSERT  UPDATE DELETE 语句
	 * @param sql 
	 * 		SQL语句
	 * @param param  
	 * 		？所对应的值
	 * @return 
	 * 		影响的行数
	 * @throws SQLException
	 */
	public int executeUpdate(String sql,Object...param) throws SQLException
	{
		int rows = 0;
		try
		{
			conn = getConnection();
			//装载SQL
			pstm = conn.prepareStatement(sql);
			if(param != null)
			{
				//给 ? 参数赋值
				for(int i=0;i<param.length;i++)
				{
					pstm.setObject(i+1, param[i]);
				}
			}
			//执行SQL
			rows = pstm.executeUpdate();
		}finally{
			closeAll();
		}	
		return rows;
	}
	/**
	 * 查询单列单值数据：SUM COUNT MAX MIN AVG 
	 * @param sql
	 * @param param
	 * @return 单值对象
	 * @throws SQLException
	 */
	public Object executeScalar(String sql, Object... param) throws SQLException
	{
		Object obj = null;
		
		try{
			conn = getConnection();
			pstm = conn.prepareStatement(sql);
			if(param != null)
			{
				for(int i=0;i<param.length;i++)
				{
					pstm.setObject(i+1, param[i]);
				}
			}
			rs = pstm.executeQuery();
			if(rs.next())
			{
				obj = rs.getObject(1);
			}
		}finally{
			closeAll();
		}
		
		return obj;
	}
	/**
	 * 对查询结果集进行封装，将结果集封装为一个集合框架对象
	 * @param sql
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Vector<Vector<Object>> executeQuery(String sql, Object... param) throws SQLException
	{
		Vector<Vector<Object>> allData = new Vector<Vector<Object>>();		
		try{
			conn = getConnection();
			pstm = conn.prepareStatement(sql);
			if(param != null)
			{
				for(int i=0;i<param.length;i++) {
					pstm.setObject(i+1, param[i]);
				}
			}
			rs = pstm.executeQuery();
			//将每一条记录封装到一个Vector,在将Vector封装到allData
			int columnCount = rs.getMetaData().getColumnCount();//获得结果集的列数
			int rowNo = 1;	//设置默认行号从1开始
			while(rs.next())
			{
				Vector<Object> row = new Vector<Object>();
				row.add(rowNo++); //添加行号
				for(int col=1;col<=columnCount;col++)//取数据的行索引从1开始
				{
					row.add(rs.getObject(col));
				}
				allData.add(row);	//将一行记录数据添加到allData				
			}
		}finally{
			closeAll();
		}
		return allData;
	}
}
