import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import static constants.UserConstants.*

class CommonTestListener {

    @BeforeTestSuite
    def setupTestSuite(TestSuiteContext testSuiteContext) {
        String suiteName = testSuiteContext.getTestSuiteId()
        
        // Skip login suite itself
        if (!suiteName.toLowerCase().contains("login")) {
            try {
                Mobile.startExistingApplication('com.swaglabsmobileapp')
            } catch (Exception e) {
                println("App not running, starting fresh...")
                Mobile.startApplication('path/to/your/app.apk', false)
            }
            
            CustomKeywords.'mobile.LoginKeywords.loginSuccessful'(VALID_USER, VALID_PASS)
        }
    }

    @AfterTestSuite
    def teardownTestSuite(TestSuiteContext testSuiteContext) {
		String suiteName = testSuiteContext.getTestSuiteId()
		
		// Skip login suite itself
		if (!suiteName.toLowerCase().contains("login")) {			
			Mobile.closeApplication()
		}
    }

    @BeforeTestCase
    def setupTestCase(TestCaseContext testCaseContext) {
		String suiteName = testCaseContext.getTestCaseId()
        if (!suiteName.toLowerCase().contains("login")) {
            CustomKeywords.'mobile.LoginKeywords.goToHomeScreen'()
        }
    }
}
