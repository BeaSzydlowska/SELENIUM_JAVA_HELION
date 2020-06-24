package pop;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {
    private WebDriver driver;

    @FindBy(xpath ="//a[@id='helloUser']")
    private WebElement greeting;
    @FindBy(xpath ="//a[@class='login-link']")
    private WebElement logoutBtn;
    @FindBy(xpath ="//p[contains(text(),'Aktywacja konta w helion.pl, sprawd')]")
    private WebElement registerConfirmation;
    @FindBy(xpath ="//div[@class='your-profile']//a[contains(text(),'Twoje konto')]")
    private WebElement accountBtn;


    public AccountPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getGreetingText() {
        return greeting.getText();
    }

    public String getRegisterConfirmationText() {
        return registerConfirmation.getText();
    }

    public LoginPage logoutUser(){
        Actions actions = new Actions(driver);
        actions.moveToElement(accountBtn).perform();;
        logoutBtn.click();
        return new LoginPage(driver);
    }
}
