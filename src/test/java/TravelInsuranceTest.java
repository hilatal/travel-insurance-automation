package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TravelInsuranceTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void purchaseFlowTest() {
        // 1. פותח את האתר
        driver.get("https://digital.harel-group.co.il/travel-policy");

        // 2. לוחץ על "לרכישה בפעם הראשונה"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-hrl-bo='purchase-for-new-customer']")
        )).click();
        // 3. בוחר את היבשת הרצויה, במקרה שלי "אירופה"
        WebElement europeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='אירופה']/..")
        ));
        europeButton.click();

        // 4. לחץ על "הלאה לבחירת תאריכי הנסיעה"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-hrl-bo='wizard-next-button']")
        )).click();

        // 5. בחירת תאריכים באמצעות Date Picker
        LocalDate departureDate = LocalDate.now().plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        // ---------- תאריך יציאה ----------
        WebElement departureInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("travel_start_date")
        ));
        departureInput.click();

        String depDateAttr = departureDate.toString(); // yyyy-MM-dd

        WebElement depDayButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[data-hrl-bo='" + depDateAttr + "']")
        ));
        depDayButton.click();

        // ---------- תאריך חזרה ----------
        WebElement returnInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("travel_end_date")
        ));
        returnInput.click();

        String retDateAttr = returnDate.toString();

        WebElement retDayButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[data-hrl-bo='" + retDateAttr + "']")
        ));
        retDayButton.click();

        // 6. לחץ על "הלאה לפרטי הנוסעים"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("nextButton")
        )).click();

        // 7. המתנה למעבר לדף פרטי הנוסעים
        wait.until(ExpectedConditions.urlContains("passengers"));

        // 8. בדיקה שהדף נפתח
        Assert.assertTrue(
                driver.getCurrentUrl().contains("passengers"),
                "דף פרטי הנוסעים לא נפתח"
        );
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
