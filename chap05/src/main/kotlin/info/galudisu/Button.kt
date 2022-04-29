package info.galudisu

interface Button {
  fun onClick(handle: () -> Int)
}

fun tryToCountButtonClicks(button: Button): Int {
  var clicks = 0
  button.onClick { clicks++ }
  return clicks
}
