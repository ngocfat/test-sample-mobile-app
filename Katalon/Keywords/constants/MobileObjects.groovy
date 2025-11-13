// Keyword/constants/MobileObjects.groovy
package constants

import com.kms.katalon.core.testobject.TestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

public class MobileObjects {

	public static class Login {
		// Giữ nguyên phần của bạn
		private static final String PATH = 'Object Repository/LoginPage'

		public static final TestObject TxbUsername = findTestObject("${PATH}/txbUsername")
		public static final TestObject TxbPassword = findTestObject("${PATH}/txbPassword")
		public static final TestObject BtnLogin = findTestObject("${PATH}/btnLogin")
		public static final TestObject BtnStandardUser = findTestObject("${PATH}/btnStandardUser")
		public static final TestObject BtnLockedOutUser = findTestObject("${PATH}/btnLockedOutUser")
		public static final TestObject BtnProblemUser = findTestObject("${PATH}/btnProblemUser")
		public static final TestObject LblErrorMessage = findTestObject("${PATH}/lblErrorMessage")
	}

	public static class Products {
		private static final String PATH = 'Object Repository/ProductsPage'

		public static final TestObject LblProductsTitle = findTestObject("${PATH}/lblProductsTitle")
		public static final TestObject BtnSort = findTestObject("${PATH}/btnSort")
		public static final TestObject ListItems = findTestObject("${PATH}/listItems")
		public static final TestObject LblItemNames = findTestObject("${PATH}/lblItemNames")
		public static final TestObject LblItemPrices = findTestObject("${PATH}/lblItemPrices")
	}
}