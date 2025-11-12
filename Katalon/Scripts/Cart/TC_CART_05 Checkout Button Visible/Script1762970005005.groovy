// Test Cases/Cart/TC5 Checkout Button Visible
// TC5 Checkout Button Visible

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

KeywordUtil.logInfo("\n===================== 🚀 TC5: CHECKOUT BUTTON VISIBLE =====================")

// Swipe Until Element Is Visible: CHECKOUT button
TestObject checkoutBtn = new TestObject("checkoutBtn")
checkoutBtn.addProperty("content-desc", ConditionType.EQUALS, "test-CHECKOUT")

for (int i = 0; i < 5; i++) {
	boolean visible = Mobile.waitForElementPresent(checkoutBtn, 1, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
	if (visible) {
		break
	}
	CustomKeywords.'mobile.ProductKeywords.swipeUp'()
}

// Wait and verify element is visible
Mobile.waitForElementPresent(checkoutBtn, 10)
Mobile.verifyElementVisible(checkoutBtn, 10)
KeywordUtil.logInfo("✅ Checkout button is visible")

// Click CHECKOUT
Mobile.tap(checkoutBtn, 0)

// Wait for checkout form
TestObject continueBtn = new TestObject("continueBtn")
continueBtn.addProperty("content-desc", ConditionType.EQUALS, "test-CONTINUE")
Mobile.waitForElementPresent(continueBtn, 20)

// Pause to observe
Mobile.delay(5)

KeywordUtil.logInfo("\n===================== ✅ TC5 COMPLETED =====================")
