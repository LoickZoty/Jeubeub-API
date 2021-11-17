package com.example.demo.data;

import com.example.demo.game.Game;

public interface GameDataInterface {
	public void push(Game game);
	public void remove(int id);
	public Game getGame(int id);
}
