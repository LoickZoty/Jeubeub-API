package com.example.demo.data.shopData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ShopData implements ShopDataInterface {
	private String url = "jdbc:mysql://localhost:3306/jeubeub";
	private String username = "root";
	private String password = "";
	
	private Connection con;
	
	public ShopData() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean pushItemPlayer(String playerId, int itemId, int itemQuantity) throws SQLException {
		String query = ("SELECT count(*) AS rowcount FROM user_items WHERE user_items.item_id = ? and user_items.user_id = ?");
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setInt(1, itemId);
		preparedStatement.setString(2, playerId);
		
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			if (rs.getInt("rowcount") == 0) {
				query = "INSERT INTO user_items (quantity, item_id, user_id) VALUES (?,?,?)";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, itemQuantity);
				preparedStatement.setInt(2, itemId);
				preparedStatement.setString(3, playerId);
				if (preparedStatement.executeUpdate() == 1) return true;
				
			} else {
				query = "UPDATE user_items set quantity = quantity + ? where user_items.user_id = ? and user_items.item_id = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, itemQuantity);
				preparedStatement.setString(2, playerId);
				preparedStatement.setInt(3, itemId);
				if (preparedStatement.executeUpdate() == 1) return true;
			}
		}
		return false;
	}
	
	@Override
	public ArrayList<Map<String, Object>> displayItemsPlayer(String playerId) throws SQLException {
		String query = "SELECT shop_items.name, user_items.quantity FROM user_items JOIN shop_items ON shop_items.id = user_items.item_id WHERE user_id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setString(1, playerId);
		
		ResultSet rs = preparedStatement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		
		ArrayList<Map<String, Object>> objects = new ArrayList<Map<String, Object>>();
		while(rs.next()) {
			Map<String, Object> object = new HashMap<String, Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i);
				object.put(columnName, rs.getObject(columnName));
			}
			objects.add(object);
		}	
		return objects;
	}
	
	public ArrayList<Map<String, Object>> displayItems() throws SQLException {
		Statement state = con.createStatement();
		
		ResultSet rs = state.executeQuery("SELECT * FROM shop_items");
		ResultSetMetaData rsmd = rs.getMetaData();
				
		ArrayList<Map<String, Object>> objects = new ArrayList<Map<String, Object>>();
		while(rs.next()) {
			Map<String, Object> object = new HashMap<String, Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i);
				object.put(columnName, rs.getObject(columnName));
			}
			objects.add(object);
		}	
		return objects;
	}
}
