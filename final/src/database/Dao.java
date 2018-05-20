package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Item;
import entity.Recommend;
import entity.Request;

public class Dao {

	private void endCon(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveItems(ArrayList<Item> items, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			for (int k = 0; k < items.size(); k++) {
				Item i = items.get(k);
				String sql = "insert into item values (?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, i.getName());
				ps.setInt(2, i.getPage());
				ps.setString(3, i.getUrl());
				ps.setInt(4, 0);

				ps.execute();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void saveDetail(Item i, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into detail values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // 17
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getPage());
			ps.setString(3, i.getUrl());
			ps.setString(4, i.getDescription());
			ps.setString(5, i.getFeatures());
			ps.setString(6, i.getReview());
			ps.setString(7, i.getReview5());
			ps.setString(8, i.getReviewNum());
			ps.setString(9, i.getTotalDownload());
			ps.setString(10, i.getWeekDownload());
			ps.setString(11, i.getHomePage());
			ps.setString(12, i.getUpdate());
			ps.setString(13, i.getCatalog());
			ps.setString(14, i.getRegister());
			ps.setString(15, i.getAudiences());
			ps.setString(16, i.getInterfaces());
			ps.setString(17, i.getLanguages());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void updateTickets(Item i, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "update item set tickets=? where name=? and page=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, i.getTickets());
			ps.setString(2, i.getName());
			ps.setInt(3, i.getPage());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void saveRequest(Request r, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into request values (?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, r.getName());
			ps.setInt(2, r.getPage());
			ps.setInt(3, r.getNum());
			ps.setString(4, r.getLabels());
			ps.setString(5, r.getPriority());
			ps.setString(6, r.getCreatedTime());
			ps.setString(7, r.getStatus());
			ps.setString(8, r.getContent());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void saveRecommend(Recommend r, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into recommend values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, r.getHost());
			ps.setInt(2, r.getPage());
			ps.setString(3, r.getUrl());
			ps.setString(4, r.getR1name());
			ps.setString(5, r.getR1url());
			ps.setString(6, r.getR2name());
			ps.setString(7, r.getR2url());
			ps.setString(8, r.getR3name());
			ps.setString(9, r.getR3url());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public ResultSet query(String sql, String password) {
		System.out.println("query");
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		ResultSet rs = jdbc.query(con, sql);
		return rs;
	}

	public void delete(Item i, String password) {
		System.out.println("delete" + i.getName());
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "delete from recommend where host=? and page=? and url=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getPage());
			ps.setString(3, i.getUrl());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void reDetail(Item i, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into reDetail values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getPage());
			ps.setString(3, i.getUrl());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void reTickets(Item i, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into reTickets values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getPage());
			ps.setString(3, i.getUrl());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

	public void reRequest(Request r, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into reRequest2 values (?,?,?,?)";
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

	public void reRecommend(Item i, String password) {
		Jdbc jdbc = new Jdbc();
		Connection con = jdbc.connect(password);
		try {
			String sql = "insert into reRecommend values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getPage());
			ps.setString(3, i.getUrl());

			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		endCon(con);
	}

}
