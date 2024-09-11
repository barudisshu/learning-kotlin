package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConventionTest {
  @Test
  fun `Using Delegates's observable to implement property change notification`() {
    val p = Person("Dmitry", 34, 2000)
    p.addPropertyChangeListener { event ->
      println("Property ${event.propertyName} changed " + "from ${event.oldValue} to ${event.newValue}")
    }

    p.age = 35
    p.salary = 2100

    assertEquals(35, p.age)
    assertEquals(2100, p.salary)
  }

  @Test
  fun `Using a delegated property which stores its value in a map`() {
    val c = Contact()
    val data = mapOf("name" to "Dmitry", "company" to "JetBrains")
    for ((attrName, value) in data) {
      c.setAttribute(attrName, value)
    }
    assertEquals("Dmitry", c.name)
  }
}
