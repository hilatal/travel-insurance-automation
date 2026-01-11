# Travel Insurance Automation Test

## Description
This project contains an end-to-end automation test written in **Java** using **Selenium WebDriver** and **TestNG**.  
The test automates a basic purchase flow of a travel insurance policy on a real production website.

The goal of the project is to demonstrate:
- UI automation skills
- Working with dynamic elements
- Proper use of waits
- Clean and readable test code
- End-to-end test structure

---

## Technologies
The project is built using the following technologies and tools:

- **Java 17**
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **WebDriverManager**
- **Google Chrome**
- **IntelliJ IDEA**

---

## Project Structure
    src
    └── test
        └── java
            └── tests
                └── TravelInsuranceTest.java

---

## Test Scenario
The automated test covers the following scenario:

1. Open the travel insurance purchase page
2. Start the purchase process for a new customer
3. Select a destination continent (Europe)
4. Proceed to the travel dates selection screen
5. Select departure date (7 days from today)
6. Select return date (30 days after departure)
7. Proceed to the passenger details page
8. Validate that the passenger details page is opened successfully

---

## Test Details
- The test uses **explicit waits** (`WebDriverWait`) to ensure elements are loaded and clickable
- Dates are selected dynamically using `LocalDate`
- The test validates successful navigation using a URL assertion
- The browser window is maximized for stability
- The browser session is safely closed after test execution

---

## Assertionsמ
The test includes an assertion to verify that the flow successfully reaches the passenger details page:

```java
Assert.assertTrue(
    driver.getCurrentUrl().contains("passengers"),
    "Passenger details page was not opened"
);

---

## How to Run

- Prerequisites

- Before running the test, make sure you have:

- Java 17 installed and configured

- Maven installed (or IntelliJ built-in Maven)

- Google Chrome installed

Internet connection