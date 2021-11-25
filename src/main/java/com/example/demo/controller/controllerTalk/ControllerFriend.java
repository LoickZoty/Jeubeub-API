package com.example.demo.controller.controllerTalk;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.talk.Talk;
import com.example.demo.talk.TalkCallGame;

@RestController
@RequestMapping(value="/Jeubeub/api/v1/friend")
public class ControllerFriend extends ControllerAbstractTalk {
	
	@GetMapping("/sendCallGame")
	public ResponseEntity<Object> sendCallGame(@RequestParam("playerId") String playerId, @RequestParam("playerIdRequest") String playerIdRequest, @RequestParam("queueId") int queueId, @RequestParam("gameName") String gameName) throws InterruptedException {
		Talk talk = super.getPlayerTalk(playerIdRequest);
		TalkCallGame talkCallGame = ((TalkCallGame)talk.getTalk(TalkCallGame.class));
		talkCallGame.send(playerId, queueId, gameName);
		return new ResponseEntity<>("{\"message\" = \"success\"}", HttpStatus.OK);
	}
	
	@GetMapping("/waitCallGame")
	public ResponseEntity<Object> waitCallGame(@RequestParam("playerId") String playerId) throws InterruptedException {
		Talk talk = super.getPlayerTalk(playerId);
		TalkCallGame talkCallGame = ((TalkCallGame)talk.getTalk(TalkCallGame.class));
		talkCallGame.initializeTalk();
		
		while(!talkCallGame.isReceiv()) {
			Thread.sleep(1000);
		}
		return new ResponseEntity<>(talkCallGame.getData(), HttpStatus.OK);
	}
}
