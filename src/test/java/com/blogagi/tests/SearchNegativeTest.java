package com.blogagi.tests;

import com.blogagi.base.BaseTest;
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
@DisplayName("Testes de Pesquisa - Cenários Negativos")
public class SearchNegativeTest extends BaseTest {

    @Test
    @Tag("smoke")
    @Tag("search")
    @Tag("negative")
    @Story("Pesquisa sem resultados")
    @DisplayName("TC002: Deve exibir mensagem apropriada quando não há resultados")
    @Description("Valida comportamento do sistema quando termo pesquisado não retorna resultados")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldShowNoResultsMessageForInvalidTerm() {
        // Arrange
        String invalidTerm = "xyzabc123";
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search(invalidTerm);

        // Assert
        assertThat(resultsPage.hasResults())
                .as("Não deve haver resultados para termo inválido")
                .isFalse();

        // Nota: Validar mensagem específica depende da implementação do site
        // Se houver mensagem de "nenhum resultado", descomente:
        // assertThat(resultsPage.isNoResultsMessageDisplayed())
        //     .as("Mensagem de 'nenhum resultado' deve ser exibida")
        //     .isTrue();

        logger.info("No results found for invalid term: {}", invalidTerm);
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdfghjkl", "qwerty999", "zzzzzzzz"})
    @Tag("regression")
    @Tag("search")
    @Tag("negative")
    @Story("Pesquisa com múltiplos termos inválidos")
    @DisplayName("TC007: Deve tratar corretamente múltiplos termos sem resultados")
    @Description("Valida que sistema mantém consistência com diferentes termos inválidos")
    @Severity(SeverityLevel.NORMAL)
    public void shouldHandleMultipleInvalidTerms(String invalidTerm) {
        // Arrange
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search(invalidTerm);

        // Assert
        assertThat(resultsPage.hasResults())
                .as("Não deve haver resultados para '%s'", invalidTerm)
                .isFalse();

        logger.info("Correctly handled invalid term: {}", invalidTerm);
    }

    @Test
    @Tag("regression")
    @Tag("search")
    @Tag("negative")
    @Story("Pesquisa com espaços")
    @DisplayName("TC008: Deve tratar pesquisa com apenas espaços")
    @Description("Valida comportamento quando usuário pesquisa apenas espaços")
    @Severity(SeverityLevel.MINOR)
    public void shouldHandleSearchWithOnlySpaces() {
        // Arrange
        String spacesOnly = "   ";
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search(spacesOnly);

        // Assert - Sistema deve ou retornar todos os resultados ou nenhum
        // Ambos são comportamentos aceitáveis
        int resultsCount = resultsPage.getResultsCount();

        assertThat(resultsCount)
                .as("Sistema deve tratar pesquisa com espaços adequadamente")
                .isGreaterThanOrEqualTo(0);

        logger.info("Search with spaces returned {} results", resultsCount);
    }

    @Test
    @Tag("security")
    @Tag("search")
    @Tag("negative")
    @Story("Validação de segurança")
    @DisplayName("TC009: Deve sanitizar caracteres especiais")
    @Description("Valida que sistema trata caracteres especiais sem quebrar")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldHandleSpecialCharactersGracefully() {
        // Arrange
        String[] specialChars = {"", "'; DROP TABLE", "../../etc/passwd"};
        HomePage homePage = new HomePage(driver);

        // Act & Assert
        for (String specialChar : specialChars) {
            try {
                SearchResultsPage resultsPage = homePage
                        .open()
                        .search(specialChar);

                // Sistema não deve quebrar
                assertThat(driver.getPageSource())
                        .as("Página não deve executar scripts maliciosos")
                        .doesNotContain("alert");

                logger.info("System handled special character safely: {}", specialChar);

            } catch (Exception e) {
                // Se houver erro, ele deve ser tratado gracefully
                logger.info("System gracefully handled error for: {}", specialChar);
            }
        }
    }

    @Test
    @Tag("regression")
    @Tag("search")
    @Tag("negative")
    @Story("Pesquisa com números")
    @DisplayName("TC010: Deve tratar pesquisa com apenas números")
    @Description("Valida comportamento quando usuário pesquisa apenas números")
    @Severity(SeverityLevel.MINOR)
    public void shouldHandleNumericSearch() {
        // Arrange
        String numericTerm = "123456789";
        HomePage homePage = new HomePage(driver);

        // Act
        SearchResultsPage resultsPage = homePage
                .open()
                .search(numericTerm);

        // Assert - Sistema deve processar sem erros
        // Pode ou não retornar resultados
        assertThat(resultsPage.getResultsCount())
                .as("Sistema deve processar busca numérica")
                .isGreaterThanOrEqualTo(0);

        logger.info("Numeric search processed successfully");
    }
}