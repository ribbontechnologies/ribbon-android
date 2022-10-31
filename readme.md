# Ribbon Android SDK

### Getting started

This is the sample android app for integrating with the [Ribbon platform](https://ribbonapp.com/) android sdk.

To import the library:
```gradle
implementation("com.ribbonapp:android-sdk:(insert latest version)")
```

Please check [releases page](https://github.com/ribbontechnologies/ribbon-android/releases) for the latest release.

## Running the App
This repo includes a basic compose sample app to show how the sdk would work (see [demo video](#demo) below).

**Please make sure to add your own account config in [here](https://github.com/ribbontechnologies/ribbon-android/blob/e4b8ab4b87e90cfdb21bb7b2770c89b3df554755/app/src/main/java/com/ribbonapp/sample/MainViewModel.kt#L51 "here") before running the app.**

## 3rd Party Libraries
At the moment we only use [retrofit](https://square.github.io/retrofit/ "retrofit") to do the network calls, everything else is google/android default libraries.

## Demo

[sample_app_demo.webm](https://user-images.githubusercontent.com/111354857/198976124-c1d8818d-864c-4625-8843-e93f1b40f0ae.webm)

## Documentation / Links
- [Ribbon Android sdk](https://docs.ribbonapp.com/docs/mobile-android-sdk "Ribbon Android sdk")
- [Documentation](https://docs.ribbonapp.com/docs "Documentation")
- [Ribbon Webpage](https://www.ribbonapp.com/ "Ribbon Webpage")
