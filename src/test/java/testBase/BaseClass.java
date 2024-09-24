package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	
public static WebDriver driver;

public Logger logger; //Log4j

public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression", "Master","DataDriven"})
	@Parameters({"os","browser"})
	 public void setUp(String os, String br) throws IOException
	{	
		
		//Loding the config.properties file
			FileReader file= new FileReader(".//src//test//resources//config.properties");
			p=new Properties(); //creation of object 
			p.load(file);
		
		
		
		
		logger = LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
			
			DesiredCapabilities capabilities= new DesiredCapabilities();
			
			//capabilities.setPlatform(Platform.WIN11);
			//capabilities.setBrowserName("chrome");
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}else if(os.equalsIgnoreCase("Linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}else
			{
				System.out.println("no matching OS");
				return;
			}
			
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			default: System.out.println("Inavalid browser name..."); return;
			}
			driver= new RemoteWebDriver(new URL("http://10.0.0.253:4444"),capabilities);
		}
		
		
		//browser
		if(p.getProperty("execution_env").equalsIgnoreCase("local")){	
		switch(br.toLowerCase())
		{
		case "chrome" : driver=new ChromeDriver(); break;
		case "edge" :driver=new EdgeDriver(); break;
		case "firefox" : driver=new FirefoxDriver(); break;
		default: System.out.println("Inavalid browser name..."); return;
		}
}
		
		
		
		
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	//	driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
		driver.get(p.getProperty("appURL"));  //reading url from properties file.
		driver.manage().window().maximize();
	}
	
	
	@AfterClass(groups= {"Sanity","Regression", "Master","DataDriven"})
	public void tearDown() {
		driver.quit();
		
	}
	
	
	public String randomString() {
		String generatedString= RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber() {
		String generatedNumber= RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric() {
		String generatedAlphanum= RandomStringUtils.randomAlphanumeric(7);
		return generatedAlphanum;
	}
	
	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot TS= (TakesScreenshot) driver;
		File sourceFile = TS.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath= System.getProperty("user.dir")+"\\screenshots\\"+ tname+"_"+timeStamp;
		File targetFile= new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
		
	}


}
