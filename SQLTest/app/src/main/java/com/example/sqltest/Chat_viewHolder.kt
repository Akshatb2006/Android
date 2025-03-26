package com.example.sqltest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Chat_viewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val groupName: TextView = itemView.findViewById(R.id.myGroupName)
    private val groupMessage: TextView =
        itemView.findViewById(R.id.myGroupMessage)
    private val groupTime: TextView = itemView.findViewById(R.id.mytimeStamp)
    private val profileImage: ImageView =
        itemView.findViewById(R.id.myImageView)

    fun bind(chatItem: Chat_item) {
        groupName.text = chatItem.groupName
        groupMessage.text = chatItem.groupMessage
        groupTime.text = chatItem.timestamp
        profileImage.setImageResource(chatItem.image)
    }
}
