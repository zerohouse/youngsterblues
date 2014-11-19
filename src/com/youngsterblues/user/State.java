package com.youngsterblues.user;

public class State {
	@SuppressWarnings("unused")
	private boolean state;
	@SuppressWarnings("unused")
	private String errorMessage;

	public void setState(boolean state, String errorMessage) {
		this.state = state;
		this.errorMessage = errorMessage;
	}
}
