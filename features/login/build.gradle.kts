dependencies {
    api(project(":core:auth-api"))
    api(project(":core:ui"))

    // Compose
    implementation(libs.compose.activity)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.junit)

    // Firebase Auth
    implementation(libs.firebase.auth)
    // Google Sign In
    implementation(libs.firebase.auth.google)
}
