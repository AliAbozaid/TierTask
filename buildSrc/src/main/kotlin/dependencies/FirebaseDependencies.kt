package dependencies

import BuildDependenciesVersions

object FirebaseDependencies {
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${BuildDependenciesVersions.FIREBASE}"
    private const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
    private const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    private const val FIREBASE_MESSAGING = "com.google.firebase:firebase-messaging-ktx"
    private const val FIREBASE_PERF = "com.google.firebase:firebase-perf-ktx"

    val FIREBASE_LIBRARIES = arrayListOf<String>().apply {
        add(FIREBASE_CRASHLYTICS)
        add(FIREBASE_ANALYTICS)
        add(FIREBASE_MESSAGING)
        add(FIREBASE_PERF)
    }
}
