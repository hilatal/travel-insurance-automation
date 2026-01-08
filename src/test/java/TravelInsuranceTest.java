package tests;  // ודאי שהקובץ נמצא בתיקייה src/test/java/tests/

import io.github.bonigarcia.wdm.WebDriverManager; // מנהל דרייבר
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
        // משתמשים ב‑WebDriverManager → לא צריך להוריד או לשים דרייבר ידני
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
                By.xpath("//button[contains(text(),'לרכישה בפעם הראשונה')]")
        )).click();

        // 3. בוחר יבשת (נניח הראשון)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button"))).click();

        // 4. לוחץ "הלאה לבחירת תאריכי הנסיעה"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'הלאה')]")
        )).click();

        // 5. תאריכי יציאה וחזרה
        LocalDate departureDate = LocalDate.now().plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        // המרת תאריכים לפורמט dd/MM/yyyy (לפי מה שה‑Date Picker דורש)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String depDateStr = departureDate.format(formatter);
        String retDateStr = returnDate.format(formatter);

        // ממלאים את תאריכי ה‑Date Picker
        WebElement departureInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("departureDate")));
        departureInput.clear();
        departureInput.sendKeys(depDateStr);

        WebElement returnInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("returnDate")));
        returnInput.clear();
        returnInput.sendKeys(retDateStr);

        // 6. בדיקת סה"כ ימים
        WebElement totalDays = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalDays")));
        Assert.assertEquals(totalDays.getText(), "30", "סה\"כ הימים אינו תקין");

        // 7. הלאה לפרטי הנוסעים
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'הלאה לפרטי הנוסעים')]")
        )).click();

        // 8. בדיקה שהדף נפתח
        Assert.assertTrue(driver.getCurrentUrl().contains("passengers"),
                "דף פרטי הנוסעים לא נפתח");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
