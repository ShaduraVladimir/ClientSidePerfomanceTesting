package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static util.WebDriverHolder.getDriver;

public class ComputersPage extends AbstractPage {

    @FindBy(xpath = "//h2/a[@href = '/desktops']")
    private WebElement desktopsSubcategory;

    public ComputersPage() {
        super();
    }

    public String getComputersPageTitle() {
        return computerCategory.getText().toLowerCase().trim();
    }

    public ComputersPage openComputersPage() {
        String title = getComputersPageTitle();
        computerCategory.click();
        wait.until(ExpectedConditions.elementToBeClickable(desktopsSubcategory));
        waitUntilPageIsFullyLoaded(wait);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(title));
        perfNavigationTiming.writeToJson("ComputersPage");
        return this;
    }
}
