package com.varu.offlineappchallenge.data.local.dao

import androidx.room.*
import com.varu.offlineappchallenge.data.local.tables.PersonTable

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(vararg persons: PersonTable)

    @Update
    suspend fun updatePerson(person: PersonTable)

    @Query("select * from persons limit :limit offset :offset")
    suspend fun getPersons(limit: Int, offset: Int): List<PersonTable>
}