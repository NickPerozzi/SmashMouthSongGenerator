package com.perozzi_package.smashmouthsonggenerator.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.ui.album_weights.AlbumGrid
import com.perozzi_package.smashmouthsonggenerator.R

class AlbumGridAdapter(
    private val listener: OnSeekBarChangeListenerInterface
) :
    ListAdapter<AlbumGrid, AlbumGridAdapter.ItemHolder>(AlbumGridDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item_layout,parent,false)
        return ItemHolder(itemHolder)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val albumGrid : AlbumGrid = getItem(position)
        holder.image.setImageResource(albumGrid.albumImage!!)
        holder.year.text = albumGrid.albumYear
        holder.title.text = albumGrid.albumName
        holder.weightBar.progress = albumGrid.albumWeight!!.toInt()
        holder.weightText.text = "Album weight: ${getItem(position).albumWeight}/5"

    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    SeekBar.OnSeekBarChangeListener{
        var title: TextView = itemView.findViewById(R.id.albumTitle)
        var year: TextView = itemView.findViewById(R.id.albumYear)
        var image: ImageView = itemView.findViewById(R.id.albumImage)
        var weightText: TextView = itemView.findViewById(R.id.albumWeight)
        var weightBar: SeekBar = itemView.findViewById(R.id.weightSeekBar)

        init {
            weightBar.setOnSeekBarChangeListener(this)
        }

        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSeekBarChange(position, p1, weightText, getItem(position).albumName)
                weightBar.progress = p1
            }
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}

        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }

    interface OnSeekBarChangeListenerInterface {
        fun onSeekBarChange(position: Int, weight: Int, textView: TextView, albumName: String?)
    }
}

class AlbumGridDiffUtil : DiffUtil.ItemCallback<AlbumGrid>() {
    override fun areContentsTheSame(oldItem: AlbumGrid, newItem: AlbumGrid): Boolean {
        val sameImage = oldItem.albumImage == newItem.albumImage
        val sameYear = oldItem.albumYear == newItem.albumYear
        val sameName = oldItem.albumName == newItem.albumName
        val sameWeight = oldItem.albumWeight == newItem.albumWeight
        return sameImage && sameYear && sameName && sameWeight
    }

    override fun areItemsTheSame(oldItem: AlbumGrid, newItem: AlbumGrid): Boolean {
        return oldItem == newItem
    }

}