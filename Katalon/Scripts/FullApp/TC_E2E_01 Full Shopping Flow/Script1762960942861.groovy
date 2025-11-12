// Test Cases/E2E/TC_E2E_01_Full_Shopping_Flow

import static constants.SortConstants.SORT_PRICE_LOW
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

KeywordUtil.logInfo("\n===================== 🚀 START E2E SHOPPING FLOW =====================")

// ➡️ Step 1: Scroll Products to verify list loads
KeywordUtil.logInfo("\n➡️ Step 1: Scroll Products to verify list loads")
CustomKeywords.'mobile.ProductKeywords.swipeUp'()
CustomKeywords.'mobile.ProductKeywords.swipeDown'()

// ➡️ Step 2: Add 2 Products and Remove 1 on Home
KeywordUtil.logInfo("\n➡️ Step 2: Add 2 Products and Remove 1 on Home")
CustomKeywords.'mobile.ProductKeywords.addProductByName'('Sauce Labs Backpack')
CustomKeywords.'mobile.ProductKeywords.addProductByName'('Sauce Labs Bolt T-Shirt')
CustomKeywords.'mobile.ProductKeywords.swipeDown'()
CustomKeywords.'mobile.ProductKeywords.removeProductByName'('Sauce Labs Backpack')

// ➡️ Step 3: Sort Products by Price Low to High
KeywordUtil.logInfo("\n➡️ Step 3: Sort Products by Price Low to High")
CustomKeywords.'mobile.ProductKeywords.selectSortOption'(SORT_PRICE_LOW)
CustomKeywords.'mobile.ProductKeywords.swipeUp'()
CustomKeywords.'mobile.ProductKeywords.swipeDown'()

// ➡️ Step 4: Open Product Details and Add to Cart
KeywordUtil.logInfo("\n➡️ Step 4: Open Product Details and Add to Cart")
CustomKeywords.'mobile.ProductKeywords.openProductDetails'('Sauce Labs Fleece Jacket')
CustomKeywords.'mobile.ProductKeywords.swipeUp'()

TestObject addToCartBtn = new TestObject("addToCartBtn")
addToCartBtn.addProperty("xpath", ConditionType.EQUALS, '//*[@content-desc="test-ADD TO CART"]')
Mobile.tap(addToCartBtn, 0)
KeywordUtil.logInfo("🟢 Added 'Sauce Labs Fleece Jacket' to Cart")

// ➡️ Step 5: Open Cart and Remove First Item
KeywordUtil.logInfo("\n➡️ Step 5: Open Cart and Remove First Item")
CustomKeywords.'mobile.CartCheckoutKeywords.clickCartIcon'()
CustomKeywords.'mobile.CartCheckoutKeywords.removeFirstItemFromCart'()

// ➡️ Step 6: Checkout Process
KeywordUtil.logInfo("\n➡️ Step 6: Checkout Process")

TestObject checkoutBtn = new TestObject("checkoutBtn")
checkoutBtn.addProperty("xpath", ConditionType.EQUALS, '//*[@content-desc="test-CHECKOUT"]')
Mobile.tap(checkoutBtn, 0)

// Sử dụng giá trị mặc định cho John Doe
CustomKeywords.'mobile.CartCheckoutKeywords.fillCheckoutInformation'('John', 'Doe', '12345') 
CustomKeywords.'mobile.CartCheckoutKeywords.finishCheckout'()

// ➡️ Step 7: Logout
KeywordUtil.logInfo("\n➡️ Step 7: Logout")
CustomKeywords.'mobile.CartCheckoutKeywords.logout'()

KeywordUtil.logInfo("\n===================== ✅ E2E TEST COMPLETED SUCCESSFULLY =====================")