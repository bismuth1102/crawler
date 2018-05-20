package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Request;

public class Dao {

	   private void endCon(Connection con){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   
	   
	   public void updateRequest(Request r, String password){
		   Jdbc jdbc = new Jdbc();
		   Connection con = jdbc.connect(password);
		   try {
			   String sql = "update request set summary=? where name=? and page=? and num=?"; 
			   PreparedStatement ps = con.prepareStatement(sql);
			   ps.setString(1, r.getSummary());
			   ps.setString(2, r.getName());
			   ps.setInt(3, r.getPage());
			   ps.setInt(4, r.getNum());
			   
			   ps.execute();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		   
		   endCon(con);
	   }
	   
	   
	   public ResultSet query(String sql, String password){
		   System.out.println("query");
		   Jdbc jdbc = new Jdbc();
		   Connection con = jdbc.connect(password);
		   ResultSet rs = jdbc.query(con, sql);
		   return rs;
	   }
	   
	   
	   public void reRequest(Request r, String password) {
		   Jdbc jdbc = new Jdbc();
		   Connection con = jdbc.connect(password);
		   try {
			   String sql = "insert into reRequest values (?,?,?,?)"; 
			   PreparedStatement ps = con.prepareStatement(sql);
			   ps.setString(1, r.getName());
			   ps.setInt(2, r.getPage());
			   ps.setInt(3, r.getNum());
			   ps.setString(4, r.getRequestUrl());
				   
			   ps.execute();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		   
		   endCon(con);
	   }
	   
}
