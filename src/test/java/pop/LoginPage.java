package pop;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy (name ="loginemail" )
    private WebElement email;
    @FindBy(name ="haslo")
    private WebElement password ;
    @FindBy (xpath ="//button[contains(text(),'Zaloguj się')]" )
    private WebElement zalogujBtn;
    @FindBy (xpath = "//p[contains(text(),'poprawnie wylogowany')]")
    private WebElement logoutMessage;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AccountPage fillValidLoginForm(){
        email.sendKeys("hivoc67077@qmrbe.com");
        password.sendKeys("haslo12345");
        zalogujBtn.click();
        return new AccountPage(driver);

    }
    public String getLogOutText() {
        return logoutMessage.getText();
    }
}
