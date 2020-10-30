package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class ProductPage extends AbstractPage {

    @FindBy(xpath = "//h1[contains(text() ,'computer')]")
    private WebElement productTitle;

    @FindBy(xpath = "//h2/a[contains(@href ,'computer')]")
    private WebElement firstProduct;

    @FindBy(xpath = "//h2/a[@href = '/desktops']")
    private WebElement desktopsSubcategory;

    public String getFirstProductTitle() {
        return firstProduct.getText();
    }

    public ProductPage() {
        super();
    }

    public String getProductTitleText() {
        return productTitle.getText();
    }

    public ProductPage openFirstProduct() {
        String firstProductInTheListName = getFirstProductTitle();
        firstProduct.click();
        wait.until(ExpectedConditions.visibilityOf(productTitle));
        Assert.assertTrue(getProductTitleText().contains(firstProductInTheListName));
        waitUntilPageIsFullyLoaded(wait);
        perfNavigationTiming.writeToJson("ProductPage");
        return this;
    }

}
