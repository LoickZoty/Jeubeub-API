package com.example.demo.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.synchronize.SynchronizePlayers;

public abstract class Game {
	public static int nextId = 0;
	
	protected SynchronizePlayers synchronizePlayers;
	
	protected String name = this.getClass().getSimpleName();
	protected int id;
	protected ArrayList<String> players;
	protected String actualPlayer;
	protected boolean finishGame;
	protected Map<String, Integer> playersRankingFinishGame;
	
	public Game(ArrayList<String> players) {
		this.id = nextId++;
		this.players = players;
		this.actualPlayer = players.get(0);
		this.synchronizePlayers = new SynchronizePlayers(players);
		this.initializePlayersRankingFinishGame();
	}
		
	public abstract Map<String,Object> getActualGameData();
	
	public abstract void finishGame();
	
	public void nextPlayer() {
		int index = players.indexOf(actualPlayer);
		if (index == players.size()-1) actualPlayer = players.get(0);
		else actualPlayer = players.get(index+1);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}
	
	public String getActualPlayer() {
		return actualPlayer;
	}
	
	public Map<String, Integer> getPlayersRankingFinishGame() {
		return playersRankingFinishGame;
	}
		
	public boolean isFinishGame() {
		return finishGame;
	}
		
	public boolean isMyTurn(String playerId) {
		return this.getActualPlayer().equals(playerId);
	}
	
	public SynchronizePlayers sync() {
		return synchronizePlayers;
	}
	
	protected void initializePlayersRankingFinishGame() {
		playersRankingFinishGame = new HashMap<String, Integer>();
		for (String player : players) {
			playersRankingFinishGame.put(player, 0);
		}
	}
}
