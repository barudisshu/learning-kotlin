object Libraries {

  // Classpath Gradle Plugin
  const val classpathKotlinGradle =
    "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"

  // Core
  const val kotlinStdLib =
    "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"

  // Coroutines
  const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"

  // json object
  const val jacksonModule = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonModuleVersion}"

  // log4j2
  const val log4j2Api = "org.apache.logging.log4j:log4j-api:${Versions.log4j2Version}"
  const val log4j2Core = "org.apache.logging.log4j:log4j-core:${Versions.log4j2Version}"
  const val log4j2Impl = "org.apache.logging.log4j:log4j-slf4j-impl:${Versions.log4j2Version}"

  // exposed
  const val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
  const val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
  const val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"

  // test
  const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.JupiterVersion}"
  const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.JupiterVersion}"
  const val jupiterPlatform = "org.junit.platform:junit-platform-runner:${Versions.PlatformVersion}"
}
