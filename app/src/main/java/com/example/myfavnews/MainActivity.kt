package com.example.myfavnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var madapter: NewsAdapter
    private var category = "general"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generalBtn : ToggleButton = findViewById(R.id.GeneralBtn)
        val scienceBtn : ToggleButton = findViewById(R.id.ScienceBtn)
        val technologyBtn : ToggleButton = findViewById(R.id.TechnologyBtn)
        val entertainmentBtn : ToggleButton = findViewById(R.id.EntertainmentBtn)
        val sportsBtn : ToggleButton = findViewById(R.id.SportsBtn)
        val healthBtn : ToggleButton = findViewById(R.id.HealthBtn)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getNews(category)
        madapter = NewsAdapter(this)
        recyclerView.adapter = madapter

        //handling clicking the categories.

        generalBtn.setOnClickListener {
            category = "general"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        scienceBtn.setOnClickListener {
            category = "science"
            generalBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        technologyBtn.setOnClickListener {
            category = "technology"
            scienceBtn.isChecked = false
            generalBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        entertainmentBtn.setOnClickListener {
            category = "entertainment"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            generalBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        sportsBtn.setOnClickListener {
            category = "sports"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            generalBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        healthBtn.setOnClickListener {
            category = "general"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            generalBtn.isChecked = false
            getNews(category)
        }




    }
    private fun getNews(category : String)  {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/$category/in.json"
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