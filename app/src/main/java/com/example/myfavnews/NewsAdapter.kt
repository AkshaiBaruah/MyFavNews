package com.example.myfavnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener : NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view , parent , false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]            //current item is of type News
        holder.titleView.text = currentItem.title
        if(currentItem.imageUrl != "null"){
            Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
        }
        else
        Glide.with(holder.itemView.context).load(R.drawable.imagenotfound).into(holder.imageView)

        if(currentItem.author != "null")
        holder.authorView.text=currentItem.author



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
    val imageView : ImageView = itemView.findViewById(R.id.newsImage)
    val authorView : TextView = itemView.findViewById(R.id.author)

}
interface NewsItemClicked {
    fun onItemClicked(item : News)
}