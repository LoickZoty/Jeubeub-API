package com.example.demo.synchronize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SynchronizePlayers {

	public Map<String, Boolean> playersSync;
	
	public SynchronizePlayers() {
		this.playersSync = new HashMap<String, Boolean>();
	}
	
	public SynchronizePlayers(ArrayList<String> players) {
		this.initializePlayersSync(players);
	}
	
	public void setPlayersSync(String playerId, boolean bool) {
		if (playersSync.containsKey(playerId))
			playersSync.replace(playerId, bool);
	}
	
	public boolean isNeedSynchronize(String playerId) {
		if (playersSync.containsKey(playerId))
			return !playersSync.get(playerId);
		return false;
	}
	
	public boolean isAllSynchronize() {
		for (Entry<String, Boolean> entry : playersSync.entrySet()) {
		    if (!entry.getValue()) return false;
		}
		return true;
	}
	
	public void put(String player, boolean bool) {
		playersSync.put(player, bool);
	}
	
	public void waitSynchronize() {
		while (!isAllSynchronize()) {}
	}
	
	public void initializePlayersSync(ArrayList<String> players) {
		playersSync = new HashMap<String, Boolean>();
		for (String player : players) {
			playersSync.put(player, false);
		}
	}
}
