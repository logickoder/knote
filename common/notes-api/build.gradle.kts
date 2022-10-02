plugins {
    id("java-library")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api(project(":common:model"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Kotlinx serialization
    implementation(rootProject.libs.kotlinx.serialization.json)
}