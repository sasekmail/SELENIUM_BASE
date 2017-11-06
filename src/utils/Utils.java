package utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import main.BaseC;






public class Utils extends BaseC {

	public static int random(int min, int max){
		
		Random r = new Random(); 
		return  r.nextInt(max-min+1)+min; 	
		
	}	
	
	public static void waitForClickableAndClick(WebElement element, int timeout, WebDriver driver ){
	WebDriverWait wait = new WebDriverWait(driver, timeout); 
	WebElement elementClickable = wait.until(ExpectedConditions.elementToBeClickable(element));
	elementClickable.click();
	}
	
    public static boolean isVisible(WebElement element, int timeout, WebDriver driver) {
    	
    	driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );
    	
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
           
        } catch (TimeoutException exception) {
            return false;
        } finally {driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );}
    }	
    
    public static boolean isVisible(WebElement element,  WebDriver driver) {
    	
    	driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );
    	
        try {
            WebDriverWait wait = new WebDriverWait(driver, BaseC.waitForElement);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
           
        } catch (TimeoutException exception) {
            return false;
        } finally {driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );}
    }	
    public static boolean isPresenceBy(String xpathExpression, int timeout, WebDriver driver) {
    	
    	driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );
    	
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
            return true;
           
        } catch (TimeoutException exception) {
            return false;
        } finally {driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );}
    }
    
    
    
 public static void waitForVisible(WebElement element, int timeout, WebDriver driver) {
    	
    	driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );
    	
        try {
        	
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            
           
        }  finally {driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );}
    }
    
 public static void waitForClickableAndClick(WebDriver driver,WebElement element){
		driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );
		WebDriverWait wait = new WebDriverWait(driver, BaseC.waitForElement);
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    element.click();
	    driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );
		
	}
    
         
    
 public static void waitForInvisible(WebElement element, int timeout, WebDriver driver) {
	 
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));    
    }	
    
    
	
    public static String getDateTime(String format){
    	
   return new SimpleDateFormat(format).format(new GregorianCalendar().getTime());	
    	
    }
    
    
    public static void scrollToAndClick(WebDriver driver, WebElement element){
    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("arguments[0].scrollIntoView()", element); 
    	element.click();	
    	
    }
    
    
    
    public static boolean checkTableCells(List<WebElement> elements, String containsString){
     
    	boolean resultOk=true;
     	for(WebElement element:elements){
     		
     		if (!element.getText().contains(containsString))  resultOk = false;
     		
     	}
    	
    	return resultOk;
    }
    
    public static void waitForLoad(WebDriver driver, int timeout) {
    	driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS );	
        new WebDriverWait(driver, timeout).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        driver.manage().timeouts().implicitlyWait(BaseC.waitForElement,TimeUnit.SECONDS );
       delay(1000);
    }
    
  public static void waitForAsynchronous(WebDriver driver, int timeout){
	  
	  try {
	        WebDriverWait driverWait = new WebDriverWait(driver, timeout);

	        ExpectedCondition<Boolean> expectation;   
	        expectation = new ExpectedCondition<Boolean>() {

	            public Boolean apply(WebDriver driverjs) {

	                JavascriptExecutor js = (JavascriptExecutor) driverjs;
	                return js.executeScript("return (jQuery.active === 0)").equals("true");
	            }
	        };
	        driverWait.until(expectation);
	    }       
	    catch (TimeoutException exTimeout) {

	     
	    }
	    catch (WebDriverException exWebDriverException) {

	       
	    }
	    return;
	  
	  
	  
  }  
    
    
    
    
    static public void delay(int delay){
    	
    	try {
    		Thread.sleep(delay);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}	
    
    }
}
