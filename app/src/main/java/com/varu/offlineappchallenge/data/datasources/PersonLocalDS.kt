package com.varu.offlineappchallenge.data.datasources

import com.varu.offlineappchallenge.data.local.dao.PersonDao
import com.varu.offlineappchallenge.data.local.tables.PersonTable
import com.varu.offlineappchallenge.data.local.tables.toPerson
import com.varu.offlineappchallenge.data.models.Person

class PersonLocalDS(private val personDao: PersonDao) {

    suspend fun storePersons(persons: List<Person>) {
        personDao.insertPersons(*persons.map { PersonTable(it) }.toTypedArray())
    }

    suspend fun fetchPersons(size: Int, page: Int): List<Person> {
        return personDao.getPersons(limit = size, offset = size * page)
            .map { it.toPerson() }
    }

    suspend fun updatePerson(person: Person) {
        personDao.updatePerson(PersonTable(person))
    }
}