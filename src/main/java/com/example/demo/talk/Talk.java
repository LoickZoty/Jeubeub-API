package com.example.demo.talk;

import java.util.HashMap;
import java.util.Map;

public class Talk {
	private Map<Class<?>, Talk> talks = new HashMap<Class<?>, Talk>();
	
	protected boolean receiv = false;
	protected String playerIdRequest;
	
	protected Talk() {}
	
	protected void send(String playerIdRequest) {
		this.receiv = true;
		this.playerIdRequest = playerIdRequest;
	}
	
	public Talk getTalk(Class<?> cls) {
		return talks.get(cls);
	}
	
	public Map<String, Object> getData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("playerIdRequest", playerIdRequest);
		return data;
	}
	
	public boolean isReceiv() {
		return receiv;
	}
	
	public void initializeTalk() {
		receiv = false;
		playerIdRequest = null;
	}
	
	public static Talk newInstance() {
		Talk talk = new Talk();
		talk.talks.put(TalkCallGame.class, new TalkCallGame());
		
		return talk;
	}
}
