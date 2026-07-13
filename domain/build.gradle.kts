plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "work.businessasusual.domain"
	compileSdk = 35

	defaultConfig {
		minSdk = 26
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	// Domain should not depend on Android UI
	buildFeatures {
		compose = false
	}
}

kotlin {
	compilerOptions {
		jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
	}
}

dependencies {
	// No Android UI dependencies here
	dependencies {
		implementation(libs.koin.core)
		testImplementation(libs.junit)
	}
}
