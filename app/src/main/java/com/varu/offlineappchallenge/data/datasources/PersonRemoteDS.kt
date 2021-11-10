package com.varu.offlineappchallenge.data.datasources

import android.util.Log
import com.varu.offlineappchallenge.data.models.PersonPageContainer
import com.varu.offlineappchallenge.data.remote.ApiClient
import java.lang.IllegalStateException

class PersonRemoteDS(private val apiClient: ApiClient) {

    suspend fun fetchPersons(size: Int, page: Int): PersonPageContainer {
        try {
            val response = apiClient.api.getPersons(size, page)
            return if (response.isSuccessful) response.body()!! else throw IllegalStateException("invalid response")
        } catch (e: Exception) {
            Log.i("PersonRemoteDS", "failure in fetching persons: ${e.message}")
            throw e
        }
    }
}