// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("com.google.gms:google-services:4.3.14")

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

val features = listOf(
    "edit-note", "notes", "login", "app", "ui", "notes-impl", "auth-impl", "settings"
)

@Suppress("UnstableApiUsage")
fun Project.feature() {

    fun com.android.build.gradle.BaseExtension.android(project: Project, isApp: Boolean) {

        compileSdkVersion(33)
        val name = if (isApp) {
            ""
        } else ".${project.name.replace("-", "_")}"

        namespace = "dev.logickoder.knote$name"

        resourcePrefix = "${name.replace(".", "")}_"

        defaultConfig.apply {
            minSdk = 21
            targetSdk = 33
            versionCode = 1
            versionName = "1.0"
            if (isApp) {
                applicationId = "dev.logickoder.knote"
            }

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

        buildFeatures.apply {
            compose = true
        }

        composeOptions.apply {
            kotlinCompilerExtensionVersion = rootProject.libs.versions.compose.compiler.get()
        }

        packagingOptions.apply {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    fun PluginContainer.setup(isApp: Boolean) = whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.AppExtension>()
                    .apply {
                        android(project, isApp)
                    }
            }
            is com.android.build.gradle.LibraryPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        android(project, isApp)
                    }
            }
        }
    }

    fun Project.dependencies(isApp: Boolean) {

        if (isApp) {
            apply(plugin = "com.android.application")
        } else apply(plugin = "com.android.library")
        apply(plugin = "kotlin-android")
        apply(plugin = "kotlinx-serialization")
        apply(plugin = "kotlin-parcelize")
        apply(plugin = "kotlin-kapt")
        apply(plugin = "dagger.hilt.android.plugin")

        dependencies {
            val coreLibraryDesugaring by configurations
            val implementation by configurations
            val kapt by configurations
            // Core
            coreLibraryDesugaring(rootProject.libs.core.java8)
            implementation(rootProject.libs.core)
            implementation(rootProject.libs.core.appcompat)
            implementation(rootProject.libs.core.constraintlayout)
            implementation(rootProject.libs.core.material)
            // Hilt
            implementation(rootProject.libs.hilt.android)
            kapt(rootProject.libs.hilt.compiler)
            // JUnit
            implementation(rootProject.libs.junit4)
            // Kotlinx immutable
            implementation(rootProject.libs.kotlinx.immutable)
            // Kotlinx serialization
            implementation(rootProject.libs.kotlinx.serialization.ktor)
            implementation(rootProject.libs.kotlinx.serialization.json)
            // Lifecycle
            implementation(rootProject.libs.lifecycle.runtime)
            implementation(rootProject.libs.lifecycle.viewmodel)
            implementation(rootProject.libs.lifecycle.viewmodel.compose)
            // Napier
            implementation(rootProject.libs.napier)
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
                jvmTarget = "11"
            }
        }
    }

    plugins.apply {
        val isApp = project.name.equals("app", true)
        setup(isApp)
        dependencies(isApp)
    }
}


subprojects {
    if (name in features) feature()
}