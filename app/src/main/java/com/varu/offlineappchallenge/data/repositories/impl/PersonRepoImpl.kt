package com.varu.offlineappchallenge.data.repositories.impl

import android.util.Log
import com.varu.offlineappchallenge.data.datasources.PersonLocalDS
import com.varu.offlineappchallenge.data.datasources.PersonRemoteDS
import com.varu.offlineappchallenge.data.models.Person
import com.varu.offlineappchallenge.data.repositories.PersonRepo
import java.lang.RuntimeException

class PersonRepoImpl(private val remoteDS: PersonRemoteDS, private val localDS: PersonLocalDS) :
    PersonRepo {

    override suspend fun fetchPersonsForMatch(size: Int, page: Int): Result<List<Person>> {
        return localDS.fetchPersons(size, page)
            .filter { it.matchAction == null } //need to apply filter afterwards, else items could miss in page while fetching from remote next time
            .let { localList ->
                if (localList.isNotEmpty()) {
                    Log.i("PersonRepoImpl", "fetchPersonsForMatch: result from local db")
                    return@let Result.success(localList)
                }

                // fetch from remote only if found no cards from local, can apply additional logic
                // to pre-fetch from remote in bg when found only few cards from the local db
                return@let try {
                    remoteDS.fetchPersons(size, page).results?.let {
                        localDS.storePersons(it)
                        Log.i("PersonRepoImpl", "fetchPersonsForMatch: result from remote")
                        Result.success(it)
                    } ?: Result.failure(RuntimeException("Invalid result"))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }

    override suspend fun processPersonMatchAction(person: Person) {
        localDS.updatePerson(person)
    }
}