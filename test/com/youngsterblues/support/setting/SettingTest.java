package com.youngsterblues.support.setting;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		System.out.println(Setting.get("db"));
		System.out.println(Setting.get("url"));
	}

}
