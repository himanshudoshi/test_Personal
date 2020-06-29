# NMF UI Layer (0.1 Beta)



This layer has been designed and created to share LookAndFeel behaviour, and custom UI components over all apps/branches including My Bell Mobile - **MBM**, My Virgin Mobile - **MVM**, **Lucky Mobile**, and **PcMobile**. In the current veriosn it provides the folowing features:

* Base Theming and Styling (including fonts).
* Supporting Custom Model Panels.
* NMF Bottom Navigation 1.0 Beta.
* NMF Payment Carousel 0.9 Beta.
* NMF Drawer 0.1 Alpha.
* Orientation Support.
* Custom List Items.
* Language Localization.

## Requirements

* Android APi 21 on.
* Works on both Java 7+ and Kotlin 1.2.x+.
* Gradle 3.1.x on.
* Android Studio 2.x on.
* Works IntelJ Idea Android plugin.


## How To Install

Inside your app build gradle file (build.gradle) and under dependencies caluse, add the folowing line:

```
implementation "ca.bell.nmf:android-ui:$nmf_ui_api" //$nmf_ui_api = 0.0.1 for current version.
```

OR

```
dependencies {
	implementation "ca.bell.nmf:android-ui:$nmf_ui_api" // $nmf_ui_api = 0.0.1 for current version
}
```


**NB:** ```$nmf_ui_api``` can be defined on ext{} clause inside project build.gradle file. Current version is 0.0.1.

## Kdoc - Kotlin Documentation
 
 All classes on NMF UI framework has kdoc documentation that explains each functionality. This documentation can help  developers to better understand their needs from different componenets of the framework.
 
## Unit Testing

Android Testing and/or Unit testing is included for each main functionality that can be unit tested. For the rest we added example projects to showcase and demosntrate the way developers can efficiently use NMF UI framework.

## Contribution Guidelines

Whenever you want to add new component into NMF UI framework follow the guidelines given below: 

1. Make sure to follow the directory structure provided below. 

2. Write down detailed demo which should be sufficient to demonstrate the in depth usage of the component. 

3. Unit test the component, cover all the aspects of the component and try to achieve maximum coverage.

4. Must provide Kdoc that covers every new functionality added to the framework.

## Folder Structure

Bellow image demonstrates project's skeletone that should be followed through out any development or changes to the framework.

![](/Users/sami.lassed/Desktop/nmf-ui-structure.png)
