plugins {
  kotlin("jvm")
  idea
  java
  application
}

dependencies {
  implementation(Libraries.kotlinStdLib)
  implementation(Libraries.log4j2Api)
  implementation(Libraries.log4j2Core)
  implementation(Libraries.log4j2Impl)
  testImplementation(kotlin("test"))
  testImplementation(Libraries.jupiterApi)
  testImplementation(Libraries.jupiterEngine)
  testImplementation(Libraries.jupiterPlatform)
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}
