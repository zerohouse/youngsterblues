package com.youngsterblues.support;

import org.junit.Test;

import com.youngsterblues.support.Setting;

public class SettingTest {

	@Test
	public void test() {
		System.out.println(Setting.get("db"));
		System.out.println(Setting.get("url"));
	}

}
