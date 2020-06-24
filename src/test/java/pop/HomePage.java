package pop;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    @FindBy(id="inputSearch")
    private WebElement searcher;
    @FindBy(id="rodo-ok")
    private WebElement cookie;
    @FindBy(xpath="//span[@class='hideFixed']")
    private WebElement goToBasket;
    @FindBy(xpath= "//div[@class='your-profile']//a[contains(text(),'Twoje konto')]")
    private WebElement accountBtn;
    @FindBy(className="login-link")
    private WebElement loginBtn;
    @FindBy(className= "register-link")
    private WebElement registerBtn;



    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage gotoLoginPage(){
        Actions actions = new Actions(driver);
        actions.moveToElement(accountBtn).perform();;

        loginBtn.click();
        return new LoginPage(driver);
    }
    public SearchPage searchProduct(String product){
        searcher.clear();
        searcher.sendKeys(product);
        searcher.submit();
        return new SearchPage(driver);
    }
    public CartPage gotoCartPage(){
        goToBasket.click();
        return new CartPage(driver);
    }
    public void acceptCookiePolicy(){
        cookie.click();
    }






}
