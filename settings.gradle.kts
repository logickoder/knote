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
rootProject.name = "Synote"
include(":app")
include(":features")
include(":features:login")
include(":features:notes")
include(":features:edit-note")
include(":common")
include(":common:ui")
