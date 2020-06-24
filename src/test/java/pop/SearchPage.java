package pop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchPage {
    private WebDriver driver;

    private List<WebElement> listOfSearchedProductsNames;

    @FindBy(xpath = "//span [@id='select2-sortby-container']")
    private WebElement sortingDefault;
    @FindBy(className = "not-found")
    private WebElement noSearchResults;


    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Sprawdza co jest ustawione jako domyslne sortowanie
     *
     * @return nazwe domyslnego sortowania
     */
    public String getDefaultSortingText() {
        return sortingDefault.getText();
    }

    /**
     * Sprawdza czy rezultat wyszukiwania nie jest pusty (czy znaleziono produkt spełniajacy warunki wyszukiwania). Jeśli lista jest niepusta zwraca true
     *
     * @return
     */
    public boolean searchResultsIsNotEmpty() {
        try {
            listOfSearchedProductsNames = driver.findElements(By.xpath("//ul[@class='list']/li//h3/a"));
            if (listOfSearchedProductsNames.size() <= 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Wybiera produkt zlisty po zadanym indeksie
     *
     * @param index
     * @return
     */
    public ProductPage chooseProductFromListByIndex(int index) {
        listOfSearchedProductsNames.get(index).click();
        return new ProductPage(driver);
    }

    /**
     * Zwraca napis "Nie znaleziono szukanej frazy"
     *
     * @return
     */
    public String getMessageWhenNoResults() {
        return noSearchResults.getText();
    }

}
