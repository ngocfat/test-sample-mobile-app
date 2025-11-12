import static constants.SortConstants.SORT_PRICE_HIGH
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil

// 1. Sort Products
CustomKeywords.'mobile.ProductKeywords.sortProducts'(SORT_PRICE_HIGH)

// 2. Get Product Names
List<Float> prices = CustomKeywords.'mobile.ProductKeywords.getProductPrices'()

// 3. Verify
if (prices.size() < 2) {
	KeywordUtil.markFailed("Không đủ sản phẩm để so sánh")
} else {
	boolean isSorted = true
	for (int i = 0; i < prices.size() - 1; i++) {
		if (prices.get(i) < prices.get(i + 1)) {
			isSorted = false
			break
		}
	}
	Mobile.verifyEqual(isSorted, true)
}