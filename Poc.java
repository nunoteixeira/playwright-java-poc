package org.example;

import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

public class Poc {
    public static void main(String[] args) {
        Page page;
        BrowserContext context;
        BrowserType browserType;
        BrowserType.LaunchOptions launchOptions;

        try (Playwright playwright = Playwright.create()) {

            browserType = playwright.chromium();
            launchOptions = new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized"));
            try (Browser browser = browserType.launch(launchOptions)) {
                context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));

                page = context.newPage();
                page.navigate("https://www.saucedemo.com/");

                List<String> users = new ArrayList<>();
                List<Locator> locators = new ArrayList<>();
            }
        }
    }
}