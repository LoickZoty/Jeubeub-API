package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value="/Jeubeub/api/v1/game/morpion")
public class ControllerMorpion {
	public ArrayList<Queue> morpionQueue = new ArrayList<Queue>();
	
    @Autowired
    private GameData gameData;
    
	@GetMapping("/waitQueue") //Chercher une solution pour ajouter n'importe quel jeu
    public @ResponseBody Morpion waitQueue(@RequestParam("playerId") int playerId) throws InterruptedException {
		if (morpionQueue.size() == 0) {
			morpionQueue.add(new Queue(2,2));
		}
		Queue queue = morpionQueue.get(0);
		queue.add(playerId);
		queue.waitQueue();
		
		if (queue.game == null) {
			Morpion morpion = new Morpion(queue.players);
			queue.game = morpion;
			gameData.push(morpion);
		}
		Morpion morpion = (Morpion)queue.game;
		queue.playersSync.setPlayersSync(playerId, true);
		if (queue.playersSync.isAllSynchronize()) morpionQueue.remove(queue);
		return morpion;
    }   
	
	@GetMapping("{id}/dropMarker")
    public @ResponseBody ResponseEntity<Object> dropMarker(@PathVariable int id, @RequestParam("playerId") int playerId, @RequestParam("x") int x, @RequestParam("y") int y) {
		Game game = gameData.games.get(id);
		if (game instanceof Morpion) {
			Morpion morpion = (Morpion)game;
			return morpion.dropMarker(playerId, x, y);
		}
		return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
    }
}
