# Kotlin Study

- Basic theory
- Coroutines (corXX)

  1. Why we need coroutines as we already have RxJava/WebFlux, Vertx, Loom, Spring's `@Async`/`@EventListener`, Akka/Pekko?
    RxJava/WebFlux的问题：
      - 难于理解，采取了大量的stream operator难于学习和掌握！！
      - `doOnSubscribe`, `flatMap`, `map`, `onXXX` 的彼此堆叠，产生 不同类型的`stream` 以及大量的边缘案例，难于评估线程的真实使用情况；另外也难于重构
      - error/exception没有统一控制标准；异常可能会被捕获，也可能不被捕获；由于是函数式写法，不同程序员之间编写风格不一致
      - 编写单元测试又是难于达到预期；由于是基于Pub-Sub的backpressure模式，往往过渡依赖于retry机制
    Vertx的问题：
      -
  2. What's the difference between Coroutines and Fibers?
  3. Benchmark comparison?

- Functional program (cjoyXX)
- Enterprise Application Architecture(PoEXX)


## receive feature

- 1.9

## coroutines 精讲

[第一章: 什么是异步编程?](cobt01/README.adoc)

