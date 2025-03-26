package com.example.sqltest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatAdapter: Chat_Adapter
    private val chatList = mutableListOf<Chat_item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        chatRecyclerView = view.findViewById(R.id.myrecycler)
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        chatList.add(Chat_item("Dog Park Group", "Jordan: Anybody's up?", "10:45 AM", R.drawable.ic_launcher_background))
        chatList.add(Chat_item("Friends", "Alex: Let's meet up", "9:30 AM", R.drawable.ic_launcher_background))
        chatList.add(Chat_item("Study Group", "Emma: Assignment due?", "8:15 AM", R.drawable.ic_launcher_background))

        chatAdapter = Chat_Adapter(chatList)
        chatRecyclerView.adapter = chatAdapter

        return view
    }
}