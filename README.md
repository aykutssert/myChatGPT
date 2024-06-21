### Android Application Usage

#### Introduction

This project is an Android application that uses the OpenAI API to answer user questions. It operates within the daily limits set by OpenAI. This section will guide you through the steps to run the project.

#### Requirements

- Android Studio
- OpenAI API Key
- Internet connection

#### Steps to Run the Project

1. Download and Open Project Files

   - Download the project files and open them with Android Studio.

2. Add API Key

   - Open the MainActivity.java file.
   - Replace YOUR_API_KEY in the following code snippet with your OpenAI API key
     ![api](api_key.png)

3. Check Project Dependencies
   - Ensure all required dependencies are defined in the project's build.gradle file, especially okhttp and jackson libraries:
   ```
   dependencies {
       implementation 'com.squareup.okhttp3:okhttp:4.9.3'
       implementation 'com.fasterxml.jackson.core:jackson-databind:2.12.5'
   }
   ```
4. Configure and Run the Project
   - Configure the project and check for any errors.
   - Connect your Android device or emulator and run the application.

#### Usage

    - Once the application opens, you will see a text box where you can type your questions and a send button.
    - Type your questions into the text box and click the send button.
    - The OpenAI API will process your questions and display the responses on the screen.

![androidUI.png](https://github.com/aykutssert/openai_api/blob/main/images/androidUI.png)

### IOS Application Usage

    ...
