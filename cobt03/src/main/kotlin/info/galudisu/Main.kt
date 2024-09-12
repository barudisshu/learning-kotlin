package info.galudisu

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Main {

  @OptIn(DelicateCoroutinesApi::class)
  @JvmStatic
  fun main(args: Array<String>) {
    (1..10_000).forEach {
      GlobalScope.launch {
        val threadName = Thread.currentThread().name
        println("$it printed on thread $threadName")
      }
    }
    Thread.sleep(1000)
  }
}
