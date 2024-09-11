object Libraries {
  // Classpath Gradle Plugin
  const val classpathKotlinGradle =
    "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"

  // Core
  const val kotlinStdLib =
    "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"

  // Coroutines
  const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES_VERSION}"
  const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES_VERSION}"

  // json object
  const val jacksonModule = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.JACKSON_MODULE_VERSION}"

  // log4j2
  const val log4j2Api = "org.apache.logging.log4j:log4j-api:${Versions.LOG4J2_VERSION}"
  const val log4j2Core = "org.apache.logging.log4j:log4j-core:${Versions.LOG4J2_VERSION}"
  const val log4j2Impl = "org.apache.logging.log4j:log4j-slf4j-impl:${Versions.LOG4J2_VERSION}"

  // exposed
  const val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.EXPOSED_VERSION}"
  const val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.EXPOSED_VERSION}"
  const val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED_VERSION}"

  // test
  const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.JUPITER_VERSION}"
  const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.JUPITER_VERSION}"
  const val jupiterPlatform = "org.junit.platform:junit-platform-runner:${Versions.PLATFORM_VERSION}"

  // mockito
  const val mockitoCore = "org.mockito:mockito-core:${Versions.MOCKITO_VERSION}"
  const val mockitoJupiter = "org.mockito:mockito-junit-jupiter:${Versions.MOCKITO_VERSION}"
  const val mockitoInline = "org.mockito:mockito-inline:${Versions.MOCKITO_VERSION}"

  const val mockk = "io.mockk:mockk:${Versions.MOCKK_VERSION}"
}
