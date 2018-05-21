package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Applyinfo;
public class BaseDao {
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/apply_info";
	private String user="root";
	private String password="root";
	private Connection conn=null;
	List<Applyinfo> info =new ArrayList<Applyinfo>();
	//创建连接
	public Connection getconnection()
	{
		try {
			getClass().forName(driver);
			conn=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//增删改查方法
	public int addgame(String sql,Object [] obj)
	{
		int result=0;
		conn=getconnection();
	    PreparedStatement pr=null;
	    try {
			pr=conn.prepareStatement(sql);
			if(obj!=null)
			{
				for(int i=0;i<obj.length;i++)
				{
					pr.setObject(i+1, obj[i]);
				}
			}
			result=pr.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	    
	}//查询方法
	public List<Applyinfo> classinfo(String sql)
	{
		info.clear();
		conn=getconnection();
		PreparedStatement pr=null;
		ResultSet resu=null;
		try {
			pr=conn.prepareStatement(sql);
			resu=pr.executeQuery();
			while(resu.next())
			{
				Applyinfo aly=new Applyinfo();
				aly.setApplyid(resu.getInt("applyId"));
				aly.setName(resu.getString("name"));
				aly.setAge(resu.getInt("age"));
				aly.setGame(resu.getString("game"));
				aly.setApplyclass(resu.getString("class"));
				info.add(aly);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
		
	}
}
