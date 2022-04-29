package info.galudisu

class CountingSet<T>(private val innerSet: MutableCollection<T> = HashSet()) :
  MutableCollection<T> by innerSet { // Delegates the MutableCollection implementation to innerSet
  var objectsAdded = 0

  override fun add(element: T): Boolean {  // no delegate
    objectsAdded++
    return innerSet.add(element)
  }

  override fun addAll(elements: Collection<T>): Boolean {  // no delegate
    objectsAdded += elements.size
    return innerSet.addAll(elements)
  }
}
