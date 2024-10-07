package com.example.criminalintent

import java.util.Date
import java.util.UUID

data class Crime(val id: UUID = UUID.randomUUID(),  // data class that holds the listed variables and data types
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false)
