package com.perozzi_package.smashmouthsonggenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong

class SavedSongAdapter : ListAdapter<SavedSong, SavedSongAdapter.ItemHolder>(SavedSongDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_song_item_layout,parent,false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.title.text = getItem(position).songTitle
        holder.lyrics.text = getItem(position).songLyrics
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.songTitle)
        var lyrics: TextView = itemView.findViewById(R.id.songLyrics)
    }

}

class SavedSongDiffUtil : DiffUtil.ItemCallback<SavedSong>() {
    override fun areContentsTheSame(oldItem: SavedSong, newItem: SavedSong): Boolean {
        return oldItem.songTitle == newItem.songTitle &&
                oldItem.songLyrics == newItem.songLyrics
    }

    override fun areItemsTheSame(oldItem: SavedSong, newItem: SavedSong): Boolean {
        return oldItem == newItem
    }

}