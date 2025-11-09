import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import constants.MobileObjects as MobileObjects

// Khởi chạy ứng dụng
Mobile.startExistingApplication('com.swaglabsmobileapp', FailureHandling.STOP_ON_FAILURE)

try {
	Mobile.setText(MobileObjects.Login.TxbUsername, 'standard_user', 0)
	Mobile.setText(MobileObjects.Login.TxbPassword, 'wrong_password', 0)
	Mobile.tap(MobileObjects.Login.BtnLogin, 3)
	Mobile.verifyElementVisible(MobileObjects.Login.LblErrorMessage, 3)
	
	def expectedValue = 'Username and password do not match any user in this service.'
	String actualValue = Mobile.getText(MobileObjects.Login.LblErrorMessage, 0)
	Mobile.verifyEqual(actualValue, expectedValue)
} 
catch (Exception e) {
    KeywordUtil.markFailed("Test Case Login thất bại. Lỗi: " + e.getMessage())
    
} 
finally {
    // Đóng ứng dụng
    Mobile.closeApplication()
}