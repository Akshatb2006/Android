package com.example.contentreceivers

import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun viewContent(){
        val contact = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        if (contact!=null && contact.moveToFirst()){
            do {
                var name = contact.getString(contact.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                Log.d("getValue",name)
            }
                while(
                    contact.moveToLast()
                )
                contact.close()
        }
    }
}