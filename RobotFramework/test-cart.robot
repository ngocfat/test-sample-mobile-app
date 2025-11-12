*** Settings ***
Library           AppiumLibrary
Library           BuiltIn
Suite Setup       Start App And Login
Suite Teardown    Close Application
Test Setup        Go To Home Screen

*** Variables ***
${REMOTE_URL}       http://127.0.0.1:4723
${DEVICE_NAME}      emulator-5554
${PLATFORM_NAME}    Android
${APP}              T:/Testing/Seminar-Demo/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
${APP_PACKAGE}      com.swaglabsmobileapp
${APP_ACTIVITY}     com.swaglabsmobileapp.MainActivity

${VALID_USER}       standard_user
${VALID_PASS}       secret_sauce

@{PRODUCTS}         Sauce Labs Backpack    Sauce Labs Bike Light
@{PRODUCT_A}        Sauce Labs Bolt T-Shirt


*** Keywords ***
Start App And Login
    Open Application    ${REMOTE_URL} 
    ...    platformName=${PLATFORM_NAME} 
    ...    deviceName=${DEVICE_NAME} 
    ...    app=${APP} 
    ...    appPackage=${APP_PACKAGE} 
    ...    appActivity=${APP_ACTIVITY} 
    ...    automationName=UiAutomator2
    ...    newCommandTimeout=120
    Login Successful

Login Successful
    Wait Until Element Is Visible    accessibility_id=test-Username    20
    Input Text                       accessibility_id=test-Username    ${VALID_USER}
    Input Text                       accessibility_id=test-Password    ${VALID_PASS}
    Click Element                    accessibility_id=test-LOGIN
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    20
    Log To Console    ✅ Login successful!

Go To Home Screen
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    10

Swipe Up
    Swipe    start_x=540    start_y=1700    end_x=540    end_y=400
    Sleep    1s
    
Swipe Down
    Swipe    start_x=540    start_y=400    end_x=540    end_y=1700
    Sleep    1s

Swipe Until Element Is Visible
    [Arguments]    ${locator}    ${max_swipes}=5
    FOR    ${i}    IN RANGE    ${max_swipes}
        ${visible}=    Run Keyword And Return Status    Element Should Be Visible    ${locator}    1s
        Run Keyword If    ${visible}    Exit For Loop
        Swipe Up
    END


Add Product By Name
    [Arguments]    ${name}
    ${xpath}=    Set Variable    //*[@content-desc="test-Item" and .//*[contains(@text,"${name}")]]//*[@content-desc="test-ADD TO CART"]
    Swipe Until Element Is Visible    ${xpath}    10
    Wait Until Element Is Visible      ${xpath}    15
    Click Element                      ${xpath}
    Sleep    1

Click Cart Icon
    Click Element    accessibility_id=test-Cart
    Wait Until Element Is Visible    xpath=//*[@text="YOUR CART"]    10

Get Cart Item Count
    ${exists}=    Run Keyword And Return Status    Element Should Be Visible    xpath=//*[@content-desc="test-Cart"]//android.widget.TextView    2
    IF    ${exists}
        ${count}=    Get Text    xpath=//*[@content-desc="test-Cart"]//android.widget.TextView
    ELSE
        ${count}=    Set Variable    0
    END
    [Return]    ${count}

Remove First Item From Cart
    Wait Until Element Is Visible    xpath=(//*[@content-desc="test-REMOVE"])[1]    10
    Click Element    xpath=(//*[@content-desc="test-REMOVE"])[1]
    Sleep    1

Continue Shopping
    [Documentation]    Return Home from cart
    ${on_cart}=    Run Keyword And Return Status    Element Should Be Visible    accessibility_id=test-CONTINUE SHOPPING
    IF    not ${on_cart}
        Click Cart Icon
    END

    Wait Until Element Is Visible    accessibility_id=test-CONTINUE SHOPPING    10
    Click Element                    accessibility_id=test-CONTINUE SHOPPING

    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    10
    Sleep    5



*** Test Cases ***

TC1 Add one Product
    Swipe Up
    Add Product By Name    @{PRODUCT_A}
    Click Cart Icon

TC2 Verify Cart Header & Continue Shopping
    [Setup]    No Operation
    Wait Until Element Is Visible    xpath=//*[@text="YOUR CART"]    10
    ${header}=    Get Text    xpath=//*[@text="YOUR CART"]
    Should Be Equal    ${header}    YOUR CART
    Continue Shopping

TC3 Add Multi Products
    Swipe Down
    FOR    ${product}    IN    @{PRODUCTS}
        Add Product By Name    ${product}
    END
    ${count}=    Get Cart Item Count
    Should Be Equal As Integers    ${count}    3
    Click Cart Icon


TC4 Remove First Item
    [Setup]    No Operation
    Swipe Until Element Is Visible    xpath=(//*[@content-desc="test-REMOVE"])[1]    5
    Sleep    2s    # Pause để quan sát
    Remove First Item From Cart
    ${count_after}=    Get Cart Item Count
    Should Be Equal As Integers    ${count_after}    2


TC5 Checkout Button Visible
    [Setup]    No Operation
    Swipe Until Element Is Visible    accessibility_id=test-CHECKOUT    5
    Wait Until Element Is Visible     accessibility_id=test-CHECKOUT    10
    Element Should Be Visible          accessibility_id=test-CHECKOUT
    Click Element    accessibility_id=test-CHECKOUT
    Wait Until Element Is Visible    accessibility_id=test-CONTINUE    20
    Sleep   5s
