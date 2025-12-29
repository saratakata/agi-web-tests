package com.blogagi.utils;

import com.blogagi.config.Configuration;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);

    public static void takeScreenshot(WebDriver driver, String testName) {
        if (!Configuration.SCREENSHOT_ON_FAILURE) {
            return;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Save to file
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = String.format("%s_%s.png", testName.replaceAll("\\s+", "_"), timestamp);
            File destFile = new File(Configuration.SCREENSHOT_PATH + fileName);

            destFile.getParentFile().mkdirs();
            FileUtils.writeByteArrayToFile(destFile, screenshotBytes);

            logger.info("Screenshot saved: {}", destFile.getAbsolutePath());

            // Attach to Allure
            Allure.addAttachment(testName, new ByteArrayInputStream(screenshotBytes));

        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}