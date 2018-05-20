package test;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.Dao;
import entity.Item;

public class DeleteFromRecommend {

	Dao dao = new Dao();

	public static void main(String args[]) {
		DeleteFromRecommend test = new DeleteFromRecommend();
		test.delete("951102ljc");
	}

	// 从table中选择limit a-b行，做成list
	private ArrayList<Item> selectFromTable(String password) {
		ArrayList<Item> items = new ArrayList<Item>();

		try {
			ResultSet rs = dao.query("select * from reRecommend order by page ASC;", password);
			while (rs.next()) {
				String name = rs.getString(1);
				int page = rs.getInt(2);
				String url = rs.getString(3);

				Item i = new Item();
				i.setName(name);
				i.setPage(page);
				i.setUrl(url);

				items.add(i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;

	}

	private void delete(String password) {
		ArrayList<Item> items = selectFromTable(password);
		for (int k = 0; k < items.size(); k++) {
			Item i = items.get(k);
			dao.delete(i, password);
		}
	}

}