// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

  val kotlin_version by extra { "1.6.10" }
  val compileSdkVersion by extra { 31 }
  val targetSdkVersion by extra { compileSdkVersion }
  val minSdkVersion by extra { 21 }
  val compose_version by extra { "1.1.1" }

  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:7.0.4")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}
