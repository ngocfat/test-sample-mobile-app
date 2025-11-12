package mobile

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static constants.MobileObjects.Login.*
import static constants.MobileObjects.Products.*

import internal.GlobalVariable

public class LoginKeywords {
	@Keyword
	def loginSuccessful(String username, String password) {
		Mobile.waitForElementPresent(TxbUsername, 15)
		Mobile.setText(TxbUsername, username, 0)
		Mobile.setText(TxbPassword, password, 0)
		Mobile.tap(BtnLogin, 0)
		Mobile.waitForElementPresent(LblProductsTitle, 15)
	}

	@Keyword
	def goToHomeScreen() {
		Mobile.waitForElementPresent(LblProductsTitle, 10)
	}
}
