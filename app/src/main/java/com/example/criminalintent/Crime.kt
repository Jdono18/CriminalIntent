package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),  // data class that holds the listed variables and data types
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false)
