# WheelPicker

Android Wheel Picker library

<b>Supports from Android SDK version 16 and above</b>.

<b><h1>About</h1></b>
Android doesn't have an inbuilt wheel picker view, so this was build to provide similair features like its iOS counterpart.<br/><br/>This library provides two types of wheel picker views - Vertical and Horizontal.

<p align="center">
  <img src="https://user-images.githubusercontent.com/12429051/111363712-5a2cfe00-86b6-11eb-9b55-790cdbb81d9a.jpg" width="200"/>
  <img src="https://user-images.githubusercontent.com/12429051/111363677-5600e080-86b6-11eb-8bed-d9ad97815a92.jpg" width="200"/>
</p>

<b><h1>Usage</h1></b>
<b>Gradle dependency:</b>

Add the following to your project level build.gradle:

```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add this to your app build.gradle:

```java
dependencies {
    compile 'com.github.vikramezhil.wheelpicker:1.0.0'
}
```

<b>Maven:</b>

Add the following to the <repositories> section of your pom.xml:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Add the following to the <dependencies> section of your pom.xml:

```xml
<dependency>
    <groupId>com.github.vikramezhil</groupId>
    <artifactId>wheelpicker</artifactId>
    <version>1.0.0</version>
</dependency>
```

<b>Vertical Wheel Picker:</b>

```xml
<com.github.vikramezhil.wheelpicker.view.WheelPicker
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:wheelPickerBgColor="@color/colorAccent"
    app:wheelPickerDividerColor="@android:color/white"
    app:wheelPickerItemSelectedBgColor="@color/colorAccent"
    app:wheelPickerItemUnselectedBgColor="@color/colorAccent"
    app:wheelPickerItemsTextBold="true"
    app:wheelPickerItemsTextItalic="true"
    app:wheelPickerOrientationVertical="true"
    app:wheelPickerScaleDownEnabled="true"
    app:wheelPickerItemsTextSize="8dp"
    app:wheelPickerItemsUnselectedTextAlpha="0.8"
    app:wheelPickerItems="@array/app_items"/>
```

<b>Horizontal Wheel Picker:</b>

```xml
<com.github.vikramezhil.wheelpicker.view.WheelPicker
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:wheelPickerBgColor="@color/colorAccent"
    app:wheelPickerDividerColor="@android:color/white"
    app:wheelPickerItemSelectedBgColor="@color/colorAccent"
    app:wheelPickerItemUnselectedBgColor="@color/colorAccent"
    app:wheelPickerItemsTextBold="true"
    app:wheelPickerItemsTextItalic="true"
    app:wheelPickerOrientationVertical="false"
    app:wheelPickerScaleDownEnabled="true"
    app:wheelPickerItemsTextSize="8dp"
    app:wheelPickerItemsUnselectedTextAlpha="0.8"
    app:wheelPickerItems="@array/app_items"/>
```

<b><h1>Documentation</h1></b>

For a detailed documentation 📔, please have a look at the [Wiki](https://github.com/vikramezhil/WheelPicker/wiki).

<b><h1>License</h1></b>

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
