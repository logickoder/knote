plugins {
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id(libs.plugins.ksp.get().pluginId).version(libs.plugins.ksp.get().version.requiredVersion)
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core
    implementation(libs.core.splashscreen)
    // Accompanist
    implementation(libs.accompanist.refresh)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.systemuicontroller)
    // Appyx Navigation
    implementation(rootProject.libs.appyx)
    // Compose
    implementation(libs.compose.activity)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.junit)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.contentnegotiation)
    // Lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)
    // Napier
    implementation(libs.napier)
    // Preferences datastore
    implementation(rootProject.libs.datastore)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}