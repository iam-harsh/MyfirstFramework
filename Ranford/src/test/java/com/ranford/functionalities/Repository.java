package com.ranford.functionalities;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.net.SyslogAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Excel.Excel_Class;
import PageLibrary.AdminPage;
import PageLibrary.BranchesPage;
import PageLibrary.GenericPage;
import PageLibrary.LoginPage;
import TestBase.Base;
import Utility.Screenshots;

public class Repository extends Base {

	private static final boolean True = false;
	WebDriver driver;
	public ExtentReports extentreport;
	public ExtentTest extenttest;

	public void launch_Application() {
		Report_Extent();
		extenttest=extentreport.startTest("Start");
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		Log.info("Chrome browser launched");
		extenttest.log(LogStatus.PASS, "Chrome Browser Launched");
		driver.get(read_testdata("sitUrl"));
		Log.info("URL entered:" + read_testdata("sitUrl"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Log.info("Chrome browser Maximised");
		extenttest.log(LogStatus.PASS, "Browser Maximise Success");
		String strTitle = driver.getTitle();
		if (strTitle.equals("KEXIM BANK")) {
/*			System.out.println("Title displayed correctly as: " + strTitle);*/
			Log.info("Title displayed correctly as: " + strTitle);
			extenttest.log(LogStatus.PASS, "Title displayed correctly as: " + strTitle);
		} else {
			Screenshots.CaptureScreenShot("VerifyTitle");
			extenttest.addScreenCapture(Screenshots.CaptureScreenShot("VerifyTitle"));

/*			System.out.println("Incorrect Title displayed: " + strTitle);*/
			Log.info("Incorrect Title displayed: " + strTitle);
			extenttest.log(LogStatus.PASS, "Incorrect Title displayed: " + strTitle);
			extenttest.log(LogStatus.PASS, "Incorrect Title displayed: " + strTitle);

		}
		/*
		 * Assert.assertEquals(driver.getTitle(), "HDFC");
		 */ }
	public void Report_Extent()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String timestamp= df.format(date);
		extentreport = new ExtentReports("C:\\Users\\IBM_ADMIN\\Downloads\\Ranford\\Ranford\\Reports\\"+"ExtentReportResults"+timestamp+".html",false);
	}
	public void login_Application() {
		LoginPage.username_textfield(driver).sendKeys(read_testdata("username"));
		LoginPage.password_textfield(driver).sendKeys(read_testdata("password"));
		LoginPage.login_button(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		/*
		 * boolean binLogout = AdminPage.logout_button(driver).isDisplayed();
		 * if(binLogout) { Assert.assertTrue(True, "Login is successful"); } else {
		 * Assert.assertTrue(false, "Login is not successful"); }
		 */
	}

	public void clickbranches() {
		AdminPage.branches_button(driver).click();
	}

	/*public void createNewBranch() {
		BranchesPage.newBranch_btn(driver).click();
		BranchesPage.branchName_txt(driver).sendKeys(read_testdata("branchname"));
		BranchesPage.branchAddress1_txt(driver).sendKeys(read_testdata("address"));
		BranchesPage.zipcode_txt(driver).sendKeys(read_testdata("zipcode"));
		
		 * GenericPage.dropDownSelection(driver,
		 * By.id(read_OR("branch_country"))).selectByValue(read_testdata("country"));
		 * GenericPage.dropDownSelection(driver,
		 * By.id(read_OR("branch_state"))).selectByValue(read_testdata("state"));
		 * GenericPage.dropDownSelection(driver,
		 * By.id(read_OR("branch_city"))).selectByValue(read_testdata("city"));
		 
		GenericPage.dropDownSelection(driver, getlocator("branch_country")).selectByValue(read_testdata("country"));
		GenericPage.dropDownSelection(driver, getlocator("branch_state")).selectByValue(read_testdata("state"));
		GenericPage.dropDownSelection(driver, getlocator("branch_city")).selectByValue(read_testdata("city"));
		BranchesPage.cancel_btn(driver).click();
	}*/
	public void createBranch(String bname, String address, String zip, String country, String state, String city)
	{
		BranchesPage.newBranch_btn(driver).click();
		BranchesPage.branchName_txt(driver).sendKeys(bname);
		BranchesPage.branchAddress1_txt(driver).sendKeys(address);
		BranchesPage.zipcode_txt(driver).sendKeys(zip);
		GenericPage.dropDownSelection(driver, getlocator("branch_country_id")).selectByValue(country);
		GenericPage.dropDownSelection(driver, getlocator("branch_state_id")).selectByValue(state);
		GenericPage.dropDownSelection(driver, getlocator("branch_city_id")).selectByValue(city);
		BranchesPage.cancel_btn(driver).click();
/*		String successMessage = "created Sucessfully";		
		String alertMsg = driver.switchTo().alert().getText();
		String strBranchId = alertMsg.replaceAll("\\D+","");
		if(alertMsg.contains(successMessage)) 
		{
			System.out.println("Branch Created Successfully. Branch id: "+strBranchId);
		}
		else
		{
			System.out.println("Branch not created. Displayed: "+alertMsg);
			Validation.CaptureScreenShot("Create New Branch");
			
		}*/
		
	}
	
	
	public Object[][] excelContent(String fileName, String sheetName) throws IOException
	{
		Excel_Class.excelconnection(fileName, sheetName);
		int rc = Excel_Class.rcount();
		int cc = Excel_Class.ccount();
		
		String[][] data=new String[rc-1][cc];
		
		for(int r=1;r<rc;r++)
		{
			for(int c=0;c<cc;c++)
			{
				data[r-1][c] = Excel_Class.readdata(c, r);
			}
		}
		
		
		return data;
		
		
	}

	
	public void logout_Application() {
		AdminPage.branches_button(driver).click();
		driver.close();
		System.out.println("Project Over");
		extentreport.endTest(extenttest);
		extentreport.flush();
	}

}