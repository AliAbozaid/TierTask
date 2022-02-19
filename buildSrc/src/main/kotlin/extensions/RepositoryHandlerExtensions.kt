package extensions

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

/**
 * Adds all default repositories used to access to the different declared dependencies
 */

fun RepositoryHandler.applyDefault() {

	google()
	mavenCentral()
	maven("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://plugins.gradle.org/m2/")

	gradlePluginPortal()
	mavenCentral()
}
