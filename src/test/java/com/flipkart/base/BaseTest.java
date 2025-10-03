package com.flipkart.base;

import com.flipkart.config.ConfigReader;
import com.flipkart.listeners.ExtentReportListener;
import com.flipkart.pages.HomePage;
import com.flipkart.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        DriverFactory.setDriver(browser);
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getBaseUrl());
        homePage = new HomePage(driver);

        ExtentReportListener.getTest().info("Browser launched: " + browser);
        ExtentReportListener.getTest().info("Navigated to: " + ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            ExtentReportListener.getTest().info("Closing browser");
            DriverFactory.quitDriver();
        }
    }
}
