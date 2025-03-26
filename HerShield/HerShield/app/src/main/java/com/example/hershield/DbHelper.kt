package com.example.hershield

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * Database helper class for storing emergency contacts
 */
class DbHelper(private val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EmergencyContacts.db"
        private const val TABLE_NAME = "CONTACTS"
        private const val KEY_ID = "ID"
        private const val NAME = "NAME"
        private const val PHONE_NO = "PHONE_NO"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT, $PHONE_NO TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /**
     * Adds a new contact to the database
     * @param contact ContactModel object containing contact information
     * @return Boolean indicating success or failure
     */
    fun addContact(contact: ContactModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(NAME, contact.name)
            put(PHONE_NO, contact.phoneNo)
        }
        val success = db.insert(TABLE_NAME, null, values)
        db.close()

        if (success != -1L) {
            Toast.makeText(context, "Contact added successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to add contact", Toast.LENGTH_SHORT).show()
        }

        return success != -1L
    }

    /**
     * Retrieves all contacts from the database
     * @return List of ContactModel objects
     */
    fun getAllContacts(): List<ContactModel> {
        val list: MutableList<ContactModel> = ArrayList()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phoneNo = cursor.getString(2)
                list.add(ContactModel(id, name, phoneNo))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    /**
     * Deletes a contact from the database
     * @param id ID of the contact to delete
     * @return Boolean indicating success or failure
     */
    fun deleteContact(id: Int): Boolean {
        val db = writableDatabase
        val success = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success > 0
    }

    /**
     * Counts the number of contacts in the database
     * @return Int representing the number of contacts
     */
    fun count(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    /**
     * Updates an existing contact in the database
     * @param contact ContactModel object with updated information
     * @return Boolean indicating success or failure
     */
    fun updateContact(contact: ContactModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(NAME, contact.name)
            put(PHONE_NO, contact.phoneNo)
        }
        val success = db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(contact.id.toString()))
        db.close()
        return success > 0
    }

    /**
     * Gets a specific contact by ID
     * @param id ID of the contact to retrieve
     * @return ContactModel object or null if not found
     */
    fun getContactById(id: Int): ContactModel? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, NAME, PHONE_NO),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var contact: ContactModel? = null
        if (cursor.moveToFirst()) {
            contact = ContactModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
        }
        cursor.close()
        return contact
    }
}