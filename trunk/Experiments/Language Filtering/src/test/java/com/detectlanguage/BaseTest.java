package com.detectlanguage;

import org.junit.Before;

public class BaseTest {
    public static String TEST_API_KEY = "73bc07b472b0ab36b055f0a56d3eb4c9";

    @Before
    public void setUp() {
        DetectLanguage.apiHost = System.getProperty("detectlanguage_api_host",
                DetectLanguage.apiHost);
        DetectLanguage.apiKey = System.getProperty("detectlanguage_api_key",
                TEST_API_KEY);
    }
}
