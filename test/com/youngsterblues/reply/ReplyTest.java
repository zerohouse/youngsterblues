package com.youngsterblues.reply;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReplyTest {

	@Test
	public void test() {
		System.out.println(Reply.getReplyListJson("53", null, null));
		assertTrue(true);
	}

}
