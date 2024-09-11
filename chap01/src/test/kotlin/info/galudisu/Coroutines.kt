package info.galudisu

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
class Coroutines {
  companion object {
    val logger: Logger = LoggerFactory.getLogger(Coroutines::class.java)

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
  }

  @BeforeEach
  fun setUp() {
    Dispatchers.setMain(mainThreadSurrogate)
  }

  @AfterEach
  fun tearDown() {
    Dispatchers.resetMain()
    mainThreadSurrogate.close()
  }

  private suspend fun bathTime() {
    // Continuation = data structure stores all local context
    logger.info("going to the bathroom")
    delay(500L) // suspends/"blocks" the computation
    // Continuation restored here
    logger.info("Bath done, exiting")
  }

  @Test
  fun `suspend  coroutines`() {
    runBlocking {
      launch(Dispatchers.Main) {
        bathTime()
      }
    }
  }

  private suspend fun boilingWater() {
    logger.info("Boiling water")
    delay(1000L)
    logger.info("Water boiled")
  }

  private suspend fun sequentialMorningRoutine() {
    coroutineScope { // start a "context" for coroutines
      bathTime()
      // add more code, including suspend functions
      // parallel code here. all needs to finish before the scope is closed
    }
    coroutineScope {
      boilingWater()
    }
  }

  @Test
  fun `run one by one`() {
    runBlocking {
      launch(Dispatchers.Main) {
        sequentialMorningRoutine()
      }
    }
  }

  private suspend fun concurrentMorningRoutine() {
    coroutineScope {
      launch { bathTime() } // this coroutine is a CHILD of the coroutineScope
      launch { boilingWater() }
    }
  }

  @Test
  fun `run in concurrent`() {
    runBlocking {
      launch(Dispatchers.Main) {
        concurrentMorningRoutine()
      }
    }
  }

  // build blueprint
  private suspend fun eatMyBreakfast() {
    logger.info("Starting to eat")
    delay(500L)
    logger.info("Done with eat")
  }

  private suspend fun makeCoffee() {
    logger.info("Starting to make coffee")
    delay(500L)
    logger.info("Done with coffee")
  }

  private suspend fun morningRoutineWithCoffee() {
    coroutineScope {
      val bathTimeJob: Job = launch { bathTime() }
      val boilingWaterJob: Job = launch { boilingWater() }
      bathTimeJob.join()
      boilingWaterJob.join()
      launch { makeCoffee() }
    }
  }

  /** structured concurrency */
  private suspend fun morningRoutineWithcoffeeStructured() {
    coroutineScope {
      coroutineScope {
        // parallel jobs
        launch { bathTime() }
        launch { boilingWater() }
      }
      // both coroutines are done
      launch { makeCoffee() }
    }
  }

  private suspend fun preparingJavaCoffee(): String {
    logger.info("Starting to make coffee")
    delay(500L)
    logger.info("Done with coffee")
    return "Java coffee"
  }

  private suspend fun toastingBread(): String {
    logger.info("Starting to make breakfast")
    delay(1000L)
    logger.info("Toast is out!")
    return "Toasted bread"
  }

  /** return value fro coroutines */
  private suspend fun prepareBreakfast() {
    coroutineScope {
      val coffee = async { preparingJavaCoffee() } // Deferred = analogous to the Future[T], in a new thread
      val toast = async { toastingBread() }
      // semantic blocking
      val finalCoffee = coffee.await()
      val finalToast = toast.await()
      logger.info("I'm eating $finalToast and drinking $finalCoffee")
    }
  }

  @Test
  fun `suspend with return`() {
    runBlocking {
      launch(Dispatchers.Main) {
        prepareBreakfast()
      }
    }
  }

  // 1 - cooperative scheduling - coroutines yield manually
  private suspend fun workingHard() {
    logger.info("Working")
    // CPU-intensive computation

    while (true) {
      // do some hard code
    }
    delay(100L)
    logger.info("Work done")
  }

  private suspend fun takeABreak() {
    logger.info("Taking a break")
    delay(1000L)
    logger.info("Break done")
  }

