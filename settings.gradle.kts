pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Recent Flics"
include(":app")
include(":core:designsystem")
include(":core:network")
include(":data:movies")
include(":feature:home")
include(":core:types")
include(":core:database")
include(":core:datastore")
include(":feature:movie")
