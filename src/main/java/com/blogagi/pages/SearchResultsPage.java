package com.blogagi.pages;

import com.blogagi.config.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private final By searchResults = By.cssSelector(Configuration.SEARCH_RESULTS_SELECTOR);
    private final By noResultsMessage = By.cssSelector(Configuration.NO_RESULTS_MESSAGE_SELECTOR);
    private final By articleTitle = By.cssSelector("h2.entry-title, h3.entry-title");
    private final By articleLink = By.cssSelector("a[rel='bookmark']");
    private final By articleExcerpt = By.cssSelector(".entry-summary, .entry-content");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        waitForResultsLoad();
    }

    @Step("Aguardar carregamento dos resultados")
    private void waitForResultsLoad() {
        try {
            // Esperar por resultados ou mensagem de erro
            wait.until(driver ->
                !findElements(searchResults).isEmpty() ||
                    !findElements(noResultsMessage).isEmpty()
            );
        } catch (Exception e) {
            logger.warn("Timeout waiting for search results", e);
        }
    }

    @Step("Obter número de resultados")
    public int getResultsCount() {
        List<WebElement> results = findElements(searchResults);
        int count = results.size();
        logger.info("Found {} search results", count);
        return count;
    }

    @Step("Verificar se há resultados")
    public boolean hasResults() {
        return getResultsCount() > 0;
    }

    @Step("Verificar se mensagem 'sem resultados' é exibida")
    public boolean isNoResultsMessageDisplayed() {
        boolean displayed = isDisplayed(noResultsMessage);
        logger.info("No results message displayed: {}", displayed);
        return displayed;
    }

    @Step("Obter títulos dos resultados")
    public List<String> getResultTitles() {
        return findElements(articleTitle).stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    @Step("Obter snippets dos resultados")
    public List<String> getResultExcerpts() {
        return findElements(articleExcerpt).stream()
            .limit(5) // Limitar a 5 para performance
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    @Step("Verificar se resultados contêm termo: {searchTerm}")
    public boolean resultsContainTerm(String searchTerm) {
        String term = searchTerm.toLowerCase();

        List<String> titles = getResultTitles();
        List<String> excerpts = getResultExcerpts();

        boolean foundInTitles = titles.stream()
            .anyMatch(title -> title.toLowerCase().contains(term));

        boolean foundInExcerpts = excerpts.stream()
            .anyMatch(excerpt -> excerpt.toLowerCase().contains(term));

        boolean found = foundInTitles || foundInExcerpts;
        logger.info("Term '{}' found in results: {}", searchTerm, found);

        return found;
    }

    @Step("Validar que todos os links são válidos")
    public boolean allLinksAreValid() {
        List<WebElement> links = findElements(articleLink);

        return links.stream().allMatch(link -> {
            String href = link.getAttribute("href");
            boolean valid = href != null && href.startsWith("http");
            if (!valid) {
                logger.warn("Invalid link found: {}", href);
            }
            return valid;
        });
    }

    @Step("Obter URL do primeiro resultado")
    public String getFirstResultUrl() {
        List<WebElement> links = findElements(articleLink);
        if (links.isEmpty()) {
            return null;
        }
        return links.get(0).getAttribute("href");
    }
}
