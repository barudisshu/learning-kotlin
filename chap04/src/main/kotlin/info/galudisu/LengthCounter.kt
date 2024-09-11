package info.galudisu

class LengthCounter {
  var counter: Int = 0
    private set // You can't change this property outside the class.

  fun addWord(word: String) {
    counter += word.length
  }
}
