package com.youngsterblues.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.youngsterblues.user.User;
import com.youngsterblues.user.UserDAO;

public class UserDAOTest {

	UserDAO userdao;
	
	@Before
	public void setup() {
		userdao = new UserDAO();
	}

	@Test
	public void test() {
		User user = userdao.getUser("testuser");
		
		System.out.println(user.getName());
		assertNotNull(user);
	}
	
	@Test
	public void machPasswordTest(){
		User user = userdao.getUser("testuser");
		assertFalse(user.matchPassword("123"));
		assertTrue(user.matchPassword("1234"));
	}
	
	@Test
	public void addDBTest(){
		User user = new User("testuser4", "1234", "고랑이");
		assertFalse(userdao.addDB(user));
	}

}
