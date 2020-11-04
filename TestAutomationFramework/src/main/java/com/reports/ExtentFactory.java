package com.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {
	
	public static ThreadLocal<ExtentTest> exTest= new ThreadLocal<ExtentTest>();
	
	public static ExtentTest getExtTest() {
		return exTest.get();
	}

	public static void setExtentTest(ExtentTest test) {
		exTest.set(test);
	}


	
}
