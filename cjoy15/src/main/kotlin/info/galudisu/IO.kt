package info.galudisu

interface Semigroup<A> {
  fun combine(x: A, y: A): A
}

interface Monoid<A> : Semigroup<A> {
  fun empty(): A
}

interface Kind<F, out T> {
  operator fun invoke(func: F): T
}

data class Tuple<A, B>(var a: A, var b: B) {
  companion object
}


sealed class Either<out L, out R> {
  data class Left<L>(val l: L) : Either<L, Nothing>()
  data class Right<R>(val r: R) : Either<Nothing, R>()

  inline fun <L, R, reified T> Either<L, R>.map(
    lft: (L) -> T,
    rgt: (R) -> T
  ): T =
    when (this) {
      is Left -> lft(l)
      is Right -> rgt(r)
    }

  inline fun <L, R, reified T> Either<L, R>.flatMap(
    lft: (L) -> Either<L, T>,
    rgt: (R) -> Either<R, T>
  ) = when (this) {
    is Left -> lft(l)
    is Right -> rgt(r)
  }
}

interface Functor<F> {
  fun <A, B> map(fa: Kind<F, A>, func: (A) -> B): Kind<F, B>
  fun <A, B> Kind<F, A>.map(func: (A) -> B): Kind<F, B> = map(this, func)
}

interface Semigroupal<F> {
  fun <A, B> product(fa: Kind<F, A>, fb: Kind<F, B>): Kind<F, Tuple<A, B>>
}

interface Apply<F> : Functor<F>, Semigroupal<F> {
  fun <A, B> ap(fa: Kind<F, A>, ff: Kind<F, (A) -> B>): Kind<F, B>
  override fun <A, B> product(fa: Kind<F, A>, fb: Kind<F, B>): Kind<F, Tuple<A, B>> =
    ap(fb, map(fa) { a -> { b -> Tuple(a, b) } })

  fun <A, B, C> mapN(fa: Kind<F, A>, fb: Kind<F, B>, ff: (Tuple<A, B>) -> C): Kind<F, C> =
    map(fa = product(fa, fb), func = ff)
}

interface Applicative<F> : Apply<F> {
  fun <A> pure(a: A): Kind<F, A>
  override fun <A, B> map(fa: Kind<F, A>, func: (A) -> B): Kind<F, B> = ap(fa, pure(func))
}

interface FlatMap<F> : Apply<F> {
  fun <A, B> flatMap(fa: Kind<F, A>, func: (A) -> Kind<F, B>): Kind<F, B>
  fun <A> flatten(ffa: Kind<F, Kind<F, A>>) = flatMap(ffa) { it }
  fun <A, B> tailRecM(a: A, func: (A) -> Kind<F, Either<A, B>>): Kind<F, B>
}

interface Monad<F> : FlatMap<F>, Applicative<F> {
  override fun <A, B> map(fa: Kind<F, A>, func: (A) -> B): Kind<F, B> = flatMap(fa) { pure(func(it)) }

  companion object {
    operator fun <F> invoke(monad: Monad<F>): Monad<F> = monad
  }
}

/**
 * Stack safe Monad
 */
sealed class Free<F, out A> {

  operator fun invoke(): Free<F, A> = invoke(this)

  @Suppress("UNCHECKED_CAST")
  operator fun invoke(free: Free<F, @UnsafeVariance A>): Free<F, A> =
    when (free) {
      // JVM Generic type erase, suppress unchecked_cast
      is FlatMapped<*, *, *> -> when (free.self) {
        is FlatMapped<*, *, *> -> {
          val c = free.self.self as Free<F, A>
          val f = free.self.func as ((A) -> Free<F, A>)
          val g = free.func as ((A) -> Free<F, A>)
          invoke(c.flatMap { f(it).flatMap(g) })
        }

        is Pure<*, *> -> {
          val a = free.self.a as A
          val func = free.func as ((A) -> Free<F, A>)
          invoke(func(a))
        }

        else -> free
      }

      else -> free
    }

  @Suppress("UNCHECKED_CAST")
  tailrec fun <F, A> Functor<F>.resume(free: Free<F, A>): Either<Kind<F, Free<F, A>>, A> = when(free) {
    is Pure -> Either.Right(free.a)
    is Suspend -> Either.Left(this.map(free.fa) { Pure(it) })
    is FlatMapped<*, *, *> -> when(free.self) {
      is Pure -> {
        val a = free.self.a as A
        val f = free.func as (A) -> Free<F, A>
        resume(f(a))
      }
      is Suspend -> TODO()
      is FlatMapped<*, *, *> -> TODO()
    }
  }

  internal class Pure<F, out A>(val a: A) : Free<F, A>()
  internal class Suspend<F, out A>(val fa: Kind<F, A>) : Free<F, A>()
  internal class FlatMapped<G, In, out Out>(val self: Free<G, In>, val func: (In) -> Free<G, Out>) : Free<G, Out>()

  fun <B> map(func: (A) -> B): Free<F, B> = flatMap { Pure(func(it)) }
  fun <B> flatMap(func: (A) -> Free<F, B>): Free<F, B> = FlatMapped(this, func)

  companion object {
    fun <F, A> pure(a: A): Free<F, A> = Pure(a)
    fun <F, A> liftM(fa: Kind<F, A>): Free<F, A> = Suspend(fa)
  }
}


/**
 * stack-safe IO
 */
sealed class IO<out A> {
  // object call as function
  operator fun invoke(): A = invoke(this)
  operator fun invoke(io: IO<@UnsafeVariance A>): A {
    @Suppress("UNCHECKED_CAST")
    tailrec fun invokeHelper(io: IO<A>): A = when (io) {
      is Pure -> io.value
      is Effect -> io.resume()
      else -> {
        val ct = io as FlatMapped<A, A>
        val sub = ct.sub
        val f = ct.f
        when (sub) {
          is Pure -> invokeHelper(f(sub.value))
          is Effect -> invokeHelper(f(sub.resume()))
          else -> {
            val ct2 = sub as FlatMapped<A, A>
            val sub2 = ct2.sub
            val f2 = ct2.f
            invokeHelper(sub2.flatMap { f2(it).flatMap(f) })
          }
        }
      }
    }
    return invokeHelper(io)
  }

  fun <B> map(f: (A) -> B): IO<B> = flatMap { Pure(f(it)) }

  @Suppress("UNCHECKED_CAST")
  fun <B> flatMap(f: (A) -> IO<B>): IO<B> = FlatMapped(this, f) as IO<B>

  class IORef<A>(private var value: A) {
    fun set(a: A): IO<A> {
      value = a
      return unit(a)
    }

    fun get(): IO<A> = unit(value)
    fun modify(f: (A) -> A): IO<A> = get().flatMap { a -> set(f(a)) }
  }

  internal class Pure<out A>(val value: A) : IO<A>()
  internal class Effect<out A>(val resume: () -> A) : IO<A>()
  internal class FlatMapped<A, out B>(val sub: IO<A>, val f: (A) -> IO<B>) : IO<A>()
  internal class Failure(val e: Throwable) : IO<Unit>()
  internal class Recover<out A>(self: IO<A>, handler: (Throwable) -> IO<A>) : IO<A>()
  internal class Async<out A>(k: ((Either<Throwable, A>) -> Unit) -> IO<Unit>) : IO<A>()

  companion object {
    val empty: IO<Unit> = IO.Effect { Unit }
    internal fun <A> unit(a: A): IO<A> = IO.Effect { a }

  }
}


/////////////////////////////////////////////////////////




