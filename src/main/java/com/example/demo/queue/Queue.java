package com.example.demo.queue;

import java.util.ArrayList;

import com.example.demo.data.gameData.GameData;
import com.example.demo.game.Game;
import com.example.demo.synchronize.SynchronizePlayers;

public class Queue {
	public static int publicNextId = 0;
	public static int privateNextId = 0;
	public static ArrayList<String> allPlayersWaiting = new ArrayList<String>();
	
	public int id;
	public ArrayList<String> players = new ArrayList<String>();
	private SynchronizePlayers playersSync = new SynchronizePlayers();
	public int minPlayers;
	public int maxPlayers;
	
	public Game game;
		
	public Queue(int minPlayers, int maxPlayers, boolean publicQueue) {
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		
		if (publicQueue) this.id = publicNextId++;
		else this.id = privateNextId++;
	}
	
	public void waitQueue() throws InterruptedException {
		while(!isStartable()) {
			Thread.sleep(1000);
		}
	}
	
	public void waitInstanceGame() throws InterruptedException {
		while(game == null) {
			Thread.sleep(1000);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isStartable() {
		return players.size() >= minPlayers && players.size() <= maxPlayers;
	}
	
	public SynchronizePlayers sync() {
		return playersSync;
	}
		
	public void add(String playerId) {
		if (!allPlayersWaiting.contains(playerId)) {
			allPlayersWaiting.add(playerId);
	 		players.add(playerId);
			playersSync.put(playerId, false);
		}
	}
}
