package com.example.demo.data.shopData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ShopDataInterface {
	public boolean pushItemPlayer(String playerId, int itemId, int itemQuantity) throws SQLException;
	
	public ArrayList<Map<String, Object>> displayItemsPlayer(String playerId) throws SQLException;
}
