package com.example.myrecyclerdemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class chat_ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    private val groupName:TextView = itemView.findViewById(R.id.myGroupName)
    private val groupMessage:TextView = itemView.findViewById(R.id.myGroupMessage)
    private val groupTIme:TextView = itemView.findViewById(R.id.myTimeStamp)
    private val profileimg:ImageView = itemView.findViewById(R.id.myimageview)

    fun bind(chatItem: Chat_item){
        groupName.text = chatItem.groupname
        groupMessage.text = chatItem.groupmessage
        groupTIme.text = chatItem.timestamp
        profileimg.setImageResource(chatItem.image)
    }


}