plugins {
  kotlin("jvm")
  idea
  java
  application
}

idea {
  module.isDownloadJavadoc = true
  module.isDownloadSources = true
}

dependencies {
  implementation(Libraries.kotlinStdLib)
  implementation(Libraries.kotlinCoroutinesCore)
  implementation(Libraries.log4j2Api)
  implementation(Libraries.log4j2Core)
  implementation(Libraries.log4j2Impl)
  testImplementation(kotlin("test"))
  testImplementation(Libraries.kotlinCoroutinesTest)
  testImplementation(Libraries.jupiterApi)
  testImplementation(Libraries.jupiterEngine)
  testImplementation(Libraries.jupiterPlatform)
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    showStandardStreams = true
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    events("standardOut", "started", "passed", "skipped", "failed")
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}
