package com.example.roomdb

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var database: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contactDB"
        ).build()

        val updateButton = findViewById<Button>(R.id.update)
        val deleteButton = findViewById<Button>(R.id.delete)


        GlobalScope.launch {
            database.contactDao().insertContact(Contact(0, "jimmy", "999899999"))
        }

            updateButton.setOnClickListener {
                GlobalScope.launch {
                    val updatedContact = Contact(0, "sinner", "6969696969")
                    val rowsUpdated = database.contactDao().updateContact(updatedContact)
                    Log.d("RoomDB", "Updated Rows: $rowsUpdated")
                }
            }

            deleteButton.setOnClickListener {
                GlobalScope.launch {
                    val contactToDelete = Contact(0, "jimmy", "999899999")
                    val rowsDeleted = database.contactDao().deleteContact(contactToDelete)
                    Log.d("RoomDB", "Deleted Rows: $rowsDeleted")

            }
        }

    }
    fun getData(view: View) {
        database.contactDao().getContact().observe(this, Observer { contacts ->
            Log.d("roomkey", contacts.toString())
        })
    }
}
