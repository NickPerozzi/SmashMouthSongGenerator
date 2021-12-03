package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.adapters.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.R
import java.util.*

class WeightAssignmentViewModel(application: Application) : AndroidViewModel(application) {

    private var weightGridLayoutManager: GridLayoutManager? =
        GridLayoutManager(application, 1, LinearLayoutManager.VERTICAL, false)

    private val smashMouthDictionary: MutableMap<String, Map<String, Any>> = mutableMapOf(
        Pair(
            "fush_yu_mang", mapOf(
                Pair("name", "Fush Yu Mang"),
                Pair("year", "(1997)"),
                Pair("imageAddress", R.drawable.alb_1_fush_yu_mang_97)
            )
        ),
        Pair(
            "astro_lounge", mapOf(
                Pair("name", "Astro Lounge"),
                Pair("year", "(1999)"),
                Pair("imageAddress", R.drawable.alb_2_astro_lounge_99)
            )
        ),
        Pair(
            "smash_mouth",
            mapOf(
                Pair("name", "Smash Mouth"),
                Pair("year", "(2001)"),
                Pair("imageAddress", R.drawable.alb_3_smash_mouth_01)
            )
        ),
        Pair(
            "get_the_picture", mapOf(
                Pair("name", "Get the Picture"),
                Pair("year", "(2003)"),
                Pair("imageAddress", R.drawable.alb_4_get_the_picture_03)
            )
        ),
        Pair(
            "all_star_smash_hits", mapOf(
                Pair("name", "All Star Smash Hits"),
                Pair("year", "(2005)"),
                Pair("imageAddress", R.drawable.alb_5_all_star_smash_hits_05)
            )
        ),
        Pair(
            "the_gift_of_rock", mapOf(
                Pair("name", "The Gift of Rock"),
                Pair("year", "(2005)"),
                Pair("imageAddress", R.drawable.alb_6_the_gift_of_rock_05)
            )
        ),
        Pair(
            "summer_girl", mapOf(
                Pair("name", "Summer Girl"),
                Pair("year", "(2006)"),
                Pair("imageAddress", R.drawable.alb_7_summer_girl_06)
            )
        ),
        Pair(
            "magic", mapOf(
                Pair("name", "Magic"),
                Pair("year", "(2012)"),
                Pair("imageAddress", R.drawable.alb_8_magic_12)
            )
        )
    )

    @Suppress("ReplaceManualRangeWithIndicesCalls")
    private fun oneWeights(map: MutableMap<String, Map<String, Any>>): MutableList<String> {
        val stringList = mutableListOf<String>()
        for (index in 0 until map.size)
            stringList.add("1")
        return stringList
    }

    var albumWeights = oneWeights(smashMouthDictionary)

    private val yearsList = mutableListOf<String>()
    private val titlesList = mutableListOf<String>()
    private val imageAddressList = mutableListOf<Int>()
    private var arrayForAlbumGrid: ArrayList<AlbumGrid> = arrayListOf()
    fun prepareDataForAdapter() {
        for (album in smashMouthDictionary.keys) {
            yearsList.add(smashMouthDictionary[album]!!["year"] as String)
            titlesList.add(smashMouthDictionary[album]!!["name"] as String)
            imageAddressList.add(smashMouthDictionary[album]!!["imageAddress"] as Int)
        }
        for (index in 0 until yearsList.size) {
            arrayForAlbumGrid.add(
                AlbumGrid(
                    titlesList[index],
                    yearsList[index],
                    imageAddressList[index],
                    albumWeights[index]
                )
            )
        }
    }

    fun prepareAlbumRecyclerView(
        thisInterface: AlbumGridAdapter.OnSeekBarChangeListenerInterface,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = weightGridLayoutManager
        recyclerView.setHasFixedSize(true)
        val adapter = AlbumGridAdapter(thisInterface)
        recyclerView.adapter = adapter
        adapter.submitList(arrayForAlbumGrid)
    }
}