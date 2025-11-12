// Test Cases/Cart/TC4 Remove First Item
// TC4 Remove First Item

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

KeywordUtil.logInfo("\n===================== 🚀 TC4: REMOVE FIRST ITEM =====================")

// Swipe Until Element Is Visible: First Remove button
TestObject removeBtn = new TestObject("firstRemoveBtn")
removeBtn.addProperty("xpath", ConditionType.EQUALS, "(//*[@content-desc='test-REMOVE'])[1]")

for (int i = 0; i < 5; i++) {
	boolean visible = Mobile.waitForElementPresent(removeBtn, 1, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
	if (visible) {
		break
	}
	CustomKeywords.'mobile.ProductKeywords.swipeUp'()
}

// Pause to observe
Mobile.delay(2)

// Remove First Item From Cart
CustomKeywords.'mobile.CartCheckoutKeywords.removeFirstItemFromCart'()

// Get Cart Item Count after removal
String countAfter = CustomKeywords.'mobile.CartCheckoutKeywords.getCartItemCount'()
KeywordUtil.logInfo("🔢 Cart items after removal: ${countAfter}")

// Verify count should be 2
Mobile.verifyEqual(Integer.parseInt(countAfter), 2)

KeywordUtil.logInfo("\n===================== ✅ TC4 COMPLETED =====================")
