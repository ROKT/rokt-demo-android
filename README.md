# rokt-demo-android
Android Demo Application

Rokt Demo application is a sample app built to showcase Rokt SDK functionality.

## Requirements

Latest version Android Studio Bumblebee. Project is configured to run on Android API 21 and above and compiled against API 30.

## Project architecture

This project it is implemented based on MVVM pattern and repository pattern.
![Architecture](demo.png)

## CI/CD System

**Buildkite** is used as the CI system https://buildkite.com/rokt/rokt-demo-android
Buildkite pipelines are defined in `.buildkite` directory.
It uses **docker** container for executing the build steps using the **Fastlane** build tool.

## FAQ

# How can I use mock data?
In the ApplicationModule, change provideDemoRepository to return DemoLibraryRepositoryMockImpl and provideAboutRoktRepository to return AboutRoktRepositoryMockImpl.

# Where are dependencies defined?
They are defined under buildSrc/dependencies. For more information on kotlin_dsl visit: https://docs.gradle.org/current/userguide/kotlin_dsl.html

## License

Copyright 2020 Rokt Pte Ltd

Licensed under the Rokt Software Development Kit (SDK) Terms of Use
Version 2.0 (the "License");

You may not use this file except in compliance with the License.

You may obtain a copy of the License at https://rokt.com/sdk-license-2-0/
