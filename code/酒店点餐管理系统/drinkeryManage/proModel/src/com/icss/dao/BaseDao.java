package com.icss.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 * ��BaseDao���˽�һ���ķ�װ������ѯ�Ľ����ֱ�ӷ�װ�����ݶ���
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
		// 1 �������ݿ�����
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//2 ����
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
	 * �ر�ȫ��
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
	 * ִ��INSERT  UPDATE DELETE ���
	 * @param sql 
	 * 		SQL���
	 * @param param  
	 * 		������Ӧ��ֵ
	 * @return 
	 * 		Ӱ�������
	 * @throws SQLException
	 */
	public int executeUpdate(String sql,Object...param) throws SQLException
	{
		int rows = 0;
		try
		{
			conn = getConnection();
			//װ��SQL
			pstm = conn.prepareStatement(sql);
			if(param != null)
			{
				//�� ? ������ֵ
				for(int i=0;i<param.length;i++)
				{
					pstm.setObject(i+1, param[i]);
				}
			}
			//ִ��SQL
			rows = pstm.executeUpdate();
		}finally{
			closeAll();
		}	
		return rows;
	}
	/**
	 * ��ѯ���е�ֵ���ݣ�SUM COUNT MAX MIN AVG 
	 * @param sql
	 * @param param
	 * @return ��ֵ����
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
	 * �Բ�ѯ��������з�װ�����������װΪһ�����Ͽ�ܶ���
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
			//��ÿһ����¼��װ��һ��Vector,�ڽ�Vector��װ��allData
			int columnCount = rs.getMetaData().getColumnCount();//��ý����������
			int rowNo = 1;	//����Ĭ���кŴ�1��ʼ
			while(rs.next())
			{
				Vector<Object> row = new Vector<Object>();
				row.add(rowNo++); //����к�
				for(int col=1;col<=columnCount;col++)//ȡ���ݵ���������1��ʼ
				{
					row.add(rs.getObject(col));
				}
				allData.add(row);	//��һ�м�¼������ӵ�allData				
			}
		}finally{
			closeAll();
		}
		return allData;
	}
}
