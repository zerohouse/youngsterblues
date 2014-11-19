package com.youngsterblues.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.youngsterblues.support.DAO;

public class DAOtest {
	
	DAO dao;
	
	@Before
	public void setup(){
		dao = new DAO();
	}

	@Test
	public void test() {
		assertNotNull(dao.getConnection());
	}

}
