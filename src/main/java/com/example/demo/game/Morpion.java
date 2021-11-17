package com.example.demo.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Morpion extends Game {
	private final static int X = 0;
	private final static int O = 1;
	private final static int E = 2;
	
	private ArrayList<ArrayList<Integer>> map;
	
	public Morpion(ArrayList<Integer> players) {
		super(players);
		this.initializeMap();
	}
	
	public ResponseEntity<Object> dropMarker(int playerId, int x, int y) {
		if (this.players.contains(playerId)) {
			if (this.isMyTurn(playerId)) {
				if (map.get(x).get(y) == E) {
					map.get(x).set(y, this.getMarkerByPlayer(playerId));
					this.nextPlayer();
					this.synchronizePlayers.initializePlayersSync(players);
					this.finishGame();
					return new ResponseEntity<>("Success", HttpStatus.OK);
				}
				return new ResponseEntity<>("Position already marked", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>("Not your turn", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>("Not your game", HttpStatus.UNAUTHORIZED);
	}
	
	public int getMarkerByPlayer(int playerId) {
		if (this.players.get(0) == playerId) return X;
		else return O;
	}

	@Override
	public Map<String,Object> getActualGameData() {
		HashMap<String,Object> data = new HashMap<String,Object>();
		data.put("map", map);
		return data;
	}
	
	private void initializeMap() {
		map = new ArrayList<ArrayList<Integer>>();
		map.add(new ArrayList<Integer>(Arrays.asList(E, X, X)));
		map.add(new ArrayList<Integer>(Arrays.asList(E, E, E)));
		map.add(new ArrayList<Integer>(Arrays.asList(E, E, E)));
	}
	
	private void initializeRanking(int winner) {
		if (winner == X) {
			playersRankingFinishGame.add(players.get(X));
			playersRankingFinishGame.add(players.get(O));
		} else {
			playersRankingFinishGame.add(players.get(O));
			playersRankingFinishGame.add(players.get(X));
		}
	}

	@Override
	public void finishGame() {
		if (!this.isFinishGame()) {
			if ((map.get(0).get(0) != E) && 
				(map.get(0).get(0) == map.get(0).get(1) && map.get(0).get(1) == map.get(0).get(2)) ||
				(map.get(0).get(0) == map.get(1).get(0) && map.get(1).get(0) == map.get(2).get(0)) ||
				(map.get(0).get(0) == map.get(1).get(1) && map.get(1).get(1) == map.get(2).get(2)))
				initializeRanking(map.get(0).get(0));
			
			else if ((map.get(0).get(1) != E) && (map.get(0).get(1) == map.get(1).get(1) && map.get(1).get(1) == map.get(2).get(1)))
				initializeRanking(map.get(0).get(1));
			
			else if ((map.get(0).get(2) != E) &&
				(map.get(0).get(2) == map.get(1).get(1) && map.get(1).get(1) == map.get(2).get(0)) ||
				(map.get(0).get(2) == map.get(1).get(2) && map.get(1).get(2) == map.get(2).get(2)))
				initializeRanking(map.get(0).get(2));
			
			else if ((map.get(1).get(0) != E) && (map.get(1).get(0) == map.get(1).get(1) && map.get(1).get(1) == map.get(1).get(2)))
				initializeRanking(map.get(1).get(0));
			
			else if ((map.get(2).get(0) != E) && (map.get(2).get(0) == map.get(2).get(1) && map.get(2).get(1) == map.get(2).get(2)))
				initializeRanking(map.get(2).get(0));
		}
	}
}
