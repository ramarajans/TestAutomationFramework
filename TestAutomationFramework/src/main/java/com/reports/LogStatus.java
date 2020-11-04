package com.reports;

import com.aventstack.extentreports.MediaEntityBuilder;

import com.main.DriverFactory;

public class LogStatus {

	private LogStatus() {

	}
	public static void pass(String message)
	{
		ExtentFactory.getExtTest().pass(message);

	}

	public static void fail(String message)
	{

		ExtentFactory.getExtTest().fail(message);
	}

	public static void fail(Exception message)
	{

		ExtentFactory.getExtTest().fail(message);
	}

	public static void info(String message)
	{

		ExtentFactory.getExtTest().info(message);
	}

	public static void error(String message)
	{

		ExtentFactory.getExtTest().error(message);
	}

	public static void fatal(String message)
	{

		ExtentFactory.getExtTest().fatal(message);
	}

	public static void skip(String message)
	{

		ExtentFactory.getExtTest().skip(message);
	}


	public static void warning(String message)
	{

		ExtentFactory.getExtTest().warning(message);
	}

	public static void pass(String string, String addScreenCapture) throws Exception {

		if(DriverFactory.getPassedStepsScreenshots().equalsIgnoreCase("yes")) {

			ExtentFactory.getExtTest().pass(string,  MediaEntityBuilder.createScreenCaptureFromPath("/Desktop/logo.jpg").build());
		}
	}

	public static void fail(String string, String addScreenCapture) throws Exception
	{

		if(DriverFactory.getFailedStepsScreenshots().equalsIgnoreCase("yes")) {
			ExtentFactory.getExtTest().fail(string,  MediaEntityBuilder.createScreenCaptureFromPath(addScreenCapture).build());

		}

	}

	public static void skip(String string, String addScreenCapture) throws Exception
	{
		if(DriverFactory.getSkippedStepScreenshots().equalsIgnoreCase("yes")) {

			ExtentFactory.getExtTest().skip(string,  MediaEntityBuilder.createScreenCaptureFromPath("/Desktop/logo.jpg").build());

		}

	}
}
