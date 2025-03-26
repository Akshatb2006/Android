package com.example.fitnesstrackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class workoutAdapter(private val workoutItems: MutableList<workout_items>):RecyclerView.Adapter<workoutviewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): workoutviewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.workout_items,parent,false)
        return workoutviewholder(view)
    }

    override fun getItemCount(): Int {
        val size = workoutItems.size
        return size
    }

    override fun onBindViewHolder(holder: workoutviewholder, position: Int) {
        val workoutItems = workoutItems[position]
        holder.bind(workoutItems)
    }
}