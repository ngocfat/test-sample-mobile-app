*** Settings ***
Library           AppiumLibrary
Suite Setup       Start Application
Suite Teardown    Close Application
Test Setup        Reset App

*** Variables ***
${REMOTE_URL}       http://127.0.0.1:4723
${DEVICE_NAME}      emulator-5554
${PLATFORM_NAME}    Android
${APP_PACKAGE}      com.swaglabsmobileapp
${APP_ACTIVITY}     com.swaglabsmobileapp.MainActivity

${USER_VALID}       standard_user
${PASS_VALID}       secret_sauce


*** Keywords ***
Start Application
    Open Application    ${REMOTE_URL}
    ...                 platformName=${PLATFORM_NAME}
    ...                 deviceName=${DEVICE_NAME}
    ...                 appPackage=${APP_PACKAGE}
    ...                 appActivity=${APP_ACTIVITY}
    ...                 automationName=UiAutomator2

Reset App
    Close Application
    Start Application

Input Credentials
    [Arguments]    ${username}    ${password}
    Wait Until Element Is Visible    accessibility_id=test-Username    20s
    Input Text                       accessibility_id=test-Username    ${username}
    Input Text                       accessibility_id=test-Password    ${password}

Tap Login Button
    Click Element    accessibility_id=test-LOGIN

Verify Login Success
    Wait Until Page Contains Element    accessibility_id=test-Cart    20s


*** Keywords ***
Verify Error Message
    [Arguments]    ${expected_error}
    ${actual}=    Get Text    xpath=//*[@content-desc='test-Error message']/*[@class='android.widget.TextView']
    Should Contain    ${actual}    ${expected_error}

Clear Fields
    Clear Text    accessibility_id=test-Username
    Clear Text    accessibility_id=test-Password


*** Test Cases ***
TC1 Valid Login
    [Documentation]    Verify that user can log in successfully with valid credentials.
    Input Credentials    ${USER_VALID}    ${PASS_VALID}
    Tap Login Button
    Verify Login Success

TC2 Invalid Login - Wrong Password
    [Documentation]    Verify that login fails when the password is incorrect.
    Sleep    3s
    Clear Fields
    Input Credentials    ${USER_VALID}    wrong_password
    Tap Login Button
    Verify Error Message    Username and password do not match any user in this service.

TC3 Invalid Login - Locked Out User
    [Documentation]    Verify that locked-out user cannot log in.
    Sleep    3s
    Clear Fields
    Input Credentials    locked_out_user    ${PASS_VALID}
    Tap Login Button
    Verify Error Message    Sorry, this user has been locked out.

TC4 Empty Username
    [Documentation]    Verify that login fails when username field is empty.
    Sleep    3s
    Clear Fields
    Input Credentials    ${EMPTY}    ${PASS_VALID}
    Tap Login Button
    Verify Error Message    Username is required

TC5 Empty Password
    [Documentation]    Verify that login fails when password field is empty.
    Sleep    3s
    Clear Fields
    Input Credentials    ${USER_VALID}    ${EMPTY}
    Tap Login Button
    Verify Error Message    Password is required