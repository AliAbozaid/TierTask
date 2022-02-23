plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://plugins.gradle.org/m2/")
    gradlePluginPortal()
}

object PluginsVersions {
    const val GRADLE = "7.1.1"
    const val KOTLIN = "1.6.10"
    const val NAVIGATION_PLUGIN = "2.3.5"
    const val GOOGLE_SERVICES = "4.3.8"
    const val KTLINT = "0.41.0"
    const val DETEKT_VERSION = "1.19.0"
    const val FIREBASE_CRASHLYTICS = "2.7.1" // crashlytics
    const val FIREBASE_APP_DISTRIBUTION = "2.1.3"
    const val FIREBASE_PERF_PLUGIN = "1.4.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginsVersions.KOTLIN}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("com.google.gms:google-services:${PluginsVersions.GOOGLE_SERVICES}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION_PLUGIN}")
    implementation("com.google.firebase:firebase-appdistribution-gradle:${PluginsVersions.FIREBASE_APP_DISTRIBUTION}")
    implementation("com.google.firebase:firebase-crashlytics-gradle:${PluginsVersions.FIREBASE_CRASHLYTICS}")
    implementation("com.google.firebase:perf-plugin:${PluginsVersions.FIREBASE_PERF_PLUGIN}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT_VERSION}")
    implementation(kotlin("stdlib-jdk8"))
}
