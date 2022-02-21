import dependencies.DebugDependencies
import dependencies.NavigationDependencies
import dependencies.KoinDependencies
import extensions.debugImplementation
import extensions.implementation

plugins {
	id("com.android.library")
	kotlin("android")
	kotlin("kapt")
	id("kotlin-parcelize")
	id("androidx.navigation.safeargs.kotlin")
}

android {
	compileSdk = AppConfig.compileSdk

	defaultConfig {
		minSdk = AppConfig.minSdkVersion
		targetSdk = AppConfig.targetSdkVersion
		testInstrumentationRunner = AppConfig.androidTestInstrumentation
	}

	lint {
		lintConfig = file("../../.lint/config.xml")
		checkAllWarnings = true
		warningsAsErrors = false
	}

	buildTypes {
		getByName(BuildType.DEBUG) {}
		getByName(BuildType.RELEASE) {
			isMinifyEnabled = true
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
	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	implementation(project(":navigation"))
	implementation(project(":common"))
	implementation(AppDependencies.MATERIAL)
	implementation(KoinDependencies.KOIN_LIBRARIES)
	implementation(AppDependencies.COROUTINES_LIBRARIES)
	implementation(NavigationDependencies.NAVIGATION_LIBRARIES)
	implementation(AppDependencies.CONSTRAINT_LAYOUT)
	debugImplementation(DebugDependencies.LEAKCANARY)
}
