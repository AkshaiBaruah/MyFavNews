package com.example.myfavnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var recyclerView : RecyclerView
    private lateinit var madapter: NewsAdapter
    private var category = "top"
    private var query = "football"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topBtn : ToggleButton = findViewById(R.id.TopBtn)
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

        topBtn.setOnClickListener {
            category = "top"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        scienceBtn.setOnClickListener {
            category = "science"
            topBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        technologyBtn.setOnClickListener {
            category = "technology"
            scienceBtn.isChecked = false
            topBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        entertainmentBtn.setOnClickListener {
            category = "entertainment"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            topBtn.isChecked = false
            sportsBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        sportsBtn.setOnClickListener {
            category = "sports"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            topBtn.isChecked = false
            healthBtn.isChecked = false
            getNews(category)
        }
        healthBtn.setOnClickListener {
            category = "general"
            scienceBtn.isChecked = false
            technologyBtn.isChecked = false
            entertainmentBtn.isChecked = false
            sportsBtn.isChecked = false
            topBtn.isChecked = false
            getNews(category)
        }
        val search_btn : SearchView = findViewById(R.id.search_bar)
        search_btn.setOnClickListener {

        }



    }
    private fun getNews(category : String)  {
        val url = "https://newsdata.io/api/1/news?apikey=pub_1060d54a436d939125834ee6e4ec88557987&country=in&category=$category"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {Response ->
                val newsJsonArray = Response.getJSONArray("results")
                val newsArray = ArrayList<News>()
                for ( i in 0 until newsJsonArray.length()){

                    val currentJsonObject = newsJsonArray.getJSONObject(i)
                   if(currentJsonObject.getString("image_url") == "null"){
                        continue
                    }
                    val currentNews = News(
                        currentJsonObject.getString("title"),
                        currentJsonObject.getString("source_id"),
                        currentJsonObject.getString("link"),
                        currentJsonObject.getString("image_url")
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
    private fun searchNews(query : String){
        val query_api = "https://newsdata.io/api/1/news?apikey=pub_1060d54a436d939125834ee6e4ec88557987&qInTitle=$query"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            query_api,
            null,
            {Response ->
                val newsJsonArray = Response.getJSONArray("results")
                val newsArray = ArrayList<News>()
                for ( i in 0 until newsJsonArray.length()){

                    val currentJsonObject = newsJsonArray.getJSONObject(i)
                    if(currentJsonObject.getString("image_url") == "null"){
                        continue
                    }
                    val currentNews = News(
                        currentJsonObject.getString("title"),
                        currentJsonObject.getString("source_id"),
                        currentJsonObject.getString("link"),
                        currentJsonObject.getString("image_url")
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

    override fun onItemClicked(item: News) {
        val builder =CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this , Uri.parse(item.url))
    }


}