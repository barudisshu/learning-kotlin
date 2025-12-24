plugins {
  `kotlin-dsl` // <1>
}

repositories {
  gradlePluginPortal() // <2>
}

private val kotlinGradleVersion = "2.3.0"
private val ktlintVersion = "14.0.1"
private val spotbugsGradleVersion = "6.4.8"

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinGradleVersion")
  implementation("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")
  implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:$spotbugsGradleVersion")
}
