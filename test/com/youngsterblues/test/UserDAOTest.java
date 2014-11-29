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
	public void machPasswordTest(){
		User user = userdao.getUser("zerohouse");
		System.out.println(user);
		assertFalse(user.matchPassword("123"));
		assertTrue(user.matchPassword("1234"));
	}
	
	@Test
	public void addDBTest(){
		User user = new User("zerohouse", "1234", "고랑이");
		assertFalse(userdao.addDB(user));
	}

	
}
