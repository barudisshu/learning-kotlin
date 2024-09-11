package info.galudisu

import java.io.BufferedReader

data class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

data class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)

data class Company(val name: String, val address: Address?)

data class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
  return company?.address?.country ?: "Unknown" // Several safe-call operators can be in a chain.
}

fun readNumbers(reader: BufferedReader): List<Int?> {
  val result = ArrayList<Int?>()
  for (line in reader.lineSequence()) {
    try {
      val number = line.toInt()
      result.add(number)
    } catch (e: NumberFormatException) {
      result.add(null)
    }
  }
  return result
}

fun <T> copyElements(
  source: Collection<T>,
  target: MutableCollection<T>,
) {
  for (item in source) {
    target.add(item)
  }
}
