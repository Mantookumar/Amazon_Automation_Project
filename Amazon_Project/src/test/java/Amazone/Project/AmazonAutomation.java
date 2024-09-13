package Amazone.Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonAutomation {
	static {
		  System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // Step 1: Open Amazon.in
            driver.get("https://www.amazon.in");
            Thread.sleep(3000); 
            // Step 2: Search for "lg soundbar"
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("lg soundbar");
            searchBox.submit();
            Thread.sleep(3000);

            // Step 3: Sort the results by price: low to high
            WebElement sortDropdown = driver.findElement(By.xpath("//span[@class='a-dropdown-container']"));
            sortDropdown.click();
            Thread.sleep(2000); 

            WebElement lowToHigh = driver.findElement(By.id("s-result-sort-select_1"));
            lowToHigh.click();
            Thread.sleep(3000); 

            // Step 4: Read product names and prices on the first search result page
            List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class, 's-result-item')]"));
            Map<Double, String> productData = new HashMap<>();

            for (WebElement product : products) {
                try {
                    WebElement nameElement = product.findElement(By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal']"));
                    String name = nameElement.getText();
                    double price = 0.0; 
                    try {
                        WebElement priceElement = product.findElement(By.xpath(".//span[@class='a-price-whole']"));
                        price = Double.parseDouble(priceElement.getText().replace(",", ""));
                    } catch (Exception e) {
                    }

                    // Step 5: Store product name and price in a map
                    productData.put(price, name);
                    
                } catch (Exception e) {
                    // Skip if there is an issue finding product details
                    continue;
                }
            }

            // Step 6: Sort the products by price and print them
            List<Double> sortedPrices = new ArrayList<>(productData.keySet());
            Collections.sort(sortedPrices);

            for (Double price : sortedPrices) {
                System.out.println(price + " " + productData.get(price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 7: Close the browser
            driver.quit();
        }
    }
}
