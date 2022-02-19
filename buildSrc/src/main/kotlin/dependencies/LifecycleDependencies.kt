package dependencies

import BuildDependenciesVersions

object LifecycleDependencies {
    private const val COMMON = "androidx.lifecycle:lifecycle-common-java8:${BuildDependenciesVersions.LIFECYCLE}"
    private const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    private const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    private const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    private const val EXTENSION = "androidx.lifecycle:lifecycle-extensions:${BuildDependenciesVersions.LIFECYCLE_EXTENSION}"

    val LIFE_CYCLE_LIBRARIES = arrayListOf<String>().apply {
        add(EXTENSION)
        add(COMMON)
        add(RUNTIME)
        add(LIVEDATA)
        add(VIEW_MODEL)
    }
}
