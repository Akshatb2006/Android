package com.example.freeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val api = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

        api.getTodos().enqueue(object : Callback<List<chat>> {
            override fun onResponse(call: Call<List<chat>>, response: Response<List<chat>>) {
                if (response.isSuccessful) {
                    val todoList = response.body() ?: emptyList()
                    chatAdapter = ChatAdapter(todoList)
                    recyclerView.adapter = chatAdapter
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<chat>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", t.toString())
            }
        })
    }
}
