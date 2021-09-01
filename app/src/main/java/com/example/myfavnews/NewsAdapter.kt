package com.example.myfavnews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(context :Context) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items : ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view , parent , false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]            //current item is of type News
        holder.titleView.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems (updatedItems : ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val titleView : TextView = itemView.findViewById(R.id.title)

}