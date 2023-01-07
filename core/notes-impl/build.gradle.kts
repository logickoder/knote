plugins {
    id(libs.plugins.ksp.get().pluginId).version(libs.plugins.ksp.get().version.requiredVersion)
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
}