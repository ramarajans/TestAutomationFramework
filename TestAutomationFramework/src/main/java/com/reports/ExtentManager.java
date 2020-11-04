package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.main.DriverFactory;


public class ExtentManager {

	private static ExtentReports extent;
	ExtentHtmlReporter htmlReporter = null;

	public  ExtentReports getInstance() {	

		htmlReporter = new ExtentHtmlReporter(DriverFactory.getExtentConfigLocation());
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		return extent;
	}

}
