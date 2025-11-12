// Keywords/mobile/CartCheckoutKeywords.groovy
package mobile

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

public class CartCheckoutKeywords {

	@Keyword
	def swipeUp() {
		Mobile.swipe(540, 1200, 540, 400)
		Mobile.delay(2)
	}

	@Keyword
	def swipeDown() {
		Mobile.swipe(540, 400, 540, 1000)
		Mobile.delay(2)
	}

	@Keyword
	def clickCartIcon() {
		TestObject cartIcon = new TestObject("cartIcon")
		cartIcon.addProperty("content-desc", ConditionType.EQUALS, "test-Cart")
		Mobile.tap(cartIcon, 1)

		TestObject cartTitle = new TestObject("cartTitle")
		cartTitle.addProperty("xpath", ConditionType.EQUALS, '//*[@text="YOUR CART"]')
		Mobile.waitForElementPresent(cartTitle, 10)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🟢 Opened Cart")
	}

	@Keyword
	def removeFirstItemFromCart() {
		TestObject removeBtn = new TestObject("firstRemoveBtn")
		removeBtn.addProperty("xpath", ConditionType.EQUALS, "(//*[@content-desc='test-REMOVE'])[1]")

		Mobile.waitForElementPresent(removeBtn, 10)
		Mobile.tap(removeBtn, 1)
		Mobile.delay(1)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🔴 Removed first item from Cart")
	}

	@Keyword
	def fillCheckoutInformation(String first, String last, String postal) {
		TestObject firstNameField = new TestObject("firstNameField")
		firstNameField.addProperty("content-desc", ConditionType.EQUALS, "test-First Name")
		Mobile.waitForElementPresent(firstNameField, 5)

		Mobile.setText(firstNameField, first, 0)

		TestObject lastNameField = new TestObject("lastNameField")
		lastNameField.addProperty("content-desc", ConditionType.EQUALS, "test-Last Name")
		Mobile.setText(lastNameField, last, 0)

		TestObject postalCodeField = new TestObject("postalCodeField")
		postalCodeField.addProperty("content-desc", ConditionType.EQUALS, "test-Zip/Postal Code")
		Mobile.setText(postalCodeField, postal, 0)

		TestObject continueBtn = new TestObject("continueBtn")
		continueBtn.addProperty("content-desc", ConditionType.EQUALS, "test-CONTINUE")
		Mobile.tap(continueBtn, 1)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🟢 Entered checkout information")
	}

	@Keyword
	def finishCheckout() {
		TestObject overviewTitle = new TestObject("overviewTitle")
		overviewTitle.addProperty("xpath", ConditionType.EQUALS, '//*[@text="CHECKOUT: OVERVIEW"]')
		Mobile.waitForElementPresent(overviewTitle, 10)
		
		swipeUp()

		TestObject finishBtn = new TestObject("finishBtn")
		finishBtn.addProperty("content-desc", ConditionType.EQUALS, "test-FINISH")
		Mobile.tap(finishBtn, 5)

		TestObject completeTitle = new TestObject("completeTitle")
		completeTitle.addProperty("xpath", ConditionType.EQUALS, '//*[@text="CHECKOUT: COMPLETE!"]')
		Mobile.waitForElementPresent(completeTitle, 10)

		TestObject backHomeBtn = new TestObject("backHomeBtn")
		backHomeBtn.addProperty("content-desc", ConditionType.EQUALS, "test-BACK HOME")
		Mobile.tap(backHomeBtn, 1)

		TestObject productsTitle = new TestObject("productsTitle")
		productsTitle.addProperty("content-desc", ConditionType.EQUALS, "test-PRODUCTS")
		Mobile.waitForElementPresent(productsTitle, 10)
		com.kms.katalon.core.util.KeywordUtil.logInfo("✅ Checkout completed and returned to Home")
	}

	@Keyword
	def logout() {
		TestObject menuBtn = new TestObject("menuBtn")
		menuBtn.addProperty("content-desc", ConditionType.EQUALS, "test-Menu")
		Mobile.tap(menuBtn, 1)
		Mobile.delay(0.5)

		TestObject logoutBtn = new TestObject("logoutBtn")
		logoutBtn.addProperty("xpath", ConditionType.EQUALS, '//*[@text="LOGOUT"]')
		Mobile.tap(logoutBtn, 1)

		TestObject loginBtn = new TestObject("loginBtn")
		loginBtn.addProperty("content-desc", ConditionType.EQUALS, "test-LOGIN")
		Mobile.waitForElementPresent(loginBtn, 10)
		com.kms.katalon.core.util.KeywordUtil.logInfo("✅ Logged out successfully")
	}

	@Keyword
	def getCartItemCount() {
		TestObject cartBadge = new TestObject("cartBadge")
		cartBadge.addProperty("xpath", ConditionType.EQUALS, '//*[@content-desc="test-Cart"]//android.widget.TextView')
		
		boolean exists = Mobile.waitForElementPresent(cartBadge, 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
		
		if (exists) {
			String count = Mobile.getText(cartBadge, 0)
			com.kms.katalon.core.util.KeywordUtil.logInfo("🔢 Cart item count: ${count}")
			return count
		} else {
			com.kms.katalon.core.util.KeywordUtil.logInfo("🔢 Cart item count: 0")
			return "0"
		}
	}

	@Keyword
	def continueShopping() {
		// Check if we're already on cart page
		TestObject continueBtn = new TestObject("continueShoppingBtn")
		continueBtn.addProperty("content-desc", ConditionType.EQUALS, "test-CONTINUE SHOPPING")
		
		boolean onCart = Mobile.waitForElementPresent(continueBtn, 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
		
		if (!onCart) {
			clickCartIcon()
		}
		
		Mobile.waitForElementPresent(continueBtn, 10)
		Mobile.tap(continueBtn, 1)
		
		TestObject productsTitle = new TestObject("productsTitle")
		productsTitle.addProperty("content-desc", ConditionType.EQUALS, "test-PRODUCTS")
		Mobile.waitForElementPresent(productsTitle, 10)
		Mobile.delay(2)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🏠 Returned to products page")
	}
}