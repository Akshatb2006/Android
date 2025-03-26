package com.example.roomprac2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Students::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {
    abstract fun studentsDao(): StudentsDao

}