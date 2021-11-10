package com.varu.offlineappchallenge.data.remote

import com.varu.offlineappchallenge.data.models.PersonPageContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("api/")
    suspend fun getPersons(@Query("results") size: Int, @Query("page") page: Int): Response<PersonPageContainer>
}