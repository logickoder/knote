plugins {
    id(libs.plugins.ksp.get().pluginId).version(libs.plugins.ksp.get().version.requiredVersion)
}

dependencies {
    api(project(":common:auth-api"))
    api(project(":common:notes-api"))
    // Firebase Firestore
    implementation(libs.firebase.firestore)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}