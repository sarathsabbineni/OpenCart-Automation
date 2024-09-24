package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class Tc003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")
	public void verifyLoginDDT(String email, String pwd, String  exp) {
	logger.info("********starting Tc003_LoginDDT ******* ");
		
	try {
		//Homepage
		HomePage hp= new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp= new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//My Account Page
		MyAccountPage macc= new MyAccountPage(driver);
	boolean targetPage=	macc.isMyAccountPageExists();
	
	if(exp.equalsIgnoreCase("Valid"))
	{
		if(targetPage==true) {
			macc.clickLogout();
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
	}
	
	if(exp.equalsIgnoreCase("Invalid"))
	{
		if(targetPage==true) {
			macc.clickLogout();
			Assert.assertTrue(false);
		}else {
			Assert.assertTrue(true);
		}
	}
	}catch(Exception e) {
		Assert.fail();
	}
	
	
		
		
		logger.info("********finished Tc003_LoginDDT**********");
		}

	}


