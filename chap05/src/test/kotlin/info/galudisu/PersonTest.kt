package info.galudisu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PersonTest {

  @Test
  fun `searching through a collection using a lambda`() {
    val people = listOf(Person("Alice", 27), Person("Bob", 31), Person("Carol", 31))
    val oldestGuy = people.maxByOrNull { it.age }
    assertEquals(findTheOldest(people), oldestGuy)
    val oldestLambdaGuy = people.maxByOrNull { p: Person -> p.age }
    assertEquals(findTheOldest(people), oldestLambdaGuy)

    val canBeInClub27 = { p: Person -> p.age <= 27 }
    assertFalse(people.all(canBeInClub27))
    assertTrue(people.any(canBeInClub27))
    assertEquals(1, people.count(canBeInClub27))
  }

  @Test
  fun `eager(collection) and lazy(sequence)`() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31), Person("Charles", 31), Person("Dan", 21))
    assertEquals(listOf("Bob", "Dan"), people.asSequence().map(Person::name).filter { it.length < 4 }.toList())
  }
}

