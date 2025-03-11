package stepDefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AddCartSteps {

	WebDriver driver;
	
	@Before
	public void start() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com");
	}
	@Given("the user is logged into Saucedemo")
	public void the_user_is_logged_into_saucedemo() throws InterruptedException {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        Thread.sleep(2000);
        driver.findElement(By.id("login-button")).click();
	}
	@BeforeStep
    public void beforeEachStep() {
        System.out.println("Executing step...");
    }
	@When("the user adds the product to the cart")
	public void the_user_adds_the_product_to_the_cart() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
	}
	@Then("the cart should contain the product")
	public void the_cart_should_contain_the_product() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.className("shopping_cart_link")).click();
        boolean productExists = driver.findElements(By.xpath("//*[@id=\"item_4_title_link\"]/div")).size() > 0;
        Assert.assertTrue(productExists,"Product Does Not Exist!");
	}
	@Given("the user has a product in the cart")
    public void userHasProductInCart() throws InterruptedException {
		Thread.sleep(2000);
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @When("the user proceeds to checkout with the following details:")
    public void userProceedsToCheckout(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        Thread.sleep(2000);
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys(data.get(0).get("FirstName"));
        driver.findElement(By.id("last-name")).sendKeys(data.get(0).get("LastName"));
        driver.findElement(By.id("postal-code")).sendKeys(data.get(0).get("ZipCode"));
        Thread.sleep(2000);
        driver.findElement(By.id("continue")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("finish")).click();
    }

    @Then("the order confirmation message should be displayed")
    public void verifyOrderConfirmation() throws InterruptedException {
    	Thread.sleep(2000);
        String confirmationMessage = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals("Thank you for your order!", confirmationMessage);
    }
    @Given("the user is on the inventory page")
    public void userIsOnInventoryPage() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    @When("the user selects the filter {string}")
    public void userSelectsFilter(String filterOption) throws InterruptedException {
        Select filterDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        Thread.sleep(2000);
        filterDropdown.selectByVisibleText(filterOption);
    }

    @Then("the products should be sorted in ascending order")
    public void verifyProductsSortedAscending() {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = priceElements.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(expectedPrices, actualPrices , "Products are not sorted correctly!");
    }
	@AfterStep
    public void afterEachStep() {
        System.out.println("Step execution completed.");
    }
    @After
    public void stop() throws InterruptedException {
    	Thread.sleep(2000);
        driver.close();
    }

}
