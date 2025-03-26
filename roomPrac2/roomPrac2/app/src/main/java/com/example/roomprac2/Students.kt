package com.example.roomprac2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Students(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
  val name: String,
 val password: String
)
