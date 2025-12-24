import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
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
  id("org.jlleitschuh.gradle.ktlint")
}

java {
  sourceCompatibility = JavaVersion.VERSION_25
  targetCompatibility = JavaVersion.VERSION_25
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
  testImplementation(Libraries.kotestJunit5)
  testImplementation(Libraries.kotestProperty)
}

kotlin {
  jvmToolchain(25)
  sourceSets.all {
    languageSettings {
      languageVersion = "2.3"
    }
  }
  compilerOptions {
    jvmTarget = JvmTarget.JVM_25
    languageVersion.set(KotlinVersion.KOTLIN_2_3)
    apiVersion.set(KotlinVersion.KOTLIN_2_3)
    progressiveMode = true
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

ktlint {
  android = true
  ignoreFailures = false
  enableExperimentalRules = true
  filter {
    include("**/kotlin/**")
    exclude("**/generated/**")
    exclude("**/test/**")
  }
}
