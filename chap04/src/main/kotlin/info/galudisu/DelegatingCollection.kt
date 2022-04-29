package info.galudisu

class DelegatingCollection<T>(innerList: Collection<T> = ArrayList()) : Collection<T> by innerList
