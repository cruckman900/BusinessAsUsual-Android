plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
}
android {
	namespace = "work.businessasusual"
	compileSdk = 35

	defaultConfig {
		applicationId = "work.businessasusual"
		minSdk = 26
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables.useSupportLibrary = true
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	buildFeatures {
		compose = true
		viewBinding = true
	}

	packaging {
		resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
	}
}

kotlin {
	compilerOptions {
		jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
	}
}

dependencies {
	implementation(project(":domain"))
	implementation(project(":data"))

	// Core
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.core.splashscreen)

	// Compose BOM
	implementation(platform(libs.androidx.compose.bom))

	// Compose
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.material.icons.extended)

	// Compose tooling
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)

	// Activity + Lifecycle + Navigation
	implementation(libs.activity.compose)
	implementation(libs.lifecycle.viewmodel.compose)
	implementation(libs.lifecycle.runtime.compose)
	implementation(libs.navigation.compose)

	// Koin
	implementation(libs.koin.core)
	implementation(libs.koin.android)
	implementation(libs.koin.compose)

	// Charts
	implementation(libs.vico.compose.m3)

	// Tests
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
}
