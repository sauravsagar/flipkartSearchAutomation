package com.flipkart.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.flipkart.reports.ExtentManager;
import com.flipkart.utils.DriverFactory;
import com.flipkart.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class ExtentReportListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started: " + context.getName());
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite finished: " + context.getName());
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        String description = result.getMethod().getDescription();

        ExtentTest extentTest = extent.createTest(methodName, description != null ? description : qualifiedName);
        test.set(extentTest);

        test.get().info("Test started: " + methodName);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        test.get().pass(MarkupHelper.createLabel("Test PASSED: " + methodName, ExtentColor.GREEN));
        test.get().info("Test execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        test.get().fail(MarkupHelper.createLabel("Test FAILED: " + methodName, ExtentColor.RED));
        test.get().fail(result.getThrowable());
        test.get().info("Stack Trace: " + Arrays.toString(result.getThrowable().getStackTrace()));

        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(), methodName);
            test.get().addScreenCaptureFromPath(screenshotPath);
            test.get().info("Screenshot captured at: " + screenshotPath);
        } catch (Exception e) {
            test.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        test.get().skip(MarkupHelper.createLabel("Test SKIPPED: " + methodName, ExtentColor.YELLOW));
        test.get().skip(result.getThrowable());
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
