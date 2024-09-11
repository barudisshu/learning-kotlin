plugins {
  `kotlin-dsl` // <1>
}

repositories {
  gradlePluginPortal() // <2>
}

private val kotlinGradleVersion = "1.9.22"
private val ktlintVersion = "12.1.0"
private val spotbugsGradleVersion = "5.0.14"

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinGradleVersion")
  implementation("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")
  implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:$spotbugsGradleVersion")
}
