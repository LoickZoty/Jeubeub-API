package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.GameData;
import com.example.demo.game.Game;
import com.example.demo.game.Morpion;
import com.example.demo.queue.Queue;

public abstract class ControllerAbstractGame {
	protected GameData gameData;
	protected Map<Class<?>, ArrayList<Queue>> gameQueue;
    
	public ControllerAbstractGame(GameData gameData) {
		this.gameData = gameData;
		this.initializeGameQueue();
	}
		
	public abstract @ResponseBody Game waitQueue(@RequestParam("playerId") int playerId) throws InterruptedException;
	
    public Game getNewGame(ArrayList<Queue> queues, int playerId, int minQueue, int maxQueue) throws InterruptedException {
		if (queues.size() == 0) queues.add(new Queue(minQueue,maxQueue));
    	Queue queue = queues.get(0);
		queue.add(playerId);
		queue.waitQueue();
		if (queue.game == null) {
			Morpion morpion = new Morpion(queue.players);
			queue.game = morpion;
			gameData.push(morpion);
		}
		
		Morpion morpion = (Morpion)queue.game;
		queue.playersSync.setPlayersSync(playerId, true);
		if (queue.playersSync.isAllSynchronize()) removeQueue(queues, queue);
		return morpion;
    } 
    
    public void removeQueue(ArrayList<Queue> queues, Queue queue) {
    	for (int i = 0; i < queue.players.size(); i++) {
    		int indexOf = Queue.allPlayersWaiting.indexOf(queue.players.get(i));
    		if (indexOf != -1) Queue.allPlayersWaiting.remove(indexOf);		
    	}
    	queues.remove(queue);
    }
	
	private void initializeGameQueue() {
		gameQueue = new HashMap<Class<?>, ArrayList<Queue>>();
		gameQueue.put(Morpion.class, new ArrayList<Queue>());
	}
}
