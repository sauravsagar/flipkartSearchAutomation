package com.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//div[@class='_1YokD2 _3Mn1Gg' or contains(@class,'_4rR01T')]")
    private List<WebElement> productTitles;

    @FindBy(xpath = "//div[contains(@class,'_30jeq3 _1_WHN1')]")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//div[@class='_2kHMtA']//img")
    private List<WebElement> productImages;

    @FindBy(xpath = "//span[contains(text(),'results for') or contains(text(),'results')]")
    private WebElement searchResultsText;

    @FindBy(xpath = "//div[@class='_1YokD2 _3Mn1Gg']")
    private WebElement firstProductTitle;

    @FindBy(xpath = "//div[contains(text(),'Sorry, no results found!') or contains(text(),'did not match any products')]")
    private WebElement noResultsMessage;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean areSearchResultsDisplayed() {
        try {
            Thread.sleep(2000);
            return productTitles.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        try {
            Thread.sleep(1500);
            return productTitles.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getFirstProductTitle() {
        waitForElementToBeVisible(firstProductTitle);
        return getElementText(firstProductTitle);
    }

    public boolean isSearchResultsTextDisplayed() {
        try {
            return isElementDisplayed(searchResultsText);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return isElementDisplayed(noResultsMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean doProductTitlesContainKeyword(String keyword) {
        try {
            Thread.sleep(2000);
            for (WebElement title : productTitles) {
                String titleText = title.getText().toLowerCase();
                if (titleText.contains(keyword.toLowerCase())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
