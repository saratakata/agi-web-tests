package com.blogagi.pages;

//import com.blogagi.config.Configuration;
//import io.qameta.allure.Step;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;

import com.blogagi.config.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {

    // Locators
//    private final By searchLinkXPath = By.cssSelector(".ast-icon > .ahfb-svg-iconset");
//    private final By searchInput = By.cssSelector(Configuration.SEARCH_INPUT_SELECTOR);
//    private final By searchForm = By.cssSelector("form.search-form");


    private final By searchIcon = By.cssSelector(Configuration.SEARCH_ICON_SELECTOR);
    private final By searchInput = By.cssSelector(Configuration.SEARCH_INPUT_SELECTOR);
    private final By searchForm = By.cssSelector("form.search-form");


    @FindBy(css = ".site-logo")
    private WebElement logo;

    public HomePage(WebDriver driver) {

        super(driver);


    }

    @Step("Acessar pagina inicial do Blog do Agi")
    public HomePage open() {
        logger.info("Accessing Blog do Agi homepage: {}", Configuration.BASE_URL);
        driver.get(Configuration.BASE_URL);
        waitForPageLoad();
        return this;
    }

    @Step("Verificar se pagina inicial foi carregada")
    public boolean isPageLoaded() {
        try {
            return logo.isDisplayed();
        } catch (Exception e) {
            logger.error("Homepage not loaded properly", e);
            return false;
        }
    }

    @Step("Clicar no icone de pesquisa")
    public HomePage clickSearchIcon() {
        logger.info("Clicking search icon");
        logger.info("vai a merdaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa");
        click(searchIcon);
        waitForSearchFieldVisible();
        return this;
    }

    @Step("Digitar termo de pesquisa: {searchTerm}")
    public HomePage typeSearchTerm(String searchTerm) {
        logger.info("Typing search term: {}", searchTerm);
        type(searchInput, searchTerm);
        return this;
    }

    @Step("Executar pesquisa")
    public SearchResultsPage executeSearch() {
        logger.info("Executing search");
        WebElement input = findElement(searchInput);
        input.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    @Step("Pesquisar por: {searchTerm}")
    public SearchResultsPage search(String searchTerm) {
        clickSearchIcon();
        typeSearchTerm(searchTerm);
        return executeSearch();
    }

    public boolean isSearchFieldVisible() {
        return isDisplayed(searchInput);
    }

    private void waitForPageLoad() {
        wait.until(driver ->
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }

    private void waitForSearchFieldVisible() {
        waitForElementVisible(searchInput);
    }
}

