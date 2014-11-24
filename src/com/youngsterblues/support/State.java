package com.youngsterblues.support;

import com.google.gson.Gson;

public class State {
	@SuppressWarnings("unused")
	private boolean state;
	@SuppressWarnings("unused")
	private String errorMessage;

	public void setState(boolean state, String errorMessage) {
		this.state = state;
		this.errorMessage = errorMessage;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
