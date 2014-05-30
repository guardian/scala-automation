package com.gu.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * Created by jao on 23/05/2014.
 */
public class DriverEventListener implements WebDriverEventListener {

    private TestLogger logger;
    private long commandStart = 0L;
    private String originalValue;
    private By previousBy;

    public DriverEventListener(TestLogger logger) {
        this.logger = logger;
    }

    private long end() {
        return System.currentTimeMillis() - commandStart;
    }

    private void begin() {
        commandStart = System.currentTimeMillis();
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        begin();
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        logger.driver("Exception found: " + throwable.getClass().getName() + ": message: " + throwable.getMessage()
                + " time: " + end());
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        logger.driver("Navigated back: " + end());
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        logger.driver("Changed value of " + previousBy + ". Original value: '" + originalValue + "' new value: '" +
                element.getAttribute("value") + "' time: " + end());
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        logger.driver("Ran script: '" + script + "' time: " + end());
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        begin();
        previousBy = by;
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        begin();
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        begin();
        originalValue = element.getAttribute("value");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        logger.driver("Navigated forward: " + end());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        logger.driver("Clicked on: " + previousBy.toString() + " " + end());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        logger.driver("Found element: " + previousBy.toString() + " " + end());
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        begin();
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        begin();
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        logger.driver("Navigated to: " + url + " " + end());
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        begin();
    }
}
