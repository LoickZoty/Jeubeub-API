package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;

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

@RestController
@RequestMapping(value="/Jeubeub/api/v1/game")
public class ControllerGame {
	private GameData gameData;
    
	public ControllerGame(GameData gameData) {
		this.gameData = gameData;
	}
		
	@GetMapping("/remove/{id}")
    public String remove(@PathVariable int id) {
		gameData.remove(id);
		return "Je supprime le morpion "+id;
    }
	
	@GetMapping("/games/{id}")
	public ResponseEntity<Object> display(@PathVariable int id) {
		Game game = gameData.getGame(id);
		if (game != null) return new ResponseEntity<>(game, HttpStatus.OK);	
		return new ResponseEntity<>("Game not exist", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/waitTurn")
	public ResponseEntity<Object> waitTurn(@PathVariable int id, @RequestParam("playerId") int playerId) throws InterruptedException {
		Game game = gameData.getGame(id);
		if (game != null) {
			while(!game.isMyTurn(playerId)) {
				Thread.sleep(1000);
			}
			return new ResponseEntity<>("Your turn", HttpStatus.OK);	
		}
		return new ResponseEntity<>("Timeout error", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/waitRefreshment")
	public ResponseEntity<Object> waitRefreshment(@PathVariable int id, @RequestParam("playerId") int playerId) throws InterruptedException {
		Game game = gameData.getGame(id);
		if (game != null) {
			while(!game.sync().isNeedSynchronize(playerId) && !game.isFinishGame()) {
				Thread.sleep(1000);
			}
			game.sync().setPlayersSync(playerId, true);
			return new ResponseEntity<>(game, HttpStatus.OK);	
		}
		return new ResponseEntity<>("Timeout error", HttpStatus.NOT_FOUND);
	}
}
