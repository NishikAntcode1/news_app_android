package com.example.newsfresh

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfresh.network.Article

class NewsListAdapter(private var articles: List<Article>, private val listener :NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(articles[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = articles[position]
        holder.bind(currentItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    private val titleView: TextView = itemView.findViewById(R.id.newsTitle)
    private val authorView: TextView = itemView.findViewById(R.id.newsAuthor)
    private val imageView: ImageView = itemView.findViewById(R.id.newsImage)

    @SuppressLint("SetTextI18n")
    fun bind(article: Article) {
        titleView.text = article.title
        authorView.text = "Article by ${toCamelCaseWithSpaces(article.author ?: "Unknown Author")}"



        // Load image using Glide library
        Glide.with(itemView)
            .load(article.urlToImage)
            .centerCrop()
            .into(imageView)
    }

    private fun toCamelCaseWithSpaces(input: String): String {
        val words = input.split(" ")
        val result = StringBuilder()

        for (word in words) {
            if (word.isNotEmpty()) {
                val firstChar = word[0].toUpperCase()
                val restOfWord = word.substring(1).toLowerCase()
                result.append(firstChar).append(restOfWord).append(" ")
            }
        }

        return result.toString().trimEnd()
    }
}

interface NewsItemClicked {
    fun onItemClicked(Item: Article)
}