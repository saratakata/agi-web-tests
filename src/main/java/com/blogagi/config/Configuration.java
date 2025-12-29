package com.blogagi.config;

public class Configuration {

    // URLs
    public static final String BASE_URL = "https://blogdoagi.com.br/";
    public static final String SEARCH_URL = BASE_URL + "?s=";

    // Browser Configuration
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final boolean HEADLESS = Boolean.parseBoolean(
            System.getProperty("headless", "false")
    );

    // Timeouts (segundos)
    public static final int DEFAULT_TIMEOUT = Integer.parseInt(
            System.getProperty("timeout", "30")
    );
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int SCRIPT_TIMEOUT = 10;

    // Screenshot Configuration
    public static final boolean SCREENSHOT_ON_FAILURE = Boolean.parseBoolean(
            System.getProperty("screenshotOnFailure", "true")
    );
    public static final String SCREENSHOT_PATH = "build/screenshots/";

    // Search Configuration
    public static final int MIN_SEARCH_RESULTS = 1;
    public static final int MAX_SEARCH_TIME_SECONDS = 5;

    // Selectors
//    public static final String SEARCH_ICON_SELECTOR = ".header-search-button";
    public static final String SEARCH_ICON_SELECTOR = ".ast-icon > .ahfb-svg-iconset";
    public static final String SEARCH_INPUT_SELECTOR = "input[type='search']";
    public static final String SEARCH_RESULTS_SELECTOR = "article.post";
    public static final String NO_RESULTS_MESSAGE_SELECTOR = ".no-results";

    // Valid Search Terms
    public static final String[] VALID_SEARCH_TERMS = {
            "empréstimo", "cartão", "pix", "INSS", "consignado"
    };

    // Invalid Search Terms
    public static final String[] INVALID_SEARCH_TERMS = {
            "xyzabc123", "asdfghjkl", "qwerty999"
    };

    private Configuration() {
        throw new UnsupportedOperationException("Utility class");
    }
}
