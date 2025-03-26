package com.example.sqltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Chat_Adapter(private val
                    chat_List:MutableList<Chat_item>): RecyclerView.Adapter<Chat_viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            Chat_viewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return Chat_viewHolder(view)
    }
    override fun getItemCount() = chat_List.size
    override fun onBindViewHolder(holder: Chat_viewHolder, position: Int) {
        holder.bind(chat_List[position])
    }
}