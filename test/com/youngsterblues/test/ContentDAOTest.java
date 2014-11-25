package com.youngsterblues.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.youngsterblues.contents.Content;
import com.youngsterblues.contents.ContentDAO;

public class ContentDAOTest {

	ContentDAO dao;

	@Before
	public void setup() {
		dao = new ContentDAO();
	}

	@Test
	public void addDBTest() {
		Content content = new Content("free", "zerohouse", "head",
				"contents", new Date());
		dao.addDB(content);
	}

	@Test
	public void getContentsTest() {
		ArrayList<Content> contents = dao.getContentsList("free", 10);
		System.out.println(contents.size());
		for (int i = 0; i < contents.size(); i++) {
			System.out.println(contents.get(i));
		}

		assertNotNull(contents);
	}
	
	@Test
	public void getContentHeadTest() {
		ArrayList<Content> contents = dao.getContentsHeadList("free", 10);
		System.out.println(contents.size());
		for (int i = 0; i < contents.size(); i++) {
			System.out.println(contents.get(i));
		}
		assertNotNull(contents);
	}
	
	@Test
	public void getContentTest() {
		Content content = dao.getContent(10);

		System.out.println("content " + content);
		
		assertNotNull(content);
		
	}
	
	@Test
	public void deleteContentTest() {
		assertTrue(dao.deleteDB(2));

	}
	
	@Test
	public void modContentTest() {
		assertTrue(dao.modDB("zerohouse", 46, "sssdfgasdfadsfss", "ssdfgsdfasdfdsfgs"));

	}
	

}
