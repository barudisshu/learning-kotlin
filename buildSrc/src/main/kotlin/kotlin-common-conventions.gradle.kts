import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
  maven {
    url = uri("arm.lmera.ericsson.se")
  }
  gradlePluginPortal()
  mavenLocal()
  google()
  mavenCentral()
}

plugins {
  java
  idea
  checkstyle
  id("com.github.spotbugs")
  id("org.jetbrains.kotlin.jvm")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  // Use the Kotlin JDK standard library
  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect", embeddedKotlinVersion))
  // KotlinX
  implementation(Libraries.kotlinCoroutinesCore)
  implementation(Libraries.kotlinCoroutinesTest)
  // Common Test
  implementation(Libraries.kotlinStdLib)
  implementation(Libraries.jacksonModule)
  // log4j2
  implementation(Libraries.log4j2Api)
  implementation(Libraries.log4j2Core)
  implementation(Libraries.log4j2Impl)

  testImplementation(kotlin("test"))

  testImplementation(Libraries.jupiterApi)
  testImplementation(Libraries.jupiterEngine)
  testImplementation(Libraries.jupiterPlatform)

  // mockito
  testImplementation(Libraries.mockitoCore)
  testImplementation(Libraries.mockitoJupiter)
  testImplementation(Libraries.mockitoInline)
  testImplementation(Libraries.mockk)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
    freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    kotlin {
      sourceSets {
        all {
          languageSettings.optIn("kotlin.RequiresOptIn")
        }
      }
    }
  }
}

kotlin {
  jvmToolchain(17)
  sourceSets.all {
    languageSettings {
      languageVersion = "2.0"
    }
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
    exceptionFormat = TestExceptionFormat.FULL
  }
}

idea {
  module.isDownloadJavadoc = true
  module.isDownloadSources = true
}
