buildscript {
  repositories {
    gradlePluginPortal()
    mavenLocal()
    google()
    mavenCentral()
  }

  dependencies {
    classpath(Libraries.classpathKotlinGradle)
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {

  group = "info.galudisu"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}

plugins {
  idea
  id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
  debug.set(true)
}

idea {
  module.isDownloadJavadoc = true
  module.isDownloadSources = true
}
