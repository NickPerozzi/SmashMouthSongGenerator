package com.perozzi_package.smashmouthsonggenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class LyricAdapter : ListAdapter<Lyrics, LyricAdapter.ItemHolder>(LyricDiffUtil()) {

    private val diffCallback = object : DiffUtil.ItemCallback<Lyrics>() {
        override fun areContentsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean {
            return oldItem.lyrics == newItem.lyrics
        }

        override fun areItemsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean {
            return oldItem.lyrics == newItem.lyrics
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.lyrics_item_layout,parent,false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.lyricSpace.text = getItem(position).lyrics
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lyricSpace: TextView = itemView.findViewById(R.id.lyricTextView)
    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var listOfLyrics: List<Lyrics>
        get() = differ.currentList
        set(value) {submitList(value)}

}

class LyricDiffUtil : DiffUtil.ItemCallback<Lyrics>() {
    override fun areContentsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean {
        return oldItem.lyrics == newItem.lyrics
    }

    override fun areItemsTheSame(oldItem: Lyrics, newItem: Lyrics): Boolean {
        return oldItem.lyrics == newItem.lyrics
    }

}