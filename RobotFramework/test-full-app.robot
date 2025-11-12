*** Settings ***
Library           AppiumLibrary
Library           BuiltIn
Suite Setup       Start App And Login
Suite Teardown    Close Application

*** Variables ***
${REMOTE_URL}       http://127.0.0.1:4723
${DEVICE_NAME}      emulator-5554
${PLATFORM_NAME}    Android
${APP_PACKAGE}      com.swaglabsmobileapp
${APP_ACTIVITY}     com.swaglabsmobileapp.MainActivity

${VALID_USER}       standard_user
${VALID_PASS}       secret_sauce
${SORT_PRICE_LOW}   Price (low to high)

*** Keywords ***
Start Application
    Open Application    ${REMOTE_URL}
    ...    platformName=${PLATFORM_NAME}
    ...    deviceName=${DEVICE_NAME}
    ...    appPackage=${APP_PACKAGE}
    ...    appActivity=${APP_ACTIVITY}
    ...    automationName=UiAutomator2

Start App And Login
    Log To Console    \n===================== 🟢 START APP & LOGIN =====================
    Start Application
    Wait Until Element Is Visible    accessibility_id=test-Username    15s
    Input Text    accessibility_id=test-Username    ${VALID_USER}
    Input Text    accessibility_id=test-Password    ${VALID_PASS}
    Click Element    accessibility_id=test-LOGIN
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    15s
    Log To Console    ✅ Logged in successfully and on Products screen

Swipe Up
    Swipe    start_x=540    start_y=1700    end_x=540    end_y=400
    Sleep    1s

Swipe Down
    Swipe    start_x=540    start_y=400    end_x=540    end_y=1700
    Sleep    1s

Swipe Until Element Is Visible
    [Arguments]    ${locator}    ${max_swipes}=6
    FOR    ${i}    IN RANGE    ${max_swipes}
        ${visible}=    Run Keyword And Return Status    Element Should Be Visible    ${locator}    1s
        Run Keyword If    ${visible}    Exit For Loop
        Swipe Up
    END

Add Product By Name
    [Arguments]    ${name}
    ${xpath}=    Set Variable    //*[@content-desc="test-Item" and .//*[contains(@text,"${name}")]]//*[@content-desc="test-ADD TO CART"]
    Swipe Until Element Is Visible    ${xpath}
    Click Element    ${xpath}
    Sleep    1s
    Log To Console    🟢 Added product: ${name}

Remove Product By Name
    [Arguments]    ${name}
    ${xpath}=    Set Variable    //*[@content-desc="test-Item" and .//*[contains(@text,"${name}")]]//*[@content-desc="test-REMOVE"]
    Swipe Until Element Is Visible    ${xpath}
    Click Element    ${xpath}
    Sleep    1s
    Log To Console    🔴 Removed product: ${name}

Click Cart Icon
    Click Element    accessibility_id=test-Cart
    Wait Until Element Is Visible    xpath=//*[@text="YOUR CART"]    10s
    Log To Console    🟢 Opened Cart

Remove First Item From Cart
    Wait Until Element Is Visible    xpath=(//*[@content-desc="test-REMOVE"])[1]    10s
    Click Element    xpath=(//*[@content-desc="test-REMOVE"])[1]
    Sleep    1s
    Log To Console    🔴 Removed first item from Cart

Select Sort Option
    [Arguments]    ${option_text}
    Click Element    accessibility_id=test-Modal Selector Button
    Wait Until Element Is Visible    xpath=//*[@text="${option_text}"]    5s
    Click Element    xpath=//*[@text="${option_text}"]
    Sleep    1s
    Log To Console    🟢 Selected sort option: ${option_text}

Open Product Details
    [Arguments]    ${product_name}
    ${xpath}=    Set Variable    xpath=//*[contains(@text,"${product_name}")]
    Swipe Until Element Is Visible    ${xpath}
    Click Element    ${xpath}
    Wait Until Element Is Visible    xpath=//*[@text="${product_name}"]    5s
    Log To Console    🟢 Opened product details: ${product_name}

Fill Checkout Information
    [Arguments]    ${first}=John    ${last}=Doe    ${postal}=12345
    Wait Until Element Is Visible    accessibility_id=test-First Name    5s
    Input Text    accessibility_id=test-First Name    ${first}
    Input Text    accessibility_id=test-Last Name     ${last}
    Input Text    accessibility_id=test-Zip/Postal Code    ${postal}
    Click Element    accessibility_id=test-CONTINUE
    Log To Console    🟢 Entered checkout information

Finish Checkout
    Wait Until Element Is Visible    xpath=//*[@text="CHECKOUT: OVERVIEW"]    10s
    Click Element    accessibility_id=test-FINISH
    Wait Until Element Is Visible    xpath=//*[@text="CHECKOUT: COMPLETE!"]    10s
    Click Element    accessibility_id=test-BACK HOME
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    10s
    Log To Console    ✅ Checkout completed and returned to Home

Logout
    Click Element    accessibility_id=test-Menu
    Sleep    500ms
    Click Element    xpath=//*[@text="LOGOUT"]
    Wait Until Element Is Visible    accessibility_id=test-LOGIN    10s
    Log To Console    ✅ Logged out successfully

*** Test Cases ***
TC_E2E_01 | Full Shopping Flow
    [Documentation]    End-to-End flow: Login → Scroll → Add 2 items → Remove 1 from Home → Sort → Detail → Add → Cart → Remove → Checkout → Home → Logout

    Log To Console    \n===================== 🚀 START E2E SHOPPING FLOW =====================

    Log To Console    \n➡️ Step 1: Scroll Products to verify list loads
    Swipe Up
    Swipe Down

    Log To Console    \n➡️ Step 2: Add 2 Products and Remove 1 on Home
    Add Product By Name    Sauce Labs Backpack
    Add Product By Name    Sauce Labs Bolt T-Shirt
    Swipe Down
    Remove Product By Name    Sauce Labs Backpack

    Log To Console    \n➡️ Step 3: Sort Products by Price Low to High
    Select Sort Option    ${SORT_PRICE_LOW}
    Swipe Up
    Swipe Down

    Log To Console    \n➡️ Step 4: Open Product Details and Add to Cart
    Open Product Details    Sauce Labs Fleece Jacket
    Swipe Up
    Click Element    accessibility_id=test-ADD TO CART
    Log To Console    🟢 Added "Sauce Labs Fleece Jacket" to Cart

    Log To Console    \n➡️ Step 5: Open Cart and Remove First Item
    Click Cart Icon
    Remove First Item From Cart

    Log To Console    \n➡️ Step 6: Checkout Process
    Click Element    accessibility_id=test-CHECKOUT
    Fill Checkout Information
    Swipe Up
    Finish Checkout

    Swipe Up
    Swipe Down
    Log To Console    \n➡️ Step 7: Logout
    Logout

    Log To Console    \n===================== ✅ E2E TEST COMPLETED SUCCESSFULLY =====================
