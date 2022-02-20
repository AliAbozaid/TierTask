import dependencies.DebugDependencies
import dependencies.NavigationDependencies
import dependencies.NetworkDependencies
import dependencies.LifecycleDependencies
import extensions.addTestsDependencies
import extensions.implementation
import extensions.api
import dependencies.KoinDependencies
import extensions.buildConfigStringField
import extensions.kapt

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
        getByName(BuildType.DEBUG) {
            buildConfigStringField("BASE_URL", "https://api.jsonbin.io/")
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardConsumerRules), AppConfig.proguardRules
            )
            buildConfigStringField("BASE_URL", "https://api.jsonbin.io/")
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
    api(NetworkDependencies.MOSHI)
    kapt(NetworkDependencies.MOSHI_CODEGEN)
    api(NetworkDependencies.OKHTTP_LIBRARIES)
    api(NetworkDependencies.RETROFIT_LIBRARIES)
    api(KoinDependencies.KOIN_LIBRARIES)
    implementation(LifecycleDependencies.LIFE_CYCLE_LIBRARIES)
    implementation(NavigationDependencies.NAVIGATION_LIBRARIES)
    implementation(AppDependencies.CONSTRAINT_LAYOUT)
    debugImplementation(DebugDependencies.LEAKCANARY)
    addTestsDependencies()
}
