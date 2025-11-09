***Settings***
Library    AppiumLibrary
Library    BuiltIn
Test Setup    Open App
Test Teardown    Close App

***Variables***
${REMOTE_URL}       http://127.0.0.1:4723
${DEVICE_NAME}      emulator-5554
${PLATFORM_NAME}    Android
${APP_PACKAGE}      com.swaglabsmobileapp
${APP_ACTIVITY}     .MainActivity
${TIMEOUT}          10s

# btnLockedOutUser
${BTN_LOCKED_OUT}    xpath=//*[@class = 'android.widget.TextView' and (@text = 'locked_out_user' or . = 'locked_out_user')]
# btnLogin (absolute xpath provided)
${BTN_LOGIN}         xpath=//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.TextView[1]
# btnProblemUser
${BTN_PROBLEM_USER}  xpath=//*[@class = 'android.widget.TextView' and (@text = 'problem_user' or . = 'problem_user')]
# btnStandardUser
${BTN_STANDARD_USER}    xpath=//*[@class = 'android.widget.TextView' and (@text = 'standard_user' or . = 'standard_user')]
# lblErrorMessage (absolute xpath provided)
${LBL_ERROR_MESSAGE}   xpath=//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.TextView[1]
# txbPassword
${TXB_PASSWORD}      xpath=//*[@class = 'android.widget.EditText' and (@text = 'Password' or . = 'Password')]
# txbUsername (absolute xpath provided)
${TXB_USERNAME}      xpath=//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.EditText[1]

${LBL_PRODUCTS_TITLE}   xpath=//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.widget.TextView[1]

***Keywords***
Open App
    [Documentation]    Opens the app using Appium. Adjust variables at top if needed.
    Open Application    ${REMOTE_URL}
    ...    platformName=${PLATFORM_NAME}
    ...    deviceName=${DEVICE_NAME}
    ...    appPackage=${APP_PACKAGE}
    ...    appActivity=${APP_ACTIVITY}
    ...    automationName=UiAutomator2
    Wait Until Page Contains Element    ${BTN_LOGIN}    ${TIMEOUT}

Close App
    Close Application

***Test Cases***
Login With Empty Password
    [Documentation]    Enter username only and verify password-required error
    Input Text    ${TXB_USERNAME}    standard_user
    Click Element    ${BTN_LOGIN}
    Wait Until Page Contains Element    ${LBL_ERROR_MESSAGE}    ${TIMEOUT}
    ${actual}=    Get Text    ${LBL_ERROR_MESSAGE}
    Should Be Equal    ${actual}    Password is required

Login With Empty Username
    [Documentation]    Tap login with no username and verify username-required error
    Click Element    ${BTN_LOGIN}
    Wait Until Page Contains Element    ${LBL_ERROR_MESSAGE}    ${TIMEOUT}
    ${actual}=    Get Text    ${LBL_ERROR_MESSAGE}
    Should Be Equal    ${actual}    Username is required

Login With Locked Out User
    [Documentation]    Attempt login with a locked out account and verify locked-out message
    Click Element    ${BTN_LOCKED_OUT}
    Click Element    ${BTN_LOGIN}
    Wait Until Page Contains Element    ${LBL_ERROR_MESSAGE}    ${TIMEOUT}
    ${actual}=    Get Text    ${LBL_ERROR_MESSAGE}
    Should Be Equal    ${actual}    Sorry, this user has been locked out.

Login With Standard User
    [Documentation]    Successful login with standard_user should land on Products page
    Click Element    ${BTN_STANDARD_USER}
    Click Element    ${BTN_LOGIN}
    Wait Until Page Contains Element    ${LBL_PRODUCTS_TITLE}    ${TIMEOUT}
    Element Should Be Visible    ${LBL_PRODUCTS_TITLE}

Login With Wrong Account
    [Documentation]    Wrong password shows appropriate error message
    Input Text    ${TXB_USERNAME}    standard_user
    Input Text    ${TXB_PASSWORD}    wrong_password
    Click Element    ${BTN_LOGIN}
    Wait Until Page Contains Element    ${LBL_ERROR_MESSAGE}    ${TIMEOUT}
    ${actual}=    Get Text    ${LBL_ERROR_MESSAGE}
    Should Be Equal    ${actual}    Username and password do not match any user in this service.