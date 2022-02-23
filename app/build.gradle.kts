import dependencies.DebugDependencies
import dependencies.KoinDependencies
import dependencies.FirebaseDependencies
import dependencies.NavigationDependencies
import extensions.addTestsDependencies
import extensions.implementation


val buildVersionCode = Integer.parseInt(System.getenv("versionCode") ?: "1")
val buildVersionName = System.getenv("versionName") ?: "0.0.1"
val firebaseAppId = System.getenv("FIREBASE_APP_ID") ?: ""
val releaseNotes = System.getenv("RELEASE_NOTES") ?: ""
val buildTypeName = System.getenv("build_type") ?: ""

plugins {
	id("com.android.application")
	kotlin("android")
	id("kotlin-android")
	kotlin("kapt")
	id("com.google.gms.google-services")
	id("androidx.navigation.safeargs.kotlin")
	id("kotlin-parcelize")
	id("com.google.firebase.crashlytics")
	id("com.google.firebase.appdistribution")
}

android {
	compileSdk = AppConfig.compileSdk

	defaultConfig {
		applicationId = "app.tier"
		minSdk = AppConfig.minSdkVersion
		targetSdk = AppConfig.targetSdkVersion
		versionCode = buildVersionCode
		versionName = buildVersionName
		testInstrumentationRunner = AppConfig.androidTestInstrumentation
	}

	buildFeatures {
		viewBinding = true
	}

	signingConfigs {
		getByName("debug") {
			storeFile = file("../debug.keystore")
			keyAlias = "androiddebugkey"
			keyPassword = "android"
			storePassword = "android"

			firebaseAppDistribution {
				appId = firebaseAppId
				groups = "Test"
				releaseNotesFile = releaseNotes
			}
		}

		create("release") {
			storeFile = file("../release.keystore")
			storePassword = "Tier123"
			keyAlias = "Tier"
			keyPassword = "Tier123"
		}
	}

	buildTypes {
		getByName("debug") {
			isDebuggable = true
			applicationIdSuffix = ".debug"
			signingConfig = signingConfigs.getByName("debug")
		}
		getByName("release") {
			isMinifyEnabled = true
			signingConfig = signingConfigs.getByName("release")
			proguardFiles(
				getDefaultProguardFile(AppConfig.proguardConsumerRules),
				AppConfig.proguardRules
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_11.toString()
	}

	sourceSets {
		getByName("main") {
			java.srcDir("src/main/kotlin")
		}
		getByName("test") {
			java.srcDir("src/test/kotlin")
		}
		getByName("androidTest") {
			java.srcDir("src/androidTest/kotlin")
		}
	}

	lint {
		lintConfig = file("../.lint/config.xml")
		checkAllWarnings = true
		warningsAsErrors = false
	}

	testOptions {
		unitTests.isIncludeAndroidResources = true
		unitTests.isReturnDefaultValues = true
	}

}

dependencies {
	implementation(project(":navigation"))
	implementation(project(":common"))
	implementation(project(":feature:map"))
	implementation(AppDependencies.KOTLIN)
	implementation(platform(FirebaseDependencies.FIREBASE_BOM))
	implementation(FirebaseDependencies.FIREBASE_LIBRARIES)

	// Presentation
	implementation(NavigationDependencies.NAVIGATION_LIBRARIES)
	implementation(AppDependencies.CONSTRAINT_LAYOUT)

	// Koin
	implementation(KoinDependencies.KOIN_LIBRARIES)

	// Coroutines
	implementation(AppDependencies.COROUTINES_LIBRARIES)

	implementation(AppDependencies.TIMBER)

	debugImplementation(DebugDependencies.LEAKCANARY)

	addTestsDependencies()
}
