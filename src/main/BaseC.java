package main;


import org.testng.annotations.AfterClass;
import com.google.gson.Gson;


import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BaseC {
   
	Map<String,String> parameters = new HashMap<String,String>();
    public  static ArrayList<String> logger = new ArrayList<>();
    private static String parametersIn;
	private static String parametersOut;
    
    private static String inPath = System.getProperty("user.dir") + File.separator + "ats_params.json";
    private static String outPath = System.getProperty("user.dir") + File.separator + "ats_result.json";
	private static Charset charset = Charset.forName("utf-8");

    
    protected WebDriver driver;
    private String testName;
    public static final int waitForElement = 40;
    
    @BeforeSuite
    public void beforeSuit(){
  
    }

    @Parameters("browser")
    @BeforeClass

    public void beforeClass(@Optional("chrome") String browser) {

        if (browser.equalsIgnoreCase("ff")) {
        	
        	
        	System.setProperty("webdriver.gecko.driver", "C:\\octopus\\lib\\geckodriver.exe");
        	DesiredCapabilities caps = DesiredCapabilities.firefox();
        	caps.setCapability("marionette", true);
            driver = new FirefoxDriver(caps);
        }
        if (browser.equalsIgnoreCase("ie")) {
        	 System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
        	 DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        	
          	 driver = new InternetExplorerDriver();
        }
        
        if (browser.equalsIgnoreCase("chrome")) {
        	System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        	DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        	
        
        	
          	 driver = new ChromeDriver();
        }
       
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(waitForElement,TimeUnit.SECONDS );
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    
    @BeforeTest
    public void beforeTest(ITestContext testContext) throws IOException{
      
    readParameters();
    	
    }
    
    
    @AfterTest
    public  void afterTest(){
    	
    }
    
    @BeforeMethod
    public void beforeTest(Method method)  {

        testName = method.getName();
        

    }

    @AfterMethod(alwaysRun=true)
    public void afterMethod(ITestResult result) throws IOException{
    	
    	 System.out.println("Status: "+result.getStatus());      	
    	
         if (result.getStatus() == ITestResult.FAILURE) {
             writeParameters(logger, 
        			 result.getThrowable().getMessage(), false); 
         //  loggerMethods.getScreenShot(driver)
 		     
         } else if (result.getStatus() == ITestResult.SKIP) {
        	 
          } else {
        	  
        	  writeParameters(logger, 
         			 null, true); 
             
              }
        	
    	
    }

    @AfterClass
    public void closeTestClass() throws JSONException, IOException{
    	
       driver.quit();
    }

    
    
public  void writeParameters( List<String> outParametr, String errorMessage, boolean testStatus) throws IOException, JSONException{
		
		System.setOut(new PrintStream (new FileOutputStream(java.io.FileDescriptor.out), true, "utf-8"));
		String result;
		JSONObject resultStatus = new JSONObject();
		resultStatus.put("status",testStatus);
		
		JSONObject errorMessageObject = new JSONObject();
		errorMessageObject.put("stackTrace", errorMessage);
		
		resultStatus.put("error", errorMessageObject);
		
		JSONArray arrayResult = new JSONArray();
		JSONObject itemResult = new JSONObject();
		//LinkedHashMap<String, String> jsonOrderedMap = new LinkedHashMap<String, String>();
		int j = 1;
		int i=1;
		for(String element:outParametr){
			
			if(j==10) j=91;
			if(j==100) j=991;
			itemResult.put("valume_" + j, i+" "+element);
			System.out.println(element);
			++j;
			++i;
		}
		
	//	JSONObject itemResult = new JSONObject(jsonOrderedMap);
		
	//	JSONArray arrayResult = new JSONArray(Arrays.asList(itemResult));
		arrayResult.put(itemResult);		
		
		resultStatus.put("result", arrayResult);

		result = resultStatus.toString();
		
		
		
		if (fileExist(outPath)){
			
			Path path = Paths.get(outPath);
			Files.delete(path);
		}
			
		
		
		//Files.write( Paths.get(outPath), result.getBytes(), StandardOpenOption.CREATE);
		ArrayList<String> lines = new ArrayList<>();
		lines.add(result);
		Files.write(Paths.get(outPath), lines, charset, StandardOpenOption.CREATE);
		
	}

public static boolean fileExist(String path){
	
	boolean returnValue = false;
	Path oPath = Paths.get(path);
	if (Files.exists(oPath)) {
		returnValue = true;
	} 

	return returnValue;
}


public void readParameters() throws IOException, JSONException{
   	System.setOut(new PrintStream (new FileOutputStream(java.io.FileDescriptor.out), true, "utf-8"));
		System.out.println("Current dir: " + System.getProperty("user.dir"));
		String content = new String(Files.readAllBytes(Paths.get(inPath)),charset);
		JSONObject parametersIn = new JSONObject(content);
		
		
		for (Object key : parametersIn.keySet()) {
			
	        String keyStr = (String)key;
	        Object keyvalue = parametersIn.get(keyStr);
	        System.out.println("key: "+ keyStr + " value: " + keyvalue);
            parameters.put(keyStr, (String) keyvalue);

		}		
}

protected String getParameter(String parameterName){
	String param = parameters.get(parameterName);
	if (param.contains("GEN")){
		
		return param;
		
	} else { return param; }
}



}

