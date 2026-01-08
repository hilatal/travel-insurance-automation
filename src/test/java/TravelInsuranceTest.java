package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.time.LocalDate;

public class TravelInsuranceTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void purchaseFlowTest() {
        driver.get("https://digital.harel-group.co.il/travel-policy");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'לרכישה בפעם הראשונה')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button"))).click(); // בחירת יבשת

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'הלאה')]"))).click();

        LocalDate departureDate = LocalDate.now().plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        // כאן נשתמש ב-Date Picker אמיתי (נממש יחד בשלב הבא)

        String totalDaysText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("totalDays"))
        ).getText();

        Assert.assertEquals(totalDaysText, "30", "סה\"כ הימים אינו תקין");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'הלאה לפרטי הנוסעים')]"))).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("passengers"),
                "דף פרטי הנוסעים לא נפתח");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
