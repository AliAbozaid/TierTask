import dependencies.DebugDependencies
import dependencies.NavigationDependencies
import dependencies.LifecycleDependencies
import extensions.addTestsDependencies
import extensions.implementation

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    lint {
        lintConfig = file("../.lint/config.xml")
        checkAllWarnings = true
        warningsAsErrors = false
    }

    buildTypes {
        getByName(BuildType.DEBUG) {}
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardConsumerRules), AppConfig.proguardRules
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

    api(AppDependencies.APPCOMPAT)
    api(AppDependencies.MATERIAL)
    api(AppDependencies.PLAY_SERVICES_LOCATION)
    api(AppDependencies.GOOGLE_MAPS_UTILS)
    api(AppDependencies.GOOGLE_MAPS)
    implementation(LifecycleDependencies.LIFE_CYCLE_LIBRARIES)
    implementation(NavigationDependencies.NAVIGATION_LIBRARIES)
    implementation(AppDependencies.CONSTRAINT_LAYOUT)
    debugImplementation(DebugDependencies.LEAKCANARY)
    addTestsDependencies()
}
