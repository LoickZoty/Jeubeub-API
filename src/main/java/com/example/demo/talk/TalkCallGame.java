package com.example.demo.talk;

import java.util.Map;

public class TalkCallGame extends Talk {
	public int queueId;
	public String gameName;
	
	public void send(String playerIdRequest, int queueId, String gameName) {
		super.send(playerIdRequest);
		this.queueId = queueId;
		this.gameName = gameName;
	}
	
	@Override
	public Map<String, Object> getData() {
		Map<String, Object> data = super.getData();
		data.put("queueId", queueId);
		data.put("gameName", gameName);
		return data;
	}
	
	@Override
	public void initializeTalk() {
		super.initializeTalk();
		gameName = null;
	}
}
