package info.galudisu

object Lambda {
  fun alphabet1(): String =
    with(StringBuilder()) {
      for (letter in 'A'..'Z') {
        append(letter)
      }
      append("\nNow I know the alphabet!")
      toString()
    }

  fun alphabet2(): String =
    StringBuilder().apply {
      for (letter in 'A'..'Z') {
        append(letter)
      }
      append("\nNow I know the alphabet!")
    }.toString()
}
