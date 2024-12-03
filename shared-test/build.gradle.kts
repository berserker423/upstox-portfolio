plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.shared_test"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":app"))

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // JUnit 4
    implementation("junit:junit:4.13.2")

    // AndroidX Testing
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.test.ext:junit:1.1.5")// For JUnit extensions
    implementation("androidx.test:rules:1.5.0")

    // Hilt Dependencies
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("com.google.dagger:hilt-android-testing:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
}