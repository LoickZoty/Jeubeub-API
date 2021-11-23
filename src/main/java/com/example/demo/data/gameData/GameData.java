package com.example.demo.data.gameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.example.demo.game.Game;
import com.example.demo.game.Morpion;

@Repository
public class GameData implements GameDataInterface {
	public static HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	
	static {
		games.put(0, new Morpion(new ArrayList<String>(Arrays.asList("10", "11"))));
	}
	
	@Override
	public void push(Game game) {
		games.put(game.getId(), game);
	}

	@Override
	public void remove(int id) {
		games.remove(id);
	}

	@Override
	public Game getGame(int id) {
		if (games.containsKey(id))
			return games.get(id);
		return null;
	}
}