  suspend fun workHardRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1) // force 1 thread
    coroutineScope {
      launch(dispatcher) {
        workingHard()
      }
      launch(dispatcher) {
        takeABreak()
      }
    }
  }

  //  @Test
  fun `never yield thread`() {
    runBlocking {
      launch(Dispatchers.Main) {
        workHardRoutine()
      }
    }
  }

  suspend fun workingNicely() {
    logger.info("Working")
    // CPU-intensive computation

    while (true) {
      delay(1000L) // give a chance to for the dispatcher to run another coroutine
    }
    delay(100L)
    logger.info("Work done")
  }

  suspend fun workNicelyRoutine() {
    val dispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(1) // force 1 thread
    coroutineScope {
      launch(dispatcher) {
        workingNicely()
      }
      launch(dispatcher) {
        takeABreak()
      }
    }
  }

  //  @Test
  fun `single thread in both coroutines never yield`() {
    runBlocking {
      launch(Dispatchers.Main) {
        workNicelyRoutine()
      }
    }
  }

  val simpleDispatcher = Dispatchers.Default // "normal code" = short code or yielding coroutines
  val blockingDispatcher = Dispatchers.IO // blocking code (e.g. DB connections, long-running computations)
  val customDispatcher =
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
      .asCoroutineDispatcher() // on top of your own thread pool

  // cancellation, cancel a coroutine while it is running

  suspend fun forgettingFriendBirthdayRoutine() {
    coroutineScope {
      val workingJob = launch { workingNicely() }
      launch {
        delay(2000L) // after 2s I remember I have a birthday today
        workingJob.cancel() // sends a SIGNAL to the coroutine to cancel, cancellation happens at first yielding point
        workingJob.join() // you are sure that the coroutine has been cancelled
        logger.info("I forgot my friend's birthday! Buying a present now!")
      }
    }
  }

  @Test
  fun `cancellation yield job`() {
    runBlocking {
      launch(Dispatchers.Main) {
        forgettingFriendBirthdayRoutine()
      }
    }
  }

  // if a coroutine doesn't yield, it can't be cancelled
  suspend fun forgettingFriendBirthdayRoutineUncancelable() {
    coroutineScope {
      val workingJob = launch { workingHard() }
      launch {
        delay(2000L) // after 2s I remember I have a birthday today
        logger.info("Trying to stop working... ")
        workingJob.cancel() // sends a SIGNAL to the coroutine to cancel, cancellation happens at first yielding point (NEVER)
        workingJob.join() // you are sure that the coroutine has been cancelled
        logger.info("I forgot my friend's birthday! Buying a present now!")
      }
    }
  }

  // resources
  class Desk : AutoCloseable {
    init {
      logger.info("Starting to work on this desk")
    }

    override fun close() {
      logger.info("Cleaning up the desk")
    }
  }

  suspend fun forgettingFriendBirthdayRoutineWithResource() {
    val desk = Desk()
    coroutineScope {
      val workingJob =
        launch {
          // use the resource here
          desk.use { _ -> // this resource will be closed upon completion of the coroutine
            workingNicely()
          }
        }
      // can also define your own "cleanup" code in case of completion
      workingJob.invokeOnCompletion { _: Throwable? -> // callback
        // can handle completion and cancellation differently, depending on the exception
        logger.info("Make sure I talk to my colleagues that I'll be out for 30 mins")
      }

      launch {
        delay(2000L) // after 2s I remember I have a birthday today
        logger.info("Trying to stop working... ")
        workingJob.cancel() // sends a SIGNAL to the coroutine to cancel, cancellation happens at first yielding point (NEVER)
        workingJob.join() // you are sure that the coroutine has been cancelled
        logger.info("I forgot my friend's birthday! Buying a present now!")
      }
    }
  }

  @Test
  fun `coroutines with resource`() {
    runBlocking {
      launch(Dispatchers.Main) {
        forgettingFriendBirthdayRoutineWithResource()
      }
    }
  }

  // cancellation propagates to child coroutines
  suspend fun drinkWater() {
    while (true) {
      logger.info("Drinking water")
      delay(1000L)
    }
  }

  suspend fun forgettingFriendBirthdayRoutineStayHydrated() {
    coroutineScope {
      val workingJob =
        launch {
          // child coroutines
          launch { workingNicely() }
          launch { drinkWater() }
        }
      launch {
        delay(2000L) // after 2s I remember I have a birthday today
        workingJob.cancel() // sends a SIGNAL to the coroutine to cancel, cancellation happens at first yielding point
        workingJob.join() // you are sure that the coroutine has been cancelled
        logger.info("I forgot my friend's birthday! Buying a present now!")
      }
    }
  }

  @Test
  fun `cancel parent's coroutine will also cancel child's coroutine`() {
    runBlocking {
      launch(Dispatchers.Main) {
        forgettingFriendBirthdayRoutineStayHydrated()
      }
    }
  }

  // coroutines context
  suspend fun asynchronousGreeting() {
    coroutineScope {
      launch(CoroutineName("Greeting coroutine") + Dispatchers.Default /* these two =  CoroutineContext */) {
        logger.info("Hello, everyone!")
      }
    }
  }

  @Test
  fun `named coroutine context`() {
    runBlocking {
      launch(Dispatchers.Main) {
        asynchronousGreeting()
      }
    }
  }

  suspend fun contextInheritance() {
    coroutineScope {
      launch(CoroutineName("Greeting Coroutine")) {
        logger.info("[parent coroutine] Hello!")
        launch(CoroutineName("Child Greeting Coroutine")) { // coroutine context will be inherited here
          logger.info("[child coroutine] Hi there!")
        }
        delay(200)
        logger.info("[parent coroutine] Hi again from parent!")
      }
    }
  }

  @Test
  fun `coroutine inheritance`() {
    runBlocking {
      launch(Dispatchers.Main) {
        contextInheritance()
      }
    }
  }
}
