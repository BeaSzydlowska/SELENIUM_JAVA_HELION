package pop;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    @FindBys({
            @FindBy(xpath = "//table[@id='koszyk']/tbody/tr[@class='pozycja']")
    })
    private List<WebElement> rows;
    @FindBy(xpath = "//a[contains(text(),'zaznaczone')]")
    private WebElement removeSelected;
    @FindBy(xpath = "//th[@class='checkbox']//span[@class='input']")
    private WebElement selectAll;
    @FindBy(xpath = "//p[contains(text(),'Twój koszyk jest pusty')]")
    private WebElement emptyBasket;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * umożliwia zwrócenie tytułu produktu znajdującego sie w koszyku"
     *
     * @return tytuł
     */
    public String getProductTitle() {
        return rows.get(0).findElement(By.xpath("td[@class='desc']//h2/a")).getText();
//        for (WebElement row : rows) {
//            WebElement title = row.findElement(By.xpath("td[@class='desc']//h2/a"));
//            Reporter.log(title.getText(), true);
//            return title.getText();
//        }
//        return null;
    }

    /**
     * umożliwia zwrócenie listy tytułów  znajdujących się w koszyku"
     *
     * @return productsTitleList
     */
    public List<String> getProductsTitleList() {
        List<String> productsTitleList = new ArrayList<>();
        for (WebElement row : rows) {
            WebElement title = row.findElement(By.xpath("td[@class='desc']//h2/a"));
//            Reporter.log("Dodaję element: " + title.getText(),true);
            productsTitleList.add(title.getText());
        }
        for (String element : productsTitleList){
            Reporter.log(element, true);
        }
        return productsTitleList;
    }


    /**
     * umożliwia klikniecie w "Usuń zaznaczone" a nastepnie zastwierdza alert"
     */
    public void removeSelected() {
        removeSelected.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    /**
     * umożliwia zaznaczenie wszystkich pozycji w koszyku
     */
    public void selectAllProducts() {
        selectAll.click();
    }

    /**
     * Sprawdza, czy koszyk został opróżniony ze wszystkich produktów. Jesli nie = zwróci false
     * @return true or false
     */
    public boolean ifallProductsAreRemoved() {
        try {
            emptyBasket.getText();
            return true;
        } catch (Exception exceptionCart) {
            return false;
        }
    }

    /**
     * Jesli koszyk został opróżniony zwraca napis "Koszyk jest pusty.
     * @return emptyBasket.getText()
     */
    public String getEmptyCartText(){
        return emptyBasket.getText();
    }


    /**
     * klika na wybrany checkbox po tytule produktu
     * @param product
     */
    public void clickOnSelectedCheckbox(String product) {
        for (WebElement row : rows) {
            WebElement title = row.findElement(By.xpath("td[@class='desc']//h2/a"));
            if (title.getText().contains(product)) {
                WebElement checkbox = row.findElement(By.xpath("td/div/label/span[@class='input']"));
                checkbox.click();
            }
        }
    }

}