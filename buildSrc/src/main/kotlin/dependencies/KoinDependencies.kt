package dependencies

import BuildDependenciesVersions

object KoinDependencies {
    private const val KOIN_ANDROID = "io.insert-koin:koin-android:${BuildDependenciesVersions.KOIN}"
    const val KOIN_EXT = "io.insert-koin:koin-android-ext:${BuildDependenciesVersions.KOIN}"

    val KOIN_LIBRARIES = arrayListOf<String>().apply {
        add(KOIN_ANDROID)
        add(KOIN_EXT)
    }
}
