
plugins {
  id("kotlin-library-conventions")
}

dependencies {
  // exposedCore
  implementation(Libraries.exposedCore)
  implementation(Libraries.exposedDao)
  implementation(Libraries.exposedJdbc)
}
