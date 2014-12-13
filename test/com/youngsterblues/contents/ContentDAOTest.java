package com.youngsterblues.contents;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContentDAOTest {

	@Test
	public void deleteTest() {
		Content content = new Content(57, null, "zerohouse", null, null, null);
		content.delete();
		assertTrue(true);
	}

	@Test
	public void modTest() {
		Content content = new Content(58, null, "zerohouse", "그림", "그림", null);
		content.mod();
	}

}
