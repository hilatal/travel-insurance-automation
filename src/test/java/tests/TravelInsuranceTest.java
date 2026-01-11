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

public class TravelInsuranceTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void purchaseFlowTest() {
        // 1. Open the website
        driver.get("https://digital.harel-group.co.il/travel-policy");

        // 2. Press on "לרכישה בפעם הראשונה"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-hrl-bo='purchase-for-new-customer']"))
        ).click();

        // 3. Select the desired continent, in my case "אירופה"
        WebElement europeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='אירופה']/.."))
        );
        europeButton.click();

        // 4. Press on "הלאה לבחירת תאריכי הנסיעה"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-hrl-bo='wizard-next-button']"))
        ).click();

        // 5. Selecting dates using Date Picker
        LocalDate departureDate = LocalDate.now().plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        // ---------- Selecting 'תאריך יציאה' ----------
        WebElement departureInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("travel_start_date"))
        );
        departureInput.click();

        String depDateAttr = departureDate.toString(); // yyyy-MM-dd

        WebElement depDayButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[data-hrl-bo='" + depDateAttr + "']"))
        );
        depDayButton.click();

        // ---------- Selecting 'תאריך חזרה' ----------
        WebElement returnInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("travel_end_date"))
        );
        returnInput.click();

        String retDateAttr = returnDate.toString();

        WebElement retDayButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[data-hrl-bo='" + retDateAttr + "']"))
        );
        retDayButton.click();

        // 6. Press on "הלאה לפרטי הנוסעים"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("nextButton"))
        ).click();

        // 7. Waiting to move to the 'פרטי נוסעים' page
        wait.until(ExpectedConditions.urlContains("passengers"));

        // 8. Checking that the page has been opened
        Assert.assertTrue(
                driver.getCurrentUrl().contains("passengers"),
                "דף פרטי הנוסעים לא נפתח"
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
                // Ignore exceptions if the browser is already closed
            }
        }
    }
}