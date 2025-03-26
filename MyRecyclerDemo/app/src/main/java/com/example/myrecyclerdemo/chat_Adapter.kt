package com.example.myrecyclerdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class chat_Adapter(private val chat_list:MutableList<Chat_item>):RecyclerView.Adapter<chat_ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chat_ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return chat_ViewHolder(view)

    }

    override fun getItemCount()=chat_list.size

    override fun onBindViewHolder(holder: chat_ViewHolder, position: Int) {
        val item = chat_list[position]
        holder.bind(item)
    }

}