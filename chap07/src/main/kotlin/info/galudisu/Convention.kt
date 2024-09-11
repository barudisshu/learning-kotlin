package info.galudisu

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

open class PropertyChangeAware {
  protected val changeSupport = PropertyChangeSupport(this)

  fun addPropertyChangeListener(listener: PropertyChangeListener) {
    changeSupport.addPropertyChangeListener(listener)
  }

  fun removePropertyChangeListener(listener: PropertyChangeListener) {
    changeSupport.removePropertyChangeListener(listener)
  }
}

class Person(
  val name: String,
  age: Int,
  salary: Int,
) : PropertyChangeAware() {
  private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
    changeSupport.firePropertyChange(prop.name, oldValue, newValue)
  }
  var age: Int by Delegates.observable(age, observer)
  var salary: Int by Delegates.observable(salary, observer)
}

class Contact {
  private val attributes = hashMapOf<String, String>()

  fun setAttribute(
    attrName: String,
    value: String,
  ) {
    attributes[attrName] = value
  }

  val name: String by attributes
}

object Users : IntIdTable() {
  val name = varchar("name", length = 50).index()
  val age = integer("age")
}

class User(
  id: EntityID<Int>,
) : IntEntity(id) {
  var name: String by Users.name
  var age: Int by Users.age
}
