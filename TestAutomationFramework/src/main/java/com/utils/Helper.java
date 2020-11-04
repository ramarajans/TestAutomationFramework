package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

	
	public static String currentTimeStamp()throws Exception{

		DateFormat df = new SimpleDateFormat("ddMMyyHHMMss");
		Date currentDate = new Date();

		return df.format(currentDate);
	}
}
