package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.config.ConfigReader;
import com.flipkart.listeners.ExtentReportListener;
import com.flipkart.pages.SearchResultsPage;
import com.flipkart.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class SearchTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return ExcelReader.getTestData(ConfigReader.getTestDataPath(), "SearchData");
    }

    @Test(priority = 1, description = "Verify Flipkart search box is displayed")
    public void testSearchBoxDisplayed() {
        ExtentReportListener.getTest().info("Verifying search box is displayed on home page");

        boolean isDisplayed = homePage.isSearchBoxDisplayed();

        ExtentReportListener.getTest().pass("Search box displayed: " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Search box should be displayed on home page");
    }

    @Test(priority = 2, dataProvider = "searchData", description = "Verify product search functionality with test data")
    public void testProductSearch(Map<String, String> testData) {
        String testCaseId = testData.get("TestCaseID");
        String searchKeyword = testData.get("SearchKeyword");
        String expectedResult = testData.get("ExpectedResult");

        ExtentReportListener.getTest().info("Test Case ID: " + testCaseId);
        ExtentReportListener.getTest().info("Searching for: " + searchKeyword);
        ExtentReportListener.getTest().info("Expected Result: " + expectedResult);

        SearchResultsPage resultsPage = homePage.searchForProduct(searchKeyword);

        ExtentReportListener.getTest().info("Waiting for search results to load");
        boolean resultsDisplayed = resultsPage.areSearchResultsDisplayed();

        ExtentReportListener.getTest().info("Search results displayed: " + resultsDisplayed);
        ExtentReportListener.getTest().info("Product count: " + resultsPage.getProductCount());

        Assert.assertTrue(resultsDisplayed, "Search results should be displayed for keyword: " + searchKeyword);
        ExtentReportListener.getTest().pass("Search completed successfully for: " + searchKeyword);
    }

    @Test(priority = 3, description = "Verify search results contain relevant products")
    public void testSearchResultsRelevance() {
        String searchKeyword = "iPhone";

        ExtentReportListener.getTest().info("Searching for: " + searchKeyword);
        SearchResultsPage resultsPage = homePage.searchForProduct(searchKeyword);

        ExtentReportListener.getTest().info("Verifying search results relevance");
        boolean isRelevant = resultsPage.doProductTitlesContainKeyword(searchKeyword);

        ExtentReportListener.getTest().info("Results contain keyword: " + isRelevant);
        Assert.assertTrue(isRelevant, "Search results should contain relevant products for: " + searchKeyword);
        ExtentReportListener.getTest().pass("Search results are relevant for: " + searchKeyword);
    }

    @Test(priority = 4, description = "Verify product count is greater than zero")
    public void testProductCountGreaterThanZero() {
        String searchKeyword = "Laptop";

        ExtentReportListener.getTest().info("Searching for: " + searchKeyword);
        SearchResultsPage resultsPage = homePage.searchForProduct(searchKeyword);

        int productCount = resultsPage.getProductCount();
        ExtentReportListener.getTest().info("Product count: " + productCount);

        Assert.assertTrue(productCount > 0, "Product count should be greater than zero");
        ExtentReportListener.getTest().pass("Product count validation passed: " + productCount);
    }

    @Test(priority = 5, description = "Verify first product title is not empty")
    public void testFirstProductTitleNotEmpty() {
        String searchKeyword = "Headphones";

        ExtentReportListener.getTest().info("Searching for: " + searchKeyword);
        SearchResultsPage resultsPage = homePage.searchForProduct(searchKeyword);

        String firstProductTitle = resultsPage.getFirstProductTitle();
        ExtentReportListener.getTest().info("First product title: " + firstProductTitle);

        Assert.assertFalse(firstProductTitle.isEmpty(), "First product title should not be empty");
        ExtentReportListener.getTest().pass("First product title validation passed");
    }
}
