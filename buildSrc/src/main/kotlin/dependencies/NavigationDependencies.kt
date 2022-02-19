package dependencies

import BuildDependenciesVersions

object NavigationDependencies {
    private const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.NAVIGATION}"
    private const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.NAVIGATION}"

    val NAVIGATION_LIBRARIES = arrayListOf<String>().apply {
        add(NAVIGATION_FRAGMENT)
        add(NAVIGATION_UI)
    }
}
