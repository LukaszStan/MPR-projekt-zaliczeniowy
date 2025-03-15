import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumDeleteDogTest {
    WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver = new EdgeDriver();
    }

    @Test
    public void deleteDog(){
        driver.get("http://localhost:8081/index");
        Assertions.assertThat(driver.getPageSource()).contains("aaa");
        driver.findElement(By.xpath("//td[text()='aaa']/following-sibling::td/a[text()='Delete']")).click();
        Assertions.assertThat(driver.getCurrentUrl()).contains("index");
        Assertions.assertThat(driver.getPageSource()).doesNotContain("aaa");
    }
}
