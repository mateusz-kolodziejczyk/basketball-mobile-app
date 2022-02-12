package org.mk.basketballmanager.models.datastores

interface DataStore<T> {
    fun findAll(): List<T>
    fun create(obj: T)
    fun update(obj: T)
    fun add(obj: T)
    fun delete(obj: T)
    fun findOne(obj: T): T?
    fun deleteAll()
}