package com.youngsterblues.support;

import com.google.gson.Gson;

public class State {
	private boolean success;
	private String errorMessage;

	public void setState(boolean state, String errorMessage) {
		this.success = state;
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	@Override
	public String toString() {
		return "State [success=" + success + ", errorMessage=" + errorMessage
				+ "]";
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
