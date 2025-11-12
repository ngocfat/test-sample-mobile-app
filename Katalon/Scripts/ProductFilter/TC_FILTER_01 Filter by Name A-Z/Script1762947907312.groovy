import static constants.SortConstants.SORT_NAME_AZ
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil

// 1. Sort Products
CustomKeywords.'mobile.ProductKeywords.sortProducts'(SORT_NAME_AZ)

// 2. Get Product Names
List<String> names = CustomKeywords.'mobile.ProductKeywords.getProductNames'()

// 3. Verify
if (names.size() < 2) {
	KeywordUtil.markFailed("Không đủ sản phẩm để so sánh")
} else {
	boolean isSorted = true
	for (int i = 0; i < names.size() - 1; i++) {
		if (names.get(i).compareTo(names.get(i+1)) > 0) {
			isSorted = false
			break
		}
	}
	Mobile.verifyEqual(isSorted, true)
}