plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
}

val composeVersion = rootProject.extra.get("compose_version") as String
val kotlinVersion = rootProject.extra.get("kotlin_version") as String

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "dev.logickoder.synote"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.schemaLocation" to "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // for using some java 8 classes like LocalDate with older versions of android
    //noinspection GradleDependency
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.5.0")

    implementation ("androidx.core:core-splashscreen:1.0.0")

    // Material Design
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("androidx.compose.material:material:$composeVersion")

    // Constraint Layout
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Icons
    implementation ("androidx.compose.material:material-icons-extended:$composeVersion")

    // Google Accompanist
    val accompanistVersion = "0.25.0"
    implementation ("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    implementation ("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    // Compose
    implementation ("androidx.activity:activity-compose:1.5.1")
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeVersion")

    // qrcode scanner
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") {
        isTransitive = false
    }
    implementation("com.google.zxing:core:3.4.1")

    // lifecycle
    val lifecycleVersion = "2.6.0-alpha01"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // preferences datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // gson
    implementation ("com.google.code.gson:gson:2.9.1")

    // retrofit
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:retrofit-converters:2.8.1")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Room
    val roomVersion = "2.4.3"
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$composeVersion")
}