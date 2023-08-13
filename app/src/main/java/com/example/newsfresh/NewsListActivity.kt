package com.example.newsfresh

import android.app.ProgressDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfresh.network.Article
import com.example.newsfresh.network.ResponseDataClass
import com.example.newsfresh.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsListActivity : AppCompatActivity(), NewsItemClicked {

//    https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY

    val API_KEY: String = "1ba19939590e4b499145f5e242e2705c"
    val COUNTRY_CODE: String = "us"

    private lateinit var adapter: NewsListAdapter
    private var articles: List<Article> = emptyList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        recyclerView = findViewById(R.id.myRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsListAdapter(articles, this)
        recyclerView.adapter = adapter
        fetchData()
    }

    private fun fetchData()  {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait....")
        progressDialog.show()

        val list = ArrayList<String>()

        RetrofitInstance.apiInterface.getTopHeadlines(COUNTRY_CODE, API_KEY).enqueue(object : Callback<ResponseDataClass?> {
            override fun onResponse(
                call: Call<ResponseDataClass?>,
                response: Response<ResponseDataClass?>
            ) {
                articles = response.body()?.articles ?: emptyList()
                adapter.updateData(articles)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseDataClass?>, t: Throwable) {
                Toast.makeText(this@NewsListActivity, t.localizedMessage, Toast.LENGTH_SHORT ).show()
                progressDialog.dismiss()
            }
        })
    }

    override fun onItemClicked(item: Article) {
        val toolbarColor = Color.parseColor("#FF6C31D5")
        val url = item.url
        val intent = CustomTabsIntent.Builder()
            .setToolbarColor(toolbarColor)
            .build()
        intent.launchUrl(this@NewsListActivity, Uri.parse(url))
    }
}