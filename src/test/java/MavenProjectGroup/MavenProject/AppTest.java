package MavenProjectGroup.MavenProject;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest 
{
	WebDriver driver;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
       driver.get("https://www.google.com");  
       driver.manage().window().maximize();
       driver.findElement(By.name("q")).sendKeys("Maven project demo.");
       assertFalse(false);
       
    }
    
    
    @BeforeTest
    public void Init()
    {
    	driver = new ChromeDriver();
    }
    @AfterTest
    public void close()
    {
    	driver.close();
    }
}

