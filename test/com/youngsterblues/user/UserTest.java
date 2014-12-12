package com.youngsterblues.user;

import static org.junit.Assert.*;
import org.junit.Test;

import com.youngsterblues.support.State;

public class UserTest {

	@Test
	public void loginTestCaseNull() {
		User user = new User("zerohuse", "123", null);
		State state = user.login();
		System.out.println(state);
		assertFalse(state.isSuccess());
	}
	
	@Test
	public void loginTestCaseWrongPassword() {
		User user = new User("zerohouse", "123", null);
		State state = user.login();
		System.out.println(state);
		assertFalse(state.isSuccess());
	}
	
	@Test
	public void loginTestCaseSuccess() {
		User user = new User("zerohouse", "1234", null);
		State state = user.login();
		System.out.println(state);
		assertTrue(state.isSuccess());
	}

}
