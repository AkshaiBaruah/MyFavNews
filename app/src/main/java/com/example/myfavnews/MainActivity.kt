package com.example.myfavnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {

    lateinit var madapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView= findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        val madapter = NewsAdapter(this)
        recyclerView.adapter = madapter


    }
    private fun fetchData()  {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=2caedae36fec433dae6171558bb76d4f"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {Response ->
                val newsJsonArray = Response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for ( i in 0 until newsJsonArray.length()){
                    val currentJsonObject = newsJsonArray.getJSONObject(i)
                    val currentNews = News(
                        currentJsonObject.getString("title"),
                        currentJsonObject.getString("author"),
                        currentJsonObject.getString("url"),
                        currentJsonObject.getString("urlToImage")
                    )

                    newsArray.add(currentNews)
                }
                madapter.updateItems(newsArray)
            },

            {
                Toast.makeText( this , "Oops something went wrong! :(" , Toast.LENGTH_SHORT).show()
            }

        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

}