package com.example.roomprac2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentsDao {
    @Insert
    suspend fun insertStudent(student: Students)

    @Update
    suspend fun updateStudent(student: Students)

    @Delete
    suspend fun deleteStudent(student: Students)

//
//    @Query("SELECT * FROM students WHERE name = :name")
//    suspend fun getStudentByName(name: String): Students?
//
//    @Query("SELECT * FROM students WHERE password = :password")
//    suspend fun getStudentByPassword(password: String): Students?

    // Add a method to validate credentials
    @Query("SELECT EXISTS(SELECT 1 FROM students WHERE name = :username AND password = :password)")
    suspend fun validateCredentials(username: String, password: String): Boolean


    @Query("SELECT * FROM students")
    fun getAllStudents(): LiveData<List<Students>>

}