package com.flipkart.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'✕') or @class='_2KpZ6l _2doB4z']")
    private WebElement closeLoginPopup;

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(xpath = "//button[@type='submit' or contains(@class,'L0Z3Qu')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[contains(text(),'Login')]")
    private WebElement loginButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void closeLoginPopupIfDisplayed() {
        try {
            if (isElementDisplayed(closeLoginPopup)) {
                clickElement(closeLoginPopup);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Login popup not displayed or already closed");
        }
    }

    public void enterSearchKeyword(String keyword) {
        enterText(searchBox, keyword);
    }

    public SearchResultsPage clickSearchButton() {
        clickElement(searchButton);
        return new SearchResultsPage(driver);
    }

    public SearchResultsPage searchForProduct(String keyword) {
        closeLoginPopupIfDisplayed();
        enterSearchKeyword(keyword);
        searchBox.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public boolean isSearchBoxDisplayed() {
        return isElementDisplayed(searchBox);
    }
}
