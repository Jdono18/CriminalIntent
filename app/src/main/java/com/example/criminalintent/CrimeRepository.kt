package com.example.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.criminalintent.database.CrimeDatabase
import com.example.criminalintent.database.migration_1_2
import java.util.UUID
import java.util.concurrent.Executors


private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) { // marks the constructor as private to ensure no components can create their own instance.  Requires context object since database is accessing the filesystem.

    private val database: CrimeDatabase = Room.databaseBuilder(  // creates a concrete implementation of abstract CrimeDatabase with listed parameters
        context.applicationContext,  // context object for accessing filesystem
        CrimeDatabase::class.java,  // database class for room to create
        DATABASE_NAME  // name of database for room to create
    ).addMigrations(migration_1_2)
        .build()

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    //fun getCrimes(): List<Crime> = crimeDao.getCrimes()
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()  // defines and adds functions referenced in crimeDao interface using livedata

    //fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)  // defines and adds function referenced in crimeDao interface

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {  // defines function initialize which initializes new instance of the repository
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {  // defines get function which accesses the repository
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}