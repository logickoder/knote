plugins {
    id("com.google.gms.google-services")
}

dependencies {
    implementation(project(":common:auth-impl"))
    implementation(project(":common:notes-impl"))
    implementation(project(":features:edit-note"))
    implementation(project(":features:login"))
    implementation(project(":features:notes"))
    implementation(project(":features:settings"))
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
    // Preferences datastore
    implementation(rootProject.libs.datastore)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}