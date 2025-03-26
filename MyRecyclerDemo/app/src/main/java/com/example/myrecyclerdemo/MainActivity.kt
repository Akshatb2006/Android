package com.example.myrecyclerdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: chat_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.myrecycler)

        val item_list = mutableListOf(
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.images
            ),
                    Chat_item(
                    groupname = "Dog PArk Group",
            groupmessage = "Jordan : Anybody's Up",
            timestamp = "10:45",
            R.drawable.img1
        ),
            Chat_item(
                groupname = "Dog PArk Group",
        groupmessage = "Jordan : Anybody's Up",
        timestamp = "10:45",
        R.drawable.img2
        ),
            Chat_item(
            groupname = "Dog PArk Group",
            groupmessage = "Jordan : Anybody's Up",
            timestamp = "10:45",
            R.drawable.img3
        ),
            Chat_item(
                groupname = "Dog PArk Group",
        groupmessage = "Jordan : Anybody's Up",
        timestamp = "10:45",
        R.drawable.img4
        ),
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.img5
            ),
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.img6
            ),
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.img7
            ),
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.img8
            ),
            Chat_item(
                groupname = "Dog PArk Group",
                groupmessage = "Jordan : Anybody's Up",
                timestamp = "10:45",
                R.drawable.img9
            )
        )
        chatAdapter = chat_Adapter(item_list)
        recyclerView.apply {
            var linearManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.chatAdapter
        }
    }
}