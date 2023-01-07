dependencies {
    api(project(":core:auth-api"))
    // Firebase Auth
    implementation(libs.firebase.auth)
    // Preferences datastore
    implementation(libs.datastore)
}