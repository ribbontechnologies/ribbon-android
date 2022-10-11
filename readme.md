# Ribbon Android Sdk

### Getting started

This is the android sdk for integrating with the [Ribbon platform](https://ribbonapp.com/) in an Android App.

To import the library:
```gradle
	implementation("com.ribbon:android-sdk:0.0.1")
```

## Sample App
This repo includes a basic compose sample app to show how the sdk would work.

**Please make sure to add your own account config in [here](https://github.com/ribbontechnologies/ribbon-android/blob/e4b8ab4b87e90cfdb21bb7b2770c89b3df554755/app/src/main/java/com/ribbonapp/sample/MainViewModel.kt#L51 "here") before running the app.**

## 3rd Party Libraries
At the moment we only use [retrofit](https://square.github.io/retrofit/ "retrofit") to do the network calls, everything else is google/android default libraries.

## Documentation / Links
- [Ribbon Webpage](https://www.ribbonapp.com/ "Ribbon Webpage")
- [Documentation](https://docs.ribbonapp.com/docs "Documentation")
