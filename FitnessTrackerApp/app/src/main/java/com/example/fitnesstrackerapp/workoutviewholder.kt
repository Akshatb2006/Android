package com.example.fitnesstrackerapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class workoutviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val exercise: TextView = itemView.findViewById(R.id.mygroupname)
    private val duration: TextView = itemView.findViewById(R.id.mymessage)
    private val timestamp: TextView = itemView.findViewById(R.id.mytimestamp)
    private val image: ImageView = itemView.findViewById(R.id.myimageview)

    fun bind(workoutItems: workout_items){
        exercise.text = workoutItems.exercise
        duration.text = workoutItems.duration
        timestamp.text = workoutItems.timestamp
        image.setImageResource(workoutItems.profileimage)

    }
}