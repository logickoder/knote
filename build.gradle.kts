// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")

        val kotlin = libs.versions.kotlin.get()
        val hilt = libs.versions.hilt.get()
        classpath(kotlin("gradle-plugin", version = kotlin))
        classpath(kotlin("serialization", version = kotlin))
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt")
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

/**
 * Apply android configuration settings that are shared across all modules.
 */
fun PluginContainer.android(project: Project) {

    fun com.android.build.gradle.BaseExtension.android(project: Project) {

        compileSdkVersion(33)
        val name = if (project.name.equals("app", true)) {
            ""
        } else ".${project.name}"
        namespace = "dev.logickoder.synote$name"

        defaultConfig.apply {
            minSdk = 21
            targetSdk = 33
            versionCode = 1
            versionName = "1.0"
            applicationId = "dev.logickoder.synote"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes.apply {
            maybeCreate("release").apply {
                isMinifyEnabled = true
                signingConfig = signingConfigs.getByName("debug")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions.apply {
            // Flag to enable support for the new language APIs
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.AppExtension>()
                    .apply {
                        android(project)
                    }
            }
            is com.android.build.gradle.LibraryPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        android(project)
                    }
            }
        }
    }
}

subprojects {
    project.plugins.android(project)

    apply(plugin = "com.android.application")
    apply(plugin = "kotlin-android")
    apply(plugin = "kotlinx-serialization")
    apply(plugin = "kotlin-parcelize")

    dependencies {
        val coreLibraryDesugaring by configurations
        val implementation by configurations
        // Core
        coreLibraryDesugaring(rootProject.libs.core.java8)
        implementation(rootProject.libs.core)
        implementation(rootProject.libs.core.appcompat)
        implementation(rootProject.libs.core.constraintlayout)
        implementation(rootProject.libs.core.material)
        // Kotlinx serialization
        implementation(rootProject.libs.kotlinx.serialization.ktor)
        implementation(rootProject.libs.kotlinx.serialization.json)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
        }
    }
}