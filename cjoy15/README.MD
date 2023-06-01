Subject: Contravariance
=======================

~~~
Why contravariance is hard to understand?
~~~

## The problem

## How

## The Stack Unsafe

## High-kind function program

## The IO Monad

## The difference between Scala(3) and Kotlin

|               | Kotlin            | Scala            | description                                                                                   | Why                                                                                    |
|:--------------|:------------------|:-----------------|:----------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------|
| Covariant     | `Producer<out T>` | `Producer[+T]`   | Subtyping for the class is **preserved**: `Producer<Cat>` is a subtype of `Producer<Animal>`. | `T` only in **out** positions, use for **return** type.                                |
|               |                   |                  |                                                                                               |                                                                                        |
| Contravariant | `Consumer<in T>`  | `Consumer[-T]`   | Subtyping is **reversed**: `Consumer<Animal>` is a subtype of `Consumer<Cat>`.                | `T` only in **in** positions, use for parameter(receiver type) of the method/function. |
| Invariant     | `MutableList<T>`  | `MutableList[T]` | No way for subtyping.                                                                         | `T` in any position.                                                                   |

##
