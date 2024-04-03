package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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

                List<String> users;
                Locator credentialLocator = page.locator("#login_credentials");
                Locator passwordLocator = page.locator(".login_password");
                users = Arrays.stream(credentialLocator.allInnerTexts().get(0).split("\n")).filter(word -> word.contains("_user")).toList();
                String user = users.stream().findAny().orElse(null);
                String password = passwordLocator.textContent().split(":")[1];

                Locator usernameInput = page.getByPlaceholder("Username");
                Locator passwordInput = page.getByPlaceholder("Password");

                usernameInput.fill(user);
                passwordInput.fill(password);

                assertThat(usernameInput).hasValue(user);
                assertThat(passwordInput).hasValue(password);

                Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
                loginButton.click();
            }
        }
    }
}