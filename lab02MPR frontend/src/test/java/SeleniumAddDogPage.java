import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumAddDogPage {
    WebDriver driver;

    public SeleniumAddDogPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="name")
    WebElement nameInput;

    @FindBy(id="breed")
    WebElement breedInput;

    @FindBy(id = "submit")
    WebElement submitButton;
    public final String URL = "http://localhost:8082/addDog";

    public void open(){
        driver.get(URL);
    }

    public void fillInNames(){
        nameInput.sendKeys("TestDog");
        breedInput.sendKeys("TestBreed");
    }

    public void clickSubmitButton(){
        submitButton.click();
    }
}
