import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumEditDogPage {
    WebDriver driver;

    public SeleniumEditDogPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="name")
    WebElement nameInput;

    @FindBy(id="breed")
    WebElement breedInput;

    @FindBy(id = "submit")
    WebElement submitButton;
    public final String URL = "http://localhost:8081/editDog/1";

    public void open(){
        driver.get(URL);
    }

    public void fillInNames(){
        nameInput.clear();
        breedInput.clear();
        nameInput.sendKeys("EditDog");
        breedInput.sendKeys("EditBreed");
    }

    public void clickSubmitButton(){
        submitButton.click();
    }
}
