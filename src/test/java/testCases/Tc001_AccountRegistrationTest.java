package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class Tc001_AccountRegistrationTest extends BaseClass
{
	@Test(groups={"Sanity","Master"})
	public void verify_account_registration(){
		
		logger.info("****** Starting Tc001_AccountRegistrationTest ******");
		
	try {
		HomePage hp= new HomePage(driver);
		
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link");
		
		hp.clickRegister();
		logger.info("Clicked on Registert Link");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing Custommer details..");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+ "@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password = randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message");
		String confMsg = regpage.msgConfirm();
		
		Assert.assertEquals(confMsg, "Your Account Has Been Created!");
	}catch(Exception e)
	{
		logger.error("Test failed...");
		logger.debug("Debug logs...");
		Assert.fail();
		}
	}

}
