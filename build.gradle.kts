// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply{
        set("compose_version", "1.2.1")
        set("kotlin_version", "1.7.10")
        set("ktor_version", "2.1.0")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")

        val kotlinVersion = rootProject.extra.get("kotlin_version") as String
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}