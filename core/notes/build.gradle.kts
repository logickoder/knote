plugins {
    alias(libs.plugins.ksp)
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