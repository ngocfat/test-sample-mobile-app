*** Settings ***
Library           AppiumLibrary
Library           BuiltIn
Library           Collections
Suite Setup       Start App And Login
Suite Teardown    Close Application
Test Setup        Go To Home Screen

*** Variables ***
${REMOTE_URL}       http://127.0.0.1:4723
${DEVICE_NAME}      emulator-5554
${PLATFORM_NAME}    Android
${APP_PACKAGE}      com.swaglabsmobileapp
${APP_ACTIVITY}     com.swaglabsmobileapp.MainActivity

${VALID_USER}       standard_user
${VALID_PASS}       secret_sauce

${SORT_NAME_AZ}     Name (A to Z)
${SORT_NAME_ZA}     Name (Z to A)
${SORT_PRICE_LOW}   Price (low to high)
${SORT_PRICE_HIGH}  Price (high to low)

*** Keywords ***
Start Application
    Open Application    ${REMOTE_URL}
    ...                 platformName=${PLATFORM_NAME}
    ...                 deviceName=${DEVICE_NAME}
    ...                 appPackage=${APP_PACKAGE}
    ...                 appActivity=${APP_ACTIVITY}
    ...                 automationName=UiAutomator2

Start App And Login
    Start Application
    Login Successful

Login Successful
    Wait Until Element Is Visible    accessibility_id=test-Username    15s
    Input Text                       accessibility_id=test-Username    ${VALID_USER}
    Input Text                       accessibility_id=test-Password    ${VALID_PASS}
    Click Element                    accessibility_id=test-LOGIN
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    15s

Go To Home Screen
    Wait Until Element Is Visible    accessibility_id=test-PRODUCTS    10s

Swipe Up
    Swipe    start_x=540    start_y=1700    end_x=540    end_y=400
    Sleep    2s

Swipe Down
    Swipe    start_x=540    start_y=400    end_x=540    end_y=1700
    Sleep    2s

Swipe Until Element Is Visible
    [Arguments]    ${locator}    ${max_swipes}=5
    FOR    ${i}    IN RANGE    ${max_swipes}
        ${visible}=    Run Keyword And Return Status    Element Should Be Visible    ${locator}    1s
        Run Keyword If    ${visible}    Exit For Loop
        Swipe Up
    END

Sort Products
    [Arguments]    ${sort_option}
    Click Element    accessibility_id=test-Modal Selector Button
    Wait Until Element Is Visible    xpath=//*[@text="${sort_option}"]    5s
    Click Element    xpath=//*[@text="${sort_option}"]
    Sleep    5s

Get Product Names
    Swipe Up
    Sleep    5s   # Dừng sau khi cuộn
    Swipe Down
    ${items}=    Get WebElements    xpath=//*[@content-desc="test-Item"]
    ${names}=    Create List
    FOR    ${i}    IN RANGE    ${items.__len__()}
        ${name}=    Get Text    xpath=(//*[@content-desc="test-Item"])[${i + 1}]//*[@content-desc="test-Item title"]
        Append To List    ${names}    ${name}
    END
    [Return]    ${names}

Get Product Prices
    Swipe Up
    Sleep    5s   # Dừng sau khi cuộn
    Swipe Down
    ${items}=    Get WebElements    xpath=//*[@content-desc="test-Item"]
    ${prices}=    Create List
    FOR    ${i}    IN RANGE    ${items.__len__()}
        ${locator}=    Set Variable    (//*[@content-desc="test-Item"])[${i + 1}]//*[@content-desc="test-Price"]
        ${exists}=     Run Keyword And Return Status    Element Should Be Visible    ${locator}    1s
        IF    ${exists}
            ${price_text}=    Get Text    ${locator}
        ELSE
            ${price_text}=    Set Variable    0
        END
        ${price_val}=    Evaluate    float(str(${price_text}).replace('$',''))
        Append To List    ${prices}    ${price_val}
    END
    [Return]    ${prices}





*** Test Cases ***
TC_FP_01 | Filter Products by Name A–Z
    [Documentation]    \n1. Click the filter icon.\n2. Select "Name (A to Z)".\nExpected Result: The list is displayed in A–Z order.
    Sort Products    ${SORT_NAME_AZ}
    ${names}=    Get Product Names
    ${result}=    Evaluate    "${names[0]}" <= "${names[1]}"
    Should Be True    ${result}

TC_FP_02 | Filter Products by Name Z–A
    [Documentation]    \n1. Click the filter icon.\n2. Select "Name (Z to A)".\nExpected Result: The list is displayed in Z–A order.
    Sort Products    ${SORT_NAME_ZA}
    ${names}=    Get Product Names
    ${result}=    Evaluate    "${names[0]}" >= "${names[1]}"
    Should Be True    ${result}

TC_FP_03 | Filter Products by Price Low to High
    [Documentation]    \n1. Click the filter icon.\n2. Select "Price (low to high)".\nExpected Result: Products are displayed in ascending price order.
    Sort Products    ${SORT_PRICE_LOW}
    ${prices}=    Get Product Prices
    Should Be True    ${prices[0]} <= ${prices[1]}

TC_FP_04 | Filter Products by Price High to Low
    [Documentation]    \n1. Click the filter icon.\n2. Select "Price (high to low)".\nExpected Result: Products are displayed in descending price order.
    Sort Products    ${SORT_PRICE_HIGH}
    ${prices}=    Get Product Prices
    Should Be True    ${prices[0]} >= ${prices[1]}
