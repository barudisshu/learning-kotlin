package info.galudisu

import kotlin.reflect.KClass

class ClassInfoCache {
  private val cacheData = mutableMapOf<KClass<*>, ClassInfo<*>>()
  @Suppress("UNCHECKED_CAST")
  operator fun <T : Any> get(cls: KClass<T>): ClassInfo<T> =
    cacheData.getOrPut(cls) { ClassInfo(cls) } as ClassInfo<T>
}
