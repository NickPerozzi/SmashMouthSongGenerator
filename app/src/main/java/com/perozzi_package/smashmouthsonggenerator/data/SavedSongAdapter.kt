package com.perozzi_package.smashmouthsonggenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.ui.saved_songs.SavedSongsFragmentDirections

class SavedSongAdapter(
    private val listener: OnClickDeleteInterface
) : ListAdapter<SavedSong, SavedSongAdapter.ItemHolder>(SavedSongDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_song_item_layout,parent,false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var savedSongInPosition = getItem(position)
        holder.title.text = getItem(position).songTitle
        holder.lyrics.text = getItem(position).songLyrics

        // Hey this code probably shouldn't be in the adapter!
        holder.itemView.setOnClickListener {
            val action = SavedSongsFragmentDirections
                .actionSavedSongsFragmentToSelectedSavedSongFragment(
                    getItem(position).songLyrics,
                    getItem(position).songTitle,
                    getItem(position)
                )
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        var title: TextView = itemView.findViewById(R.id.songTitle)
        var lyrics: TextView = itemView.findViewById(R.id.songLyrics)
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClickDelete(getItem(position))
            }

        }
    }

    interface OnClickDeleteInterface {
        fun onClickDelete(savedSong: SavedSong)
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