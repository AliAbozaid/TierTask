object AppDependencies {
	const val CONSTRAINT_LAYOUT =
		"androidx.constraintlayout:constraintlayout:${BuildDependenciesVersions.CONSTRAINT_LAYOUT}"
	const val APPCOMPAT =
		"androidx.appcompat:appcompat:${BuildDependenciesVersions.APPCOMPAT}"
	const val MATERIAL =
		"com.google.android.material:material:${BuildDependenciesVersions.MATERIAL}"
	const val KOTLIN =
		"org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildDependenciesVersions.KOTLIN}"

	const val PLAY_SERVICES_LOCATION =
		"com.google.android.gms:play-services-location:${BuildDependenciesVersions.PLAY_SERVICES}"

	const val GOOGLE_MAPS_UTILS =
		"com.google.maps.android:android-maps-utils:${BuildDependenciesVersions.GOOGLE_MAPS_UTILS}"

	const val GOOGLE_MAPS =
		"com.google.android.gms:play-services-maps:${BuildDependenciesVersions.GOOGLE_MAPS}"

	const val MOSHI =
		"com.squareup.moshi:moshi-kotlin:${BuildDependenciesVersions.MOSHI}"
	const val MOSHI_CODEGEN =
		"com.squareup.moshi:moshi-kotlin-codegen:${BuildDependenciesVersions.MOSHI}"

	// coroutinesDependencies
	private const val COROUTINES_CORE =
		"org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.COROUTINES}"
	private const val COROUTINES_ANDROID =
		"org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.COROUTINES}"

	val COROUTINES_LIBRARIES = arrayListOf<String>().apply {
		add(COROUTINES_CORE)
		add(COROUTINES_ANDROID)
	}

	const val MOCKK = "io.mockk:mockk:${BuildDependenciesVersions.MOCKK}"
	const val JUNIT = "junit:junit:${BuildDependenciesVersions.JUNIT}"
	const val COROUTINES_CORE_TEST =
		"org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.COROUTINES}"
	const val COROUTINES_TEST =
		"org.jetbrains.kotlinx:kotlinx-coroutines-test:${BuildDependenciesVersions.COROUTINES_TEST}"
	const val JUPITER =
		"org.junit.jupiter:junit-jupiter-api:${BuildDependenciesVersions.JUPITER}"

	const val ANDROIDX_JUNIT =
		"androidx.test.ext:junit:${BuildDependenciesVersions.ANDROIDX_JUNIT}"
	const val ESPRESSO =
		"androidx.test.espresso:espresso-core:${BuildDependenciesVersions.ESPRESSO}"

	// common
	const val TIMBER =
		"com.jakewharton.timber:timber:${BuildDependenciesVersions.TIMBER}"

	const val KTLINT =
		"com.pinterest:ktlint:${BuildDependenciesVersions.KTLINT}"

	// detekt
	const val DETEKT =
		"io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${AppConfig.detektVersion}"



}
