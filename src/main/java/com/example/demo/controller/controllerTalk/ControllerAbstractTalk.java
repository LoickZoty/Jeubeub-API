package com.example.demo.controller.controllerTalk;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.talk.Talk;

public abstract class ControllerAbstractTalk {
	public Map<String, Talk> waitingRequest = new HashMap<String, Talk>(); //Remplacer Boolean par un object comprenant tous les boolean d'attente
	
	protected Talk getPlayerTalk(String playerId) {
		if (!waitingRequest.containsKey(playerId)) waitingRequest.put(playerId, Talk.newInstance());
		return waitingRequest.get(playerId);
	}
}
