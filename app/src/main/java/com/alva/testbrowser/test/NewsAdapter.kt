package com.alva.testbrowser.test

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alva.testbrowser.R
import com.alva.testbrowser.databinding.CellNewsBinding
import com.alva.testbrowser.databinding.FooterNewsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewsAdapter : PagingDataAdapter<NewsItem, NewsViewHolder>(DiffCallback) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)
        if (newsItem != null) {
            if (newsItem.url == "") {
                newsItem.url = "https://3g.163.com/news/article/${newsItem.id}.html"
            }
            holder.viewBinding.apply {
                shimmerLayout.apply {
                    setShimmerColor(Color.argb(178, 255, 255, 255))
                    setShimmerAngle(30)
                    startShimmerAnimation()
                }
                textTitle.text = newsItem.title
                textAuthor.text = newsItem.author
                textTime.text = newsItem.time
            }
            Glide.with(holder.itemView)
                .load(newsItem.img)
                .placeholder(R.drawable.ic_baseline_photo)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false.also { holder.viewBinding.shimmerLayout.stopShimmerAnimation() }
                    }
                }).into(holder.viewBinding.imageView)
            holder.itemView.setOnClickListener {
                Bundle().apply {
                    putString("NEWS_POSITION", newsItem.url)
                    it.findNavController().navigate(R.id.action_newsFragment_to_webFragment, this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(CellNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    object DiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
}

class NewsViewHolder(val viewBinding: CellNewsBinding) : RecyclerView.ViewHolder(viewBinding.root)

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.viewBinding.apply {
            when (loadState) {
                is LoadState.Loading -> {
                    textView.setText(R.string.footer_loading)
                    progressBar.visibility = View.VISIBLE
                    holder.itemView.isClickable = false
                }
                is LoadState.Error -> {
                    textView.setText(R.string.footer_error)
                    progressBar.visibility = View.GONE
                    holder.itemView.isClickable = true
                }
                else -> {
                    textView.setText(R.string.footer_success)
                    progressBar.visibility = View.GONE
                    holder.itemView.isClickable = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val holder = FooterViewHolder(
            FooterNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.itemView.setOnClickListener {
            retry()
        }
        return holder
    }
}

class FooterViewHolder(val viewBinding: FooterNewsBinding) :
    RecyclerView.ViewHolder(viewBinding.root)