package info.galudisu

data class Person(val name: String, val age: Int)

fun findTheOldest(people: List<Person>): Person? {
  var maxAge = 0
  var theOldest: Person? = null
  for (person in people) {
    if (person.age > maxAge) {
      maxAge = person.age
      theOldest = person
    }
  }
  return theOldest
}
