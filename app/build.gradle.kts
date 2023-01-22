val compose_version: String by rootProject.extra

plugins {
  id("com.android.application")
  id("kotlin-android")
}
android {
  val compileSdkVersion: Int by rootProject.extra
  val minSdkVersion: Int by rootProject.extra
  val targetSdkVersion: Int by rootProject.extra
  compileSdkVersion(compileSdkVersion)
  defaultConfig {
    applicationId = "com.yourcompany.android.quizme"
    minSdk = minSdkVersion
    targetSdk = targetSdkVersion
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles (getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
//    compose(true)
  }
  composeOptions {
    kotlinCompilerExtensionVersion = compose_version
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  val kotlin_version: String by rootProject.extra
  // Kotlin
  implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}")

  // Support Libraries
  implementation("androidx.appcompat:appcompat:1.4.1")

  // SplashScreen compat library
  implementation("androidx.core:core-splashscreen:1.0.0-beta02")

  // Compose
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.compose.ui:ui:$compose_version")
  implementation("androidx.compose.material:material:$compose_version")
  implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
  implementation("androidx.activity:activity-compose:1.4.0")
  implementation("androidx.compose.runtime:runtime-livedata:1.2.0-alpha08")
  implementation("androidx.core:core-splashscreen:1.0.0-beta02")

  // Testing Dependencies
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
  debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
}
