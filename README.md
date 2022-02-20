# Map Visualisation

[![Build Status](https://app.bitrise.io/app/7f9eccab2766f1d8/status.svg?token=qy75UwAHMBgOtoZbljhoFw&branch=development)](https://app.bitrise.io/app/7f9eccab2766f1d8)

Map Visualisation is an Android project showing scooters location on a map, based on [MVVM architecture](https://developer.android.com/jetpack/guide).
Scooters are individual map pins on Google Maps, but they are clustered during zooming.
When a pin is pressed some details of the vehicle is shown on a bottom sheet.

![Showcase](/docs/showcase.png)

## Features
- 100% Kotlin
- MVVM architecture
- Reactive pattern
- Android Jetpack
- Kotlin Coroutines + Flow
- Dependency injection
- CI/CD support

## Tech stack
- [Android Jetpack](https://developer.android.com/jetpack) - A collections of libraries to help developers follow best practices, reduce boilerplate code, and write code that works consistently across Android versions and devices.
    - [Hilt](https://developer.android.com/jetpack/androidx/releases/hilt) - Extend the functionality of Dagger Hilt to enable dependency injection of certain classes from the androidx libraries.
    - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Lifecycle-aware components that can adjust behavior based on the current lifecycle state of an activity or fragment.
    - [Room](https://developer.android.com/jetpack/androidx/releases/room) - Create, store, and manage persistent data backed by a SQLite database.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/overview) - Add maps to your Android app using Google Maps data, map displays, and map gesture responses.
- [PermissionDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher) - A declarative API to handle Android runtime permissions.
- [Fused Location Provider](https://developers.google.com/location-context/fused-location-provider) - Get location data for your app based on combined signals from the device sensors using a battery-efficient API.
- [Logcat](https://github.com/square/logcat) - Tiny Kotlin API for cheap logging on top of Android's normal Log class.
- [MockK](https://mockk.io) - Mocking library for Kotlin.
- [Bitrise](https://www.bitrise.io) - Mobile-first CI/CD in the cloud, for any platform.

## Development setup
You need [Android Studio Arctic Fox](https://developer.android.com/studio) (or newer) to be able to build the app.

### Code style
This project uses [ktlint](https://github.com/pinterest/ktlint). Use the `ktlint` and `ktlint-format` Gradle tasks
to check and format according to proper Kotlin lint rules.

### API key :key:
You will need to provide API keys to use the app.

Firstly you have to create an API key for the Maps SDK for Android.
You can find information about how to gain access in the following link.
- [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

Secondly you have to own the secret key for the appropriate JSONBin.io bin.

Add the keys to the `local.properties` file:

```
# API keys
API_KEY=jsonbin_secret_key
MAPS_API_KEY=maps_api_key
```
