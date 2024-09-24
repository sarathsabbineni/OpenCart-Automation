package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class Tc002_LoginTest extends BaseClass{
	
	
	
	@Test(groups={"Regression","Master"})
	public void verify_login() {
		
		logger.info("********starting Tc002_LoginTest ******* ");
		
		try {
		//Homepage
		HomePage hp= new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp= new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//My Account Page
		MyAccountPage macc= new MyAccountPage(driver);
	boolean targetPage=	macc.isMyAccountPageExists();
	
	Assert.assertTrue(targetPage);
	}
		catch(Exception e){
			Assert.fail();
			}
		
		logger.info("********finished Tc002_LoginTest**********");
		}

}
