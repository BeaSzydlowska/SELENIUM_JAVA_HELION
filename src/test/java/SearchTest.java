import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pop.HomePage;
import pop.ProductPage;
import pop.SearchPage;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SearchTest {
    public WebDriver driver;
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductPage productPage;
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
    public void searchingNonExistingProduct() {
        homePage = new HomePage(driver);
        searchPage = homePage.searchProduct("fdfdsggsdfhjhfhfghfgh");
        Assert.assertFalse(searchPage.searchResultsIsNotEmpty(), "There is a product on a list");
        Assert.assertTrue(searchPage.getMessageWhenNoResults().contains("Nie znaleziono szukanej frazy"), "Product exist in our database");


    }

}
