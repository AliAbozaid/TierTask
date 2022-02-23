package plugins

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

apply<DetektPlugin>()

configure<DetektExtension> {
    source = project.files(
        "src/main/kotlin", "app/src/main/kotlin",
        "feature/map/src/main/kotlin",
        "common/src/main/kotlin"
    )
    config = files("$rootDir/.detekt/detekt.yml")
    parallel = true
    ignoredBuildTypes = listOf("release")
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml {
            required.set(true)
            outputLocation.set(file("build/reports/detekt.xml"))
        }
        html {
            required.set(true)
            outputLocation.set(file("build/reports/detekt.html"))
        }
    }
}
