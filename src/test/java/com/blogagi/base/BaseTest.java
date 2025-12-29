package com.blogagi.base;

import com.blogagi.config.Configuration;
import com.blogagi.driver.DriverFactory;
//import com.blogagi.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@ExtendWith(TestResultWatcher.class)
public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        printTestConfiguration();
    }

//    @BeforeEach
//    public void setup(TestInfo testInfo) {
//        logger.info("Starting test: {}", testInfo.getDisplayName());
//        driver = DriverFactory.getDriver();
//
//        Allure.parameter("Browser", Configuration.BROWSER);
//        Allure.parameter("Headless", Configuration.HEADLESS);
//        Allure.parameter("Timeout", Configuration.DEFAULT_TIMEOUT);
//    }

    @BeforeEach
    void setup() {
        driver = DriverFactory.getDriver();

        Allure.parameter("Browser", Configuration.BROWSER);
        Allure.parameter("Headless", Configuration.HEADLESS);
        Allure.parameter("Timeout", Configuration.DEFAULT_TIMEOUT);

        logger.info("Starting test execution");
    }

//    @AfterEach
//    public void tearDown(TestInfo testInfo) {
//        if (testInfo.getExecutionException().isPresent()) {
//            logger.error("Test failed: {}", testInfo.getDisplayName());
//            ScreenshotUtils.takeScreenshot(driver, testInfo.getDisplayName());
//        } else {
//            logger.info("Test passed: {}", testInfo.getDisplayName());
//        }
//
//        DriverFactory.quitDriver();
//    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
        logger.info("Test execution finished");
    }

    private static void printTestConfiguration() {
        System.out.println("\n========================================");
        System.out.println("Test Configuration");
        System.out.println("========================================");
        System.out.println("Base URL: " + Configuration.BASE_URL);
        System.out.println("Browser: " + Configuration.BROWSER);
        System.out.println("Headless: " + Configuration.HEADLESS);
        System.out.println("Timeout: " + Configuration.DEFAULT_TIMEOUT + "s");
        System.out.println("Screenshot on Failure: " + Configuration.SCREENSHOT_ON_FAILURE);
        System.out.println("========================================\n");
    }
}
