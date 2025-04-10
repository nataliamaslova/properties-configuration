package ui;

import configs.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginFormTests {
    WebDriver driver;
    Actions actions;

//    TestConfig config = new TestConfig();
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    String baseUrl = config.getBaseUrl();

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getTimeout()));
        actions = new Actions(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void openHomePageTest() {
        driver.get(baseUrl);

        assertEquals(baseUrl, driver.getCurrentUrl());
        assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openLoginPageTest() {
        driver.get(baseUrl);
        WebElement loginPage = driver.findElement(By.xpath("//a[@href = 'login-form.html']"));
        actions.moveToElement(loginPage).perform();
        loginPage.click();

        WebElement title = driver.findElement(By.className("display-6"));

        assertEquals(baseUrl + "login-form.html", driver.getCurrentUrl());
        assertEquals("Login form", title.getText());
    }

    @Test
    void signInTest() {
        if(config.isRemote()) {
            System.out.println("Remote testing");
        } else {
            System.out.println("Local testing");
        }
        System.out.println("timeout = " + config.getTimeout());

        driver.get(baseUrl);
        WebElement loginPage = driver.findElement(By.xpath("//a[@href = 'login-form.html']"));
        actions.moveToElement(loginPage).perform();
        loginPage.click();

        driver.findElement(By.id("username")).sendKeys(config.getUsername());
        driver.findElement(By.id("password")).sendKeys(config.getPassword());
        driver.findElement(By.xpath("//button[@type = 'submit']")).click();
        WebElement message = driver.findElement(By.className("alert"));

        assertEquals("Login successful", message.getText());
    }
}
