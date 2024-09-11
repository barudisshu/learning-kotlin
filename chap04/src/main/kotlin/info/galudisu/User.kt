package info.galudisu

class User(private val name: String) {
  var address: String = "unspecified"
    set(value) {
      println(
        """
        Address was changed for $name:
        "$field" -> "$value".
        """.trimIndent(),
      ) // Reads the backing field value
      field = value // Updates the backing field value
    }
}
