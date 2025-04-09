@file:Suppress("UnstableApiUsage")

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("androidx.navigation.safeargs.kotlin")
  id("com.google.devtools.ksp")
  id("kotlin-parcelize")
}

android {
  namespace = "com.neu.mobileapplicationdevelopment202430"
  compileSdk = 34


  defaultConfig {
    applicationId = "com.neu.mobileapplicationdevelopment202430"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    debug {}
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions { jvmTarget = "1.8" }
  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.5.2" }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
  buildToolsVersion = "34.0.0"
  buildFeatures { viewBinding = true }
}

dependencies {
  implementation ("androidx.paging:paging-runtime-ktx:3.3.5")
  implementation ("androidx.paging:paging-compose:3.3.5")
  implementation ("androidx.work:work-runtime-ktx:2.8.1")

  implementation("androidx.cardview:cardview:1.0.0")
  implementation("androidx.fragment:fragment-ktx:1.8.2")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
  implementation("androidx.navigation:navigation-compose:2.7.7")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.camera:camera-camera2:1.4.0-rc01")
  implementation("androidx.camera:camera-lifecycle:1.4.0-rc01")
  implementation("androidx.camera:camera-view:1.4.0-rc01")
  implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
  implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
  implementation("androidx.work:work-runtime-ktx:2.9.1")
  implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.compose.material3:material3-android:1.3.1")
    debugImplementation("androidx.fragment:fragment-testing:1.8.2")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
  androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")

  // jetpack
  val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  implementation("androidx.compose.material:material:1.6.8")
  implementation("androidx.compose.foundation:foundation:1.6.8")
  implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
  implementation("androidx.activity:activity-compose:1.9.1")

  implementation("androidx.compose.ui:ui")

  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")

  androidTestImplementation("androidx.compose.ui:ui-test-junit4")

  implementation("androidx.compose.material:material-icons-core")
  implementation("androidx.compose.material:material-icons-extended")
  implementation("androidx.compose.material3:material3-window-size-class")

  implementation("androidx.activity:activity-compose:1.9.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
  implementation("androidx.compose.runtime:runtime-livedata")
  implementation("io.coil-kt:coil-compose:2.4.0")
  val room_version = "2.6.1"

  implementation("androidx.room:room-runtime:$room_version")
  ksp("androidx.room:room-compiler:$room_version")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  //noinspection GradleDependencyb
  implementation("com.squareup.okhttp3:okhttp:4.9.1")

  // optional - Kotlin Extensions and Coroutines support for Room
  implementation("androidx.room:room-ktx:$room_version")

  // optional - Test helpers
  testImplementation("androidx.room:room-testing:$room_version")

  // optional - Paging 3 Integration
  implementation("androidx.room:room-paging:$room_version")

  // Dependencies for local unit tests
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.hamcrest:hamcrest-all:1.3")
  testImplementation("androidx.arch.core:core-testing:2.2.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

  // Dependencies for Android instrumented unit tests
  androidTestImplementation("junit:junit:4.13.2")
  androidTestImplementation("org.mockito:mockito-core:3.4.6")
  androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

  // rxjava
  implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
  implementation("io.reactivex.rxjava3:rxjava:3.1.5")
  implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
}
