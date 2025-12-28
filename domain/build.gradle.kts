plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.businessasusualandroid.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Domain should not depend on Android UI
    buildFeatures {
        compose = false
    }
}

dependencies {
    // No Android UI dependencies here
    dependencies {
        implementation(libs.koin.core)
        testImplementation(libs.junit)
    }
}