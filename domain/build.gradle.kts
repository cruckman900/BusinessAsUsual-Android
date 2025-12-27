plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.businessasusualandroid.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    // Domain should not depend on Android UI
    buildFeatures {
        compose = false
    }
}

dependencies {
    // No Android UI dependencies here
}