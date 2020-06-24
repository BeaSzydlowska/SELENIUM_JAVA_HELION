import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pop.AccountPage;
import pop.HomePage;
import pop.LoginPage;


import java.util.concurrent.TimeUnit;

public class AccountTest {
    public WebDriver driver;
    public HomePage homePage;
    public LoginPage loginPage;
    public AccountPage accountPage;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "./src/test/java/data/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://helion.pl/");
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void loginValidUser() {
        homePage = new HomePage(driver);
        loginPage = homePage.gotoLoginPage();
        accountPage = loginPage.fillValidLoginForm();
        Assert.assertTrue(accountPage.getGreetingText().contains("Witaj"));
    }

    @Test(dependsOnMethods = {"loginValidUser"})
    public void logoutUser() {
        homePage = new HomePage(driver);
        loginPage = accountPage.logoutUser();
        Assert.assertTrue(loginPage.getLogOutText().contains("Zostałeś poprawnie wylogowany"));
    }


}
