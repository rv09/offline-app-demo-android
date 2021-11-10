package com.varu.offlineappchallenge.data.repositories

import com.varu.offlineappchallenge.data.models.Person

interface PersonRepo {

    suspend fun fetchPersonsForMatch(size: Int, page: Int): Result<List<Person>>

    suspend fun processPersonMatchAction(person: Person)
}