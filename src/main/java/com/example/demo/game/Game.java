package com.example.demo.game;

import java.util.ArrayList;
import java.util.Map;

import com.example.demo.synchronize.SynchronizePlayers;

public abstract class Game {
	public static int nextId = 0;
	
	protected SynchronizePlayers synchronizePlayers;
	
	protected String name = this.getClass().getSimpleName();
	protected int id;
	protected ArrayList<Integer> players;
	protected int actualPlayer;
	protected ArrayList<Integer> playersRankingFinishGame = new ArrayList<Integer>();
	
	public Game(ArrayList<Integer> players) {
		this.id = nextId++;
		this.players = players;
		this.actualPlayer = players.get(0);
		this.synchronizePlayers = new SynchronizePlayers(players);
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

	public ArrayList<Integer> getPlayers() {
		return players;
	}
	
	public int getActualPlayer() {
		return actualPlayer;
	}
	
	public ArrayList<Integer> getPlayersRankingFinishGame() {
		return playersRankingFinishGame;
	}
		
	public boolean isFinishGame() {
		return this.playersRankingFinishGame.size() > 0;
	}
		
	public boolean isMyTurn(int playerId) {
		return this.getActualPlayer() == playerId;
	}
	
	public SynchronizePlayers sync() {
		return synchronizePlayers;
	}
}
