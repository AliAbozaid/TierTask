package plugins

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint(AppDependencies.KTLINT) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

tasks {
    register<JavaExec>("ktlint") {
        group = BuildTasksGroups.VERIFICATION
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "src/**/*.kt")
    }
	ktlint.exclude(group = "test/src/**/*.kt")
    ktlint.exclude(group ="*/test/.*")
    ktlint.exclude(group ="*Test.kt")


    register<JavaExec>("ktlintFormat") {
        group = BuildTasksGroups.FORMATTING
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "-F", "src/**/*.kt")
    }
}
