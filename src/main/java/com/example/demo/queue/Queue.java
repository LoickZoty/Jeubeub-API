package com.example.demo.queue;

import java.util.ArrayList;

import com.example.demo.data.GameData;
import com.example.demo.game.Game;
import com.example.demo.synchronize.SynchronizePlayers;

public class Queue {
	public static ArrayList<String> allPlayersWaiting = new ArrayList<String>();
	
	public ArrayList<String> players = new ArrayList<String>();
	public SynchronizePlayers playersSync = new SynchronizePlayers();
	public int minPlayers;
	public int maxPlayers;
	
	public Game game;
		
	public Queue(int minPlayers, int maxPlayers) {
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
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
	
	public boolean isStartable() {
		return players.size() >= minPlayers && players.size() <= maxPlayers;
	}
	
	public void add(String playerId) {
		if (!allPlayersWaiting.contains(playerId)) {
			allPlayersWaiting.add(playerId);
	 		players.add(playerId);
			playersSync.put(playerId, false);
		}
	}
}
