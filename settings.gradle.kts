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
rootProject.name = "kNote"
include(":app")
include(":core:auth-api")
include(":core:auth-impl")
include(":core:model")
include(":core:notes-api")
include(":core:notes-impl")
include(":core:settings-api")
include(":core:ui")
include(":features:edit-note")
include(":features:login")
include(":features:notes")
include(":features:settings")
