package dependencies

import BuildDependenciesVersions

object NetworkDependencies {

    private const val OKHTTP = "com.squareup.okhttp3:okhttp:${BuildDependenciesVersions.OKHTTP}"
    private const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${BuildDependenciesVersions.OKHTTP}"

    private const val RETROFIT = "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.RETROFIT}"
    private const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${BuildDependenciesVersions.RETROFIT}"

    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${BuildDependenciesVersions.MOSHI}"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${BuildDependenciesVersions.MOSHI}"

    val OKHTTP_LIBRARIES = arrayListOf<String>().apply {
        add(OKHTTP)
        add(OKHTTP_LOGGING_INTERCEPTOR)
    }

    val RETROFIT_LIBRARIES = arrayListOf<String>().apply {
        add(RETROFIT)
        add(RETROFIT_MOSHI_CONVERTER)
    }
}
