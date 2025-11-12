// Test Cases/Cart/TC1 Add one Product
// TC1 Add one Product

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

KeywordUtil.logInfo("\n===================== 🚀 TC1: ADD ONE PRODUCT =====================")

// Swipe Up
CustomKeywords.'mobile.ProductKeywords.swipeUp'()

// Add Product By Name: Sauce Labs Bolt T-Shirt
CustomKeywords.'mobile.ProductKeywords.addProductByName'('Sauce Labs Bolt T-Shirt')

// Click Cart Icon
CustomKeywords.'mobile.CartCheckoutKeywords.clickCartIcon'()

KeywordUtil.logInfo("\n===================== ✅ TC1 COMPLETED =====================")
