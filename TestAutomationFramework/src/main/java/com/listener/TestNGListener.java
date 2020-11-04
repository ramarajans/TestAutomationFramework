package com.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.reports.LogStatus;

public class TestNGListener implements ITestListener{

	public static String testCaseName;
	
	@Override
	public void onTestStart(ITestResult result) {
		
		System.out.println("********Test Started : "+result.getName()+"********");
		LogStatus.info("Test Started : "+result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		System.out.println("********Test Successful : "+result.getName()+"********");
		LogStatus.pass("Test Successful : "+result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("********Test Failed : "+result.getName()+"********");
		LogStatus.fail("Test Failed : "+result.getName());
		ITestListener.super.onTestFailure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("********Test Skipped : "+result.getName()+"********");
		LogStatus.skip("Test Skipped : "+result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("********Test Completed : "+context.getName()+"********");
		ITestListener.super.onFinish(context);
	}
	
	
	
}
