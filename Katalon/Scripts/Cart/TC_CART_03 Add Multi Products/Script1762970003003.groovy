// Test Cases/Cart/TC3 Add Multi Products
// TC3 Add Multi Products

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

KeywordUtil.logInfo("\n===================== 🚀 TC3: ADD MULTI PRODUCTS =====================")

// Swipe Down
CustomKeywords.'mobile.ProductKeywords.swipeDown'()

// Add multiple products
String[] products = ['Sauce Labs Backpack', 'Sauce Labs Bike Light']

for (String product : products) {
	CustomKeywords.'mobile.ProductKeywords.addProductByName'(product)
}

// Get Cart Item Count
String count = CustomKeywords.'mobile.CartCheckoutKeywords.getCartItemCount'()
KeywordUtil.logInfo("🔢 Total cart items: ${count}")

// Verify count should be 3 (1 from TC1 + 2 from TC3)
Mobile.verifyEqual(Integer.parseInt(count), 3)

// Click Cart Icon
CustomKeywords.'mobile.CartCheckoutKeywords.clickCartIcon'()

KeywordUtil.logInfo("\n===================== ✅ TC3 COMPLETED =====================")
