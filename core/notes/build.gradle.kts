plugins {
    alias(libs.plugins.ksp)
}

android {
    defaultConfig.apply {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    api(project(":core:auth-api"))
    api(project(":core:notes-api"))
    // Firebase Firestore
    implementation(libs.firebase.firestore)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    // Worker
    implementation(libs.worker)
    implementation(libs.worker.testing)
    implementation(libs.worker.hilt)
    kapt(libs.worker.hilt.compiler)
}