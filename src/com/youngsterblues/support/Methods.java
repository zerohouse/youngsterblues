package com.youngsterblues.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {

	private Methods() {
	}

	public static Date parseDate(Object object) {
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = datetime.parse(object.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
