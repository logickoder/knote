pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "synote"
include(":app")
include(":features:login")
include(":features:notes")
include(":features:edit-note")
include(":common:model")
include(":common:ui")
include(":common:notes-api")
include(":common:notes-impl")
include(":common:auth-api")
include(":common:auth-impl")
