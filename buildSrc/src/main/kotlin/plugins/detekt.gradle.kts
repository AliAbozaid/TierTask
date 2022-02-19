package plugins

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

apply<DetektPlugin>()

configure<DetektExtension> {
    input = project.files(
        "src/main/kotlin", "app/src/main/java",
        "core/src/main/java",
        "network/src/main/java"
    )
    config = files("$rootDir/.detekt/detekt.yml")
    parallel = true
    ignoredBuildTypes = listOf("release")
    ignoredFlavors = listOf("staging", "production")
    reports {
        xml {
            enabled = true
            destination = file("build/reports/detekt.xml")
        }
        html {
            enabled = true
            destination = file("build/reports/detekt.html")
        }
        txt {
            enabled = true
            destination = file("build/reports/detekt.txt")
        }
        sarif {
            enabled = true
            destination = file("build/reports/detekt.sarif")
        }
        custom {
            reportId = "CustomJsonReport"
            destination = file("build/reports/detekt.json")
        }
    }
}
