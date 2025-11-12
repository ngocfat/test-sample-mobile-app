// Test Cases/Cart/TC2 Verify Cart Header and Continue Shopping
// TC2 Verify Cart Header & Continue Shopping

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

KeywordUtil.logInfo("\n===================== 🚀 TC2: VERIFY CART HEADER & CONTINUE SHOPPING =====================")

// Wait Until Element Is Visible: YOUR CART
TestObject cartHeader = new TestObject("cartHeader")
cartHeader.addProperty("xpath", ConditionType.EQUALS, '//*[@text="YOUR CART"]')
Mobile.waitForElementPresent(cartHeader, 10)

// Get Text and Verify
String headerText = Mobile.getText(cartHeader, 0)
Mobile.verifyEqual(headerText, "YOUR CART")
KeywordUtil.logInfo("✅ Cart header verified: ${headerText}")

// Continue Shopping
CustomKeywords.'mobile.CartCheckoutKeywords.continueShopping'()

KeywordUtil.logInfo("\n===================== ✅ TC2 COMPLETED =====================")
