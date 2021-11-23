package com.example.demo.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.gameData.GameData;
import com.example.demo.data.shopData.ShopData;

@RestController
@RequestMapping(value="/Jeubeub/api/v1/shop")
public class ControllerShop {
	protected ShopData shopData;
    
	public ControllerShop(ShopData shopData) {
		this.shopData = shopData;
	}
	
	@GetMapping("/pushItemPlayer")
	public ResponseEntity<Object> pushItemPlayer(@RequestParam("playerId") String playerId, @RequestParam("itemId") int itemId, @RequestParam("itemQuantity") int itemQuantity) throws SQLException {
		if (shopData.pushItemPlayer(playerId, itemId, itemQuantity)) return new ResponseEntity<>("{\"message\" = \"success\"}", HttpStatus.OK);
		else return new ResponseEntity<>("{\"message\" = \"error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/displayItemsPlayer")
	public Map<String, Object> displayItemsPlayer(@RequestParam("playerId") String playerId) throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", shopData.displayItemsPlayer(playerId));
		return map;
	}
}
