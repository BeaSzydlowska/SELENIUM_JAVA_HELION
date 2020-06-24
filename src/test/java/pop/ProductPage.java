package pop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class ProductPage {
    private WebDriver driver;

    @FindBy(xpath = "//h1/span[@itemprop ='name']")
    private WebElement title;

    @FindBy(xpath = "//fieldset[@class='active']/p[2]/a[contains(text(), 'Dodaj do koszyka')]")
    private WebElement addToBasketButton;

    @FindBy(className = "amount-button" )
    private WebElement amountButton;

    public ProductPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * zwraca tytuł produktu
     * @return tytuł
     */
    public String getProductTitle(){
        return title.getText();
    }

//    public void enterBookAmount(String quantity){
//        amountButton.clear();
//        amountButton.sendKeys(quantity);
//    }

    /**
     * klika w button"Dodaj do koszyka"
     * @return stronę koszyka
     */
    public CartPage addToCart(){
        addToBasketButton.click();
        return new CartPage(driver);
    }
}
