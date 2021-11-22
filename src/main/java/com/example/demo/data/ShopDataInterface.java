package com.example.demo.data;

public interface ShopDataInterface {
	public void push(int idPlayer, int idItem, int quantity);
	
	public void getItems(int idPlayer);
}
