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

@RestController
@RequestMapping(value="/Jeubeub/api/v1/game/morpion")
public class ControllerMorpion extends ControllerAbstractGame {
	public ControllerMorpion(GameData gameData) {
		super(gameData);
	}
	    
	@GetMapping("/waitQueue") //Chercher une solution pour ajouter n'importe quel jeu
    public @ResponseBody Game waitQueue(@RequestParam("playerId") String playerId) throws InterruptedException {
		return super.getNewGame(gameQueue.get(Morpion.class), playerId, 2, 2);
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
