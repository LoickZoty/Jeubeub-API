package com.example.demo.controller.controllerGame;

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

import com.example.demo.data.gameData.GameData;
import com.example.demo.game.Game;
import com.example.demo.game.Morpion;
import com.example.demo.queue.Queue;

public abstract class ControllerAbstractGame {
	protected GameData gameData;
	protected Map<Integer, Queue> publicQueueGame = new HashMap<Integer, Queue>();
	protected Map<Integer, Queue> privateQueueGame = new HashMap<Integer, Queue>();
    
	public ControllerAbstractGame(GameData gameData) {
		this.gameData = gameData;
	}
	
	public abstract Game getGame(ArrayList<String> players);
	
	public abstract Queue getQueue(boolean publicQueue);
		
	//public abstract @ResponseBody ResponseEntity<Object> waitPublicQueue(@RequestParam("playerId") String playerId) throws InterruptedException;
	
	@GetMapping("/waitPublicQueue")
    public @ResponseBody ResponseEntity<Object> waitPublicQueue(@RequestParam("playerId") String playerId) throws InterruptedException {
		if (publicQueueGame.size() == 0) publicQueueGame.put(Queue.publicNextId, getQueue(true));
		return responseGame(getNewGame(publicQueueGame, publicQueueGame.entrySet().iterator().next().getKey(), playerId));
    }
	
	@GetMapping("/waitPrivateQueue")
	public @ResponseBody ResponseEntity<Object> waitPrivateQueue(@RequestParam String playerId, @RequestParam int queueId) throws InterruptedException {
		return responseGame(getNewGame(privateQueueGame, queueId, playerId));	
	}
	
	@GetMapping("/createPrivateQueue")
	public @ResponseBody Queue createPrivateQueue(@RequestParam("playerId") String playerId) throws InterruptedException {
		Queue queue = getQueue(false);
		privateQueueGame.put(queue.id, queue);
		return queue;
	}
	
    public Game getNewGame(Map<Integer, Queue> queueGame, int queueId, String playerId) throws InterruptedException {
    	if (queueGame.containsKey(queueId)) {
	    	Queue queue = queueGame.get(queueId);
			queue.add(playerId);
			queue.waitQueue();
			if (queue.players.get(0) == playerId) {
				queue.game = getGame(queue.players);
				gameData.push(queue.game);
			}
			queue.waitInstanceGame();
			queue.sync().setPlayersSync(playerId, true);
			if (queue.sync().isAllSynchronize()) removeQueue(queueGame, queueId);
			return queue.game;
    	}
    	return null;
    } 
    
    public void removeQueue(Map<Integer, Queue> queueGame, int queueId) {
    	Queue queue = queueGame.get(queueId);
    	for (int i = 0; i < queue.players.size(); i++) {
    		int indexOf = Queue.allPlayersWaiting.indexOf(queue.players.get(i));
    		if (indexOf != -1) Queue.allPlayersWaiting.remove(indexOf);		
    	}
    	queueGame.remove(queueId);
    }
    
    public ResponseEntity<Object> responseGame(Game game) {
		if (game == null) return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
		else return new ResponseEntity<>(game, HttpStatus.OK);
    }
}
