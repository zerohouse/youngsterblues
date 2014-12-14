package com.youngsterblues.reply;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReplyDAOTest {

	@Test
	public void test() {
		ReplyDAO dao = new ReplyDAO();
		System.out.println(dao.getReplies(53, 1, 10));
		
		assertTrue(true);
	}

}
