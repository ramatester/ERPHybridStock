package driverFactory;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtils;

public class DriverScript 
{
	
	WebDriver driver;
	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TCSheet="MasterTestCases";
	
	public void startTest()throws Throwable 
	{
		String Module_Status="";
		String Module_new="";
		ExcelFileUtils xl=new ExcelFileUtils(inputpath);
		
		for(int i=1;i<=xl.rowcount(TCSheet);i++)
		{
			if(xl.getcelldata(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
			  String TCModule= xl.getcelldata(TCSheet, i, 1);
			 report=new ExtentReports("./target/ExtentReports/"+TCModule+FunctionLibrary.generateDate()+".html");
			 logger=report.startTest(TCModule);
			 logger.assignAuthor("Rama");
			  for(int j=1;j<=xl.rowcount(TCModule);j++)
			  {
				String Discription= xl.getcelldata(TCModule, j, 0);
			   String ObjectType=xl.getcelldata(TCModule, j, 1);
			   String LocatorType= xl.getcelldata(TCModule, j,2);
			   String LocatorValue= xl.getcelldata(TCModule, j, 3);
			   String TestData=xl.getcelldata(TCModule, j, 4);
			   try
			   {
				   if(ObjectType.equalsIgnoreCase("startBrowser"))
				   {
					   FunctionLibrary.startBrowser();
					   logger.log(LogStatus.INFO,Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("openUrl"))
				   {
					   FunctionLibrary.openUrl();
					   logger.log(LogStatus.INFO,Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("waitForElement"))
				   {
					   FunctionLibrary.waitForElement(LocatorType, LocatorValue, TestData);
					   logger.log(LogStatus.INFO, Discription);
					  
				   }
				   if(ObjectType.equalsIgnoreCase("typeAction"))
				   {
					   FunctionLibrary.typeAction(LocatorType, LocatorValue, TestData);
					   logger.log(LogStatus.INFO,Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("clickAction"))
				   {
					   FunctionLibrary.clickAction(LocatorType, LocatorValue);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("validateTitle"))
				   {
					   FunctionLibrary.validateTitle(TestData);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("closeBrowser"))
				   {
					   FunctionLibrary.closeBrowser();
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("droupDownAction"))
				   {
					   FunctionLibrary.droupDownAction(LocatorType, LocatorValue, TestData);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("captureStock"))
				   {
					   FunctionLibrary.captureStock(LocatorType, LocatorValue);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("stockTable"))
				   {
					   FunctionLibrary.stockTable();
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("captureSup"))
				   {
					   FunctionLibrary.captureSup(LocatorType, LocatorValue);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("supplierTable"))
				   {
					   FunctionLibrary.supplierTable();
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase("captureCus"))
				   {
					   FunctionLibrary.captureCus(LocatorType, LocatorValue);
					   logger.log(LogStatus.INFO, Discription);
				   }
				   if(ObjectType.equalsIgnoreCase(TestData))
				   {
					   FunctionLibrary.customerTable();
					   logger.log(LogStatus.INFO, Discription);
				   }
				   xl.setCellData(TCModule, j, 5,"Pass",outputpath);
				   logger.log(LogStatus.PASS,Discription);
				   Module_Status="True";
				   
				 } catch (AssertionError t) 
			   {
				
			      System.out.println(t.getMessage());
			      xl.setCellData(TCModule, j, 5,"Fail",outputpath);
			      logger.log(LogStatus.FAIL, Discription);
			      Module_new="False";
			      File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			      FileUtils.copyFile(screen,new File("./target/screenshot"+Discription+FunctionLibrary.generateDate()+".png"));
			   }
			   if(Module_Status.equalsIgnoreCase("True"))
			   {
				   xl.setCellData(TCSheet, i, 3,"Pass", outputpath);
			   }
			    if(Module_new.equalsIgnoreCase("False"))
			    {
			    	xl.setCellData(TCSheet, i, 3,"Fail",outputpath);
			    }
			    report.endTest(logger);
			    report.flush();
			  
			  }
			}else
				xl.setCellData(TCSheet, i, 3,"Blocked",outputpath);
		}
		
	}
}


