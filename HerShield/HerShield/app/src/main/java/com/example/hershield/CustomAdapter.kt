package com.example.hershield

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Custom adapter for the contacts list view
 */
class CustomAdapter(
    private val context: Context,
    private var contactsList: List<ContactModel>
) : ArrayAdapter<ContactModel>(context, android.R.layout.simple_list_item_1, contactsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Create a database helper object to handle database manipulations
        val db = DbHelper(context)

        // Get the data item for this position
        val contact = contactsList[position]

        // Check if an existing view is being reused, otherwise inflate the view
        val rowView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.contact_list_item, parent, false)

        // Lookup views for data population
        val nameText = rowView.findViewById<TextView>(R.id.tvName)
        val phoneText = rowView.findViewById<TextView>(R.id.tvPhone)
        val deleteButton = rowView.findViewById<ImageView>(R.id.delete_button)
        val contactLayout = rowView.findViewById<LinearLayout>(R.id.linear)

        // Populate the data into the template view using the data object
        nameText.text = contact.name
        phoneText.text = contact.phoneNo

        // Set up long click listener for removal dialog
        contactLayout.setOnLongClickListener {
            // Generate a MaterialAlertDialog
            MaterialAlertDialogBuilder(context)
                .setTitle("Remove Contact")
                .setMessage("Are you sure you want to remove this contact?")
                .setPositiveButton("YES") { _, _ ->
                    // Delete the specified contact from the database
                    db.deleteContact(contact.id)
                    // Get updated list and refresh
                    refresh(db.getAllContacts())
                    Toast.makeText(context, "Contact removed!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("NO") { _, _ ->
                    // Do nothing if user selects NO
                }
                .show()
            true
        }

        // Set up click listener for delete button
        deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle("Remove Contact")
                .setMessage("Are you sure you want to remove this contact?")
                .setPositiveButton("YES") { _, _ ->
                    db.deleteContact(contact.id)
                    refresh(db.getAllContacts())
                    Toast.makeText(context, "Contact removed!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("NO") { _, _ ->
                    // Do nothing if user selects NO
                }
                .show()
        }

        // Return the completed view to render on screen
        return rowView
    }

    // This method will update the ListView
    fun refresh(newList: List<ContactModel>) {
        contactsList = newList
        notifyDataSetChanged()
    }
}