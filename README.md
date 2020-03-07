# Highlightable Calendar View

This is a calendar-view which lets you highlight individual days in specific colors. You can also customize almost every aspect of it. You can use this library in your application by adding it to your dependencies in your gradle build script.

If you enjoy this library, send me a tweet [@varun_barad](https://twitter.com/varun_barad) or visit my [blog](https://varunbarad.com/blog).

## Features

Currently it supports following features:

- Next and previous month navigation.
- Allow various customisations including background and text color for day, week, month.
- Set custom start-of-week day. It defaults to Sunday.
- Event callbacks for month-change and date-select.
- Highlighting individual days' text and background colors with decorators.

## Adding in your project

__Step 1:__ Add the JitPack repository to your root-level `build.gradle` file.

```groovy
repositories {
    maven { url "https://jitpack.io" }
    ...
}
```

__Step 2:__ Add the dependency in your module-level `build.gradle`

```groovy
dependencies {
    implementation "com.varunbarad:highlightable-calendar-view:1.0.0"
    ...
}
```
