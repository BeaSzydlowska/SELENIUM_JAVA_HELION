package utils;
import org.openqa.selenium.WebDriver;
import pop.HomePage;


public class Utils {
    private WebDriver driver;

    public Utils (WebDriver driver){
        this.driver = driver;

    }
    public HomePage goToHomePage(){
        driver.get("https://helion.pl/");
        return new HomePage(driver);
    }
}
