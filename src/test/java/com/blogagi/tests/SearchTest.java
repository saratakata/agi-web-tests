package com.blogagi.tests;

import com.blogagi.base.BaseTest;
import com.blogagi.config.Configuration;
import com.blogagi.pages.HomePage;
import com.blogagi.pages.SearchResultsPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Blog do Agi")
@Feature("Funcionalidade de Pesquisa")
@DisplayName("Testes de Pesquisa - Cenários Positivos")
public class SearchTest extends BaseTest {

    @Test
    @Tag("smoke")
    @Tag("search")
    @Story("Pesquisa com termo válido")
    @DisplayName("TC001: Deve retornar resultados ao pesquisar por 'empréstimo'")
    @Description("Valida que a pesquisa retorna resultados relevantes para um termo existente")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnResultsForValidTerm() {
        // Arrange
        String searchTerm = "empréstimo";
        HomePage homePage = new HomePage(driver);

        // Act
        homePage.open();

        assertThat(homePage.isPageLoaded())
                .as("Página inicial deve carregar corretamente")
                .isTrue();

        homePage.clickSearchIcon();

        assertThat(homePage.isSearchFieldVisible())
                .as("Campo de pesquisa deve estar visível")
                .isTrue();

        SearchResultsPage resultsPage = homePage.search(searchTerm);

        // Assert
        assertThat(resultsPage.hasResults())
                .as("Deve haver pelo menos 1 resultado")
                .isTrue();

        assertThat(resultsPage.getResultsCount())
                .as("Deve haver no mínimo %d resultados", Configuration.MIN_SEARCH_RESULTS)
                .isGreaterThanOrEqualTo(Configuration.MIN_SEARCH_RESULTS);

        assertThat(resultsPage.resultsContainTerm(searchTerm))
                .as("Resultados devem conter o termo pesquisado")
                .isTrue();

        assertThat(resultsPage.allLinksAreValid())
                .as("Todos os links dos resultados devem ser válidos")
                .isTrue();

        logger.info("Test passed: Found {} results for '{}'",
                resultsPage.getResultsCount(), searchTerm);
    }

    @ParameterizedTest
    @ValueSource(strings = {"cartão", "pix", "INSS", "consignado"})
    @Tag("regression")
    @Tag("search")
    @Story("Pesquisa com múltiplos termos válidos")
    @DisplayName("TC003: Deve retornar resultados para diferentes termos válidos")
    @Description("Valida que a pesquisa funciona corretamente com diferentes termos")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnResultsForMultipleValidTerms(String searchTerm) {
        // Arrange
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search(searchTerm);

        // Assert
        assertThat(resultsPage.hasResults())
                .as("Deve haver resultados para o termo '%s'", searchTerm)
                .isTrue();

        assertThat(resultsPage.resultsContainTerm(searchTerm))
                .as("Resultados devem conter o termo '%s'", searchTerm)
                .isTrue();

        logger.info("Found {} results for term: {}",
                resultsPage.getResultsCount(), searchTerm);
    }

    @Test
    @Tag("smoke")
    @Tag("search")
    @Story("Validação de elementos da página")
    @DisplayName("TC004: Deve validar elementos da interface de pesquisa")
    @Description("Verifica se todos os elementos necessários estão presentes e funcionais")
    @Severity(SeverityLevel.NORMAL)
    public void shouldValidateSearchInterfaceElements() {
        // Arrange & Act
        HomePage homePage = new HomePage(driver).open();

        // Assert - Página inicial
        assertThat(homePage.isPageLoaded())
                .as("Logo deve estar visível")
                .isTrue();

        // Assert - Abrir pesquisa
        homePage.clickSearchIcon();

        assertThat(homePage.isSearchFieldVisible())
                .as("Campo de pesquisa deve estar visível após clicar no ícone")
                .isTrue();

        logger.info("All search interface elements are present and functional");
    }

    @Test
    @Tag("regression")
    @Tag("search")
    @Story("Validação de conteúdo dos resultados")
    @DisplayName("TC005: Deve validar estrutura dos resultados de pesquisa")
    @Description("Verifica se os resultados possuem todos os elementos esperados")
    @Severity(SeverityLevel.NORMAL)
    public void shouldValidateSearchResultsStructure() {
        // Arrange
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search("empréstimo");

        // Assert
        assertThat(resultsPage.getResultTitles())
                .as("Deve haver títulos nos resultados")
                .isNotEmpty()
                .allMatch(title -> !title.isEmpty(), "Títulos não devem estar vazios");

        String firstResultUrl = resultsPage.getFirstResultUrl();
        assertThat(firstResultUrl)
                .as("Primeiro resultado deve ter URL válida")
                .isNotNull()
                .startsWith("https://");

        logger.info("Search results structure is valid");
    }

    @Test
    @Tag("performance")
    @Tag("search")
    @Story("Performance da pesquisa")
    @DisplayName("TC006: Pesquisa deve carregar em tempo aceitável")
    @Description("Valida que os resultados carregam dentro do tempo esperado")
    @Severity(SeverityLevel.MINOR)
    public void shouldLoadSearchResultsWithinAcceptableTime() {
        // Arrange
        HomePage homePage = new HomePage(driver);
        long startTime = System.currentTimeMillis();

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search("pix");

        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;

        // Assert
        assertThat(resultsPage.hasResults())
                .as("Resultados devem ser carregados")
                .isTrue();

        assertThat(loadTime)
                .as("Pesquisa deve carregar em menos de %d segundos",
                        Configuration.MAX_SEARCH_TIME_SECONDS)
                .isLessThanOrEqualTo(Configuration.MAX_SEARCH_TIME_SECONDS);

        logger.info("Search completed in {} seconds", loadTime);
    }
}
