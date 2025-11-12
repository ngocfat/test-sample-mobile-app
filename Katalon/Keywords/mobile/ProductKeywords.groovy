package mobile

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import static constants.MobileObjects.Products.*

import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.*

public class ProductKeywords {

	@Keyword
	def swipeUp() {
		Mobile.swipe(540, 1700, 540, 400)
		Mobile.delay(2)
	}

	@Keyword
	def swipeDown() {
		Mobile.swipe(540, 400, 540, 1700)
		Mobile.delay(2)
	}

	@Keyword
	def swipeUntilElementIsVisible(TestObject to, int maxSwipes = 5) {
		for (int i = 0; i < maxSwipes; i++) {
			boolean isVisible = Mobile.waitForElementPresent(to, 1)
			if (isVisible) {
				return true
			}
			swipeUp()
		}
		Mobile.verifyElementVisible(to, 1)
	}

	@Keyword
	def sortProducts(String sortOption) {
		Mobile.tap(BtnSort, 0)

		TestObject sortOptionTO = new TestObject("dynamicSortOption")
		sortOptionTO.addProperty("xpath", ConditionType.EQUALS, "//*[@text='" + sortOption + "']")

		Mobile.waitForElementPresent(sortOptionTO, 5)
		Mobile.tap(sortOptionTO, 0)
		Mobile.delay(5)
	}

	@Keyword
	def List<String> getProductNames() {
		swipeUp()
		Mobile.delay(10)
		swipeDown()

		def items = MobileDriverFactory.getDriver().findElements(By.xpath("//*[@content-desc='test-Item']"))
		List<String> names = []

		if (items.isEmpty()) {
			return names
		}

		for (int i = 1; i <= items.size(); i++) {
			String nameXPath = "(//*[@content-desc='test-Item'])[${i}]//*[@content-desc='test-Item title']"
			TestObject nameTO = new TestObject("itemName_${i}")
			nameTO.addProperty("xpath", ConditionType.EQUALS, nameXPath)

			if (Mobile.waitForElementPresent(nameTO, 1)) {
				names.add(Mobile.getText(nameTO, 0))
			}
		}
		return names
	}

	@Keyword
	def List<Float> getProductPrices() {
		swipeUp()
		Mobile.delay(10)
		swipeDown()

		def items = MobileDriverFactory.getDriver().findElements(By.xpath("//*[@content-desc='test-Item']"))
		List<Float> prices = []

		if (items.isEmpty()) {
			return prices
		}

		for (int i = 1; i <= items.size(); i++) {
			String priceXPath = "(//*[@content-desc='test-Item'])[${i}]//*[@content-desc='test-Price']"
			TestObject priceTO = new TestObject("itemPrice_${i}")
			priceTO.addProperty("xpath", ConditionType.EQUALS, priceXPath)

			String priceText = "0"
			if (Mobile.waitForElementPresent(priceTO, 1)) {
				priceText = Mobile.getText(priceTO, 0)
			}

			String cleanedText = priceText.replace('$', '')
			prices.add(Float.parseFloat(cleanedText))
		}
		return prices
	}

	@Keyword
	def addProductByName(String name) {
		TestObject addBtnTO = new TestObject("dynamicAddBtn")
		String xpath = "//*[@content-desc='test-Item' and .//*[contains(@text,'${name}')]]//*[@content-desc='test-ADD TO CART']"
		addBtnTO.addProperty("xpath", ConditionType.EQUALS, xpath)

		swipeUntilElementIsVisible(addBtnTO)

		Mobile.tap(addBtnTO, 0)

		com.kms.katalon.core.util.KeywordUtil.logInfo("🟢 Added product: ${name}")
	}

	@Keyword
	def removeProductByName(String name) {
		TestObject xpathTO = new TestObject("dynamicRemoveBtn")
		String xpath = "//*[@content-desc='test-Item' and .//*[contains(@text,'" + name + "')]]//*[@content-desc='test-REMOVE']"
		xpathTO.addProperty("xpath", ConditionType.EQUALS, xpath)

		swipeUntilElementIsVisible(xpathTO)
		Mobile.tap(xpathTO, 0)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🔴 Removed product: " + name)
	}

	@Keyword
	def selectSortOption(String optionText) {
		// Sử dụng Test Object cho nút Sort đã định nghĩa (BtnSort)
		Mobile.tap(BtnSort, 0)

		TestObject sortOptionTO = new TestObject("dynamicSortOption")
		sortOptionTO.addProperty("xpath", ConditionType.EQUALS, "//*[@text='" + optionText + "']")

		Mobile.waitForElementPresent(sortOptionTO, 5)
		Mobile.tap(sortOptionTO, 0)
		Mobile.delay(1)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🟢 Selected sort option: " + optionText)
	}

	@Keyword
	def openProductDetails(String productName) {
		TestObject xpathTO = new TestObject("dynamicProductName")
		String xpath = "//*[contains(@text,'" + productName + "')]"
		xpathTO.addProperty("xpath", ConditionType.EQUALS, xpath)

		swipeUntilElementIsVisible(xpathTO)
		Mobile.tap(xpathTO, 0)

		TestObject titleTO = new TestObject("productDetailsTitle")
		titleTO.addProperty("xpath", ConditionType.EQUALS, "//*[@text='" + productName + "']")

		Mobile.waitForElementPresent(titleTO, 5)
		com.kms.katalon.core.util.KeywordUtil.logInfo("🟢 Opened product details: " + productName)
	}
}