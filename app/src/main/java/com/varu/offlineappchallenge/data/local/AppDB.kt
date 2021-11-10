package com.varu.offlineappchallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.varu.offlineappchallenge.data.local.dao.PersonDao
import com.varu.offlineappchallenge.data.local.tables.PersonTable

private const val DB_NAME = "app.db"

@Database(entities = [PersonTable::class], version = 1)
abstract class AppDB: RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    DB_NAME
                ).build().also {
                    instance = it
                }
            }
        }
    }
}