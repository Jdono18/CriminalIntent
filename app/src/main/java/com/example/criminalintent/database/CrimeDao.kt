package com.example.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.criminalintent.Crime

import java.util.UUID

@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime")  // query to pull all rows and columns out of crime database
    //fun getCrimes(): List<Crime>
    fun getCrimes(): LiveData<List<Crime>>  // uses LiveData to execute querying on background threads

    @Query("SELECT * FROM crime WHERE id=(:id)")  // query to pull only rows whose id matches the ID value provided
    //fun getCrime(id: UUID): Crime?
    fun getCrime(id: UUID): LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)

}