import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import constants.MobileObjects as MobileObjects // Giả sử bạn đã tạo file MobileObjects

def expectedUsername = 'standard_user'
def password = 'secret_sauce'

// Khởi chạy ứng dụng
Mobile.startExistingApplication('com.swaglabsmobileapp', FailureHandling.STOP_ON_FAILURE)

try {
    Mobile.tap(MobileObjects.Login.BtnStandardUser, 3)
	Mobile.tap(MobileObjects.Login.BtnLogin, 3)
	Mobile.verifyElementVisible(MobileObjects.Products.LblProductsTitle, 3)
} 
catch (Exception e) {
    KeywordUtil.markFailed("Test Case Login thất bại. Lỗi: " + e.getMessage())
    
} 
finally {
    // Đóng ứng dụng
    Mobile.closeApplication()
}