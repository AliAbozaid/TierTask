import dependencies.DebugDependencies
import dependencies.NavigationDependencies
import extensions.addTestsDependencies
import extensions.debugImplementation
import extensions.implementation
import extensions.buildConfigStringField
import dependencies.KoinDependencies

plugins {
	id("com.android.library")
	kotlin("android")
	kotlin("kapt")
	id("kotlin-parcelize")
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
		getByName(BuildType.DEBUG) {
			buildConfigStringField("PAGER_ID", "5fa8ff8dbd01877eecdb898f")
		}
		getByName(BuildType.RELEASE) {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile(AppConfig.proguardConsumerRules),
				AppConfig.proguardRules
			)
			buildConfigStringField("PAGER_ID", "5fa8ff8dbd01877eecdb898f")
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_11.toString()
		freeCompilerArgs = listOf(
			"-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
			"-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
		)
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
	implementation(project(":feature:details"))
	implementation(KoinDependencies.KOIN_LIBRARIES)
	implementation(AppDependencies.COROUTINES_LIBRARIES)
	implementation(NavigationDependencies.NAVIGATION_LIBRARIES)
	implementation(AppDependencies.CONSTRAINT_LAYOUT)
	debugImplementation(DebugDependencies.LEAKCANARY)
	addTestsDependencies()
}
