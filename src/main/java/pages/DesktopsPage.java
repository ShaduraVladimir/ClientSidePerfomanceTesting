package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static util.WebDriverHolder.getDriver;

public class DesktopsPage extends AbstractPage {

    @FindBy(xpath = "//h2/a[contains(@href ,'computer')]")
    private WebElement firstProduct;

    @FindBy(xpath = "//h2/a[@href = '/desktops']")
    private WebElement desktopsSubcategory;

    public DesktopsPage() {
        super();
    }

    public String getDesktopsPageTitle() {
        return desktopsSubcategory.getText().toLowerCase().trim();
    }

    public DesktopsPage openDesktopsPage() {
        waitUntilPageIsFullyLoaded(wait);
        wait.until(ExpectedConditions.elementToBeClickable(desktopsSubcategory));
        String title = getDesktopsPageTitle();
        desktopsSubcategory.click();
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct));
        waitUntilPageIsFullyLoaded(wait);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(title));
        perfNavigationTiming.writeToJson("DesktopsPage");
        return this;
    }

}
