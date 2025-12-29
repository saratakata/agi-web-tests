package com.blogagi.pages;

import com.blogagi.config.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.JavascriptExecutor;


import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Configuration.DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementVisibleAndReturn(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected WebElement findElement(By locator) {
        waitForElementVisible(locator);
        return driver.findElement(locator);
    }


    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

//
//    protected void click(By locator) {
//        logger.debug("Clicking on element: {}", locator);
//        waitForElementClickable(locator);
//        findElement(locator).click();
//    }


    protected void click(By locator) {
        logger.info(" click KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        driver.findElement(By.cssSelector(".ast-icon > .ahfb-svg-iconset")).click();
    }

    protected void type(By locator, String text) {
        logger.debug("Typing '{}' into element: {}", text, locator);
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }


    protected void jsClick(By locator) {
        WebElement element = waitForElementVisibleAndReturn(locator);
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", element);
    }

}
