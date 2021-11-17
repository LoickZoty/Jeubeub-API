package com.example.demo.synchronize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SynchronizePlayers {

	public Map<Integer, Boolean> playersSync;
	
	public SynchronizePlayers() {
		this.playersSync = new HashMap<Integer, Boolean>();
	}
	
	public SynchronizePlayers(ArrayList<Integer> players) {
		this.initializePlayersSync(players);
	}
	
	public void setPlayersSync(int playerId, boolean bool) {
		if (playersSync.containsKey(playerId))
			playersSync.replace(playerId, bool);
	}
	
	public boolean isNeedSynchronize(int playerId) {
		if (playersSync.containsKey(playerId))
			return !playersSync.get(playerId);
		return false;
	}
	
	public boolean isAllSynchronize() {
		for (Entry<Integer, Boolean> entry : playersSync.entrySet()) {
		    if (!entry.getValue()) return false;
		}
		return true;
	}
	
	public void put(int player, boolean bool) {
		playersSync.put(player, bool);
	}
	
	public void waitSynchronize() {
		while (!isAllSynchronize()) {}
	}
	
	public void initializePlayersSync(ArrayList<Integer> players) {
		playersSync = new HashMap<Integer, Boolean>();
		for (int player : players) {
			playersSync.put(player, false);
		}
	}
}
