import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pop.CartPage;
import pop.HomePage;
import pop.ProductPage;
import pop.SearchPage;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CartTest {
    public WebDriver driver;
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private Utils utils;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "./src/test/java/data/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://helion.pl/");
        driver.manage().window().maximize();
        utils = new Utils(driver);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }


    @Test(enabled = true)
    public void searchingByTitle() {
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/test/java/data/titles.csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String productTitle = nextLine[0];
                homePage = new HomePage(driver);
                searchPage = homePage.searchProduct(productTitle);
                Assert.assertTrue(searchPage.searchResultsIsNotEmpty(), "There is no product on a list");
                productPage = searchPage.chooseProductFromListByIndex(0);
                Assert.assertTrue(productPage.getProductTitle().contains(productTitle));
                utils.goToHomePage();
            }

        } catch (IOException | CsvValidationException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Test(enabled = true)
    public void addMultipleProductsToCart() {
        List<String> productsList = new ArrayList<>();
        homePage = new HomePage(driver);
        homePage.acceptCookiePolicy();
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/test/java/data/multiple_products.csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
//                List<String> productsList = new ArrayList<>();
                for (int i = 0; i < nextLine.length; i++) {
                    productsList.add(nextLine[i]);
                }
            }
        } catch (IOException | CsvValidationException ex) {
            System.out.println(ex.getMessage());
        }
        List<String> productAddedToBasket = new ArrayList<>();
        for (String element : productsList) {
            searchPage = homePage.searchProduct(element);
            if (searchPage.searchResultsIsNotEmpty()) {
                Assert.assertTrue(searchPage.searchResultsIsNotEmpty(), "There is no product on a list");
                Assert.assertTrue(searchPage.getDefaultSortingText().contains("Najlepiej pasujące"), "Sorting by best match is not set as default");
                productPage = searchPage.chooseProductFromListByIndex(0);
                Assert.assertTrue(productPage.getProductTitle().contains(element), "incorrect title");
                cartPage = productPage.addToCart();
                productAddedToBasket.add(element);
                Assert.assertTrue(cartPage.getProductTitle().contains(element), "Selected product not added to basket");
                homePage = utils.goToHomePage();
            } else {
                homePage = utils.goToHomePage();
            }
        }
        cartPage = homePage.gotoCartPage();
        Assert.assertEquals(productAddedToBasket.size(), cartPage.getProductsTitleList().size(), "Lists are not equal, some products are missing");

    }


    @Test(dependsOnMethods = {"addMultipleProductsToCart"})
    public void removeAllProductFromCart() throws InterruptedException {
        cartPage.selectAllProducts();
        cartPage.removeSelected();
        Assert.assertTrue(cartPage.ifallProductsAreRemoved(), "Some products are still in cart");
        Assert.assertTrue(cartPage.getEmptyCartText().contains("Twój koszyk jest pusty"), "Your cart is not empty");

        Thread.sleep(5000);
    }

    @Test(dependsOnMethods = {"addMultipleProductsToCart"})
    public void removeSelectedProductFromCart() {
        cartPage.clickOnSelectedCheckbox("Python na start!");
        cartPage.removeSelected();
        if (!cartPage.ifallProductsAreRemoved()) {
            Assert.assertFalse(cartPage.ifallProductsAreRemoved(), "Your cart is  empty");
            for (String element : cartPage.getProductsTitleList()) {
                Assert.assertFalse(element.contains("Tysiąc szklanek herbaty. Spotkania na Jedwabnym Szlaku"), "Something went wrong, propably selected product was not removed");
            }
        }
    }
}

