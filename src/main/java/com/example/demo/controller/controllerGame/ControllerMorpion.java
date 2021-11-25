package com.example.demo.controller.controllerGame;

import java.util.ArrayList;

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

@RestController
@RequestMapping(value="/Jeubeub/api/v1/game/morpion")
public class ControllerMorpion extends ControllerAbstractGame {
	public ControllerMorpion(GameData gameData) {
		super(gameData);
	}
	
	@Override
	public Game getGame(ArrayList<String> players) {
		return new Morpion(players);
	}
	
	@Override
	public Queue getQueue(boolean publicQueue) {
		return new Queue(2, 2, publicQueue);
	}
	  		
	@GetMapping("{id}/dropMarker")
    public @ResponseBody ResponseEntity<Object> dropMarker(@PathVariable int id, @RequestParam("playerId") String playerId, @RequestParam("x") int x, @RequestParam("y") int y) {
		Game game = gameData.games.get(id);
		if (game instanceof Morpion) {
			Morpion morpion = (Morpion)game;
			return morpion.dropMarker(playerId, x, y);
		}
		return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
    }
}
