plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	kotlin("plugin.serialization")
}

android {
	namespace = "work.businessasusual.data"
	compileSdk = 35

	defaultConfig {
		minSdk = 26

		// Local dev services reachable from the emulator via the host loopback.
		buildConfigField("String", "LOCAL_HR_URL", "\"http://10.0.2.2:5041/\"")
		buildConfigField("String", "LOCAL_REGISTRY_URL", "\"http://10.0.2.2:5100/\"")

		// AWS ALB endpoint. Replace with the ALB DNS printed by deploy/aws/02-provision-infra.ps1.
		// HR + registry share one ALB (path-routed), so both point at the same base.
		buildConfigField("String", "AWS_HR_URL", "\"http://REPLACE_WITH_ALB_DNS/\"")
		buildConfigField("String", "AWS_REGISTRY_URL", "\"http://REPLACE_WITH_ALB_DNS/\"")
	}

	buildTypes {
		debug {
			// Debug: try LOCAL first, then AWS.
			buildConfigField("Boolean", "AWS_FIRST", "false")
		}
		release {
			// Release: try AWS first, then LOCAL.
			buildConfigField("Boolean", "AWS_FIRST", "true")
		}
	}

	buildFeatures {
		buildConfig = true
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

kotlin {
	compilerOptions {
		jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
	}
}

dependencies {
	implementation(project(":domain"))
	implementation(libs.koin.core)
	testImplementation(libs.junit)
	implementation("com.squareup.retrofit2:retrofit:2.11.0")
	implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
	implementation("io.insert-koin:koin-android:3.5.6")

}
