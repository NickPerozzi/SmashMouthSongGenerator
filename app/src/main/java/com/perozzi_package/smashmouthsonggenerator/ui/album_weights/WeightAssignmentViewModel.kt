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

    // Eventually, this data will not be needed here since it will be scraped from elsewhere.
    // The layout of this Smash Mouth data is a sample for how all artists' data will look, once
    // the functionality is implemented (images will be grabbed via url)
    // TODO(For loop that does this guy JSONObject(smashMouthDictionary["fush_yu_mang"]).toString()
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

    // Suppressed because map.indices (the suggestion) is incompatible with map's type
    @Suppress("ReplaceManualRangeWithIndicesCalls")
    private fun oneWeights(map: MutableMap<String, Map<String, Any>>): MutableList<String> {
        val stringList = mutableListOf<String>()
        for (index in 0 until map.size)
            stringList.add("1")
        return stringList
    }

    var albumWeights = oneWeights(smashMouthDictionary)
    private var arrayForAlbumGrid: ArrayList<AlbumGrid> = arrayListOf()

    fun prepareDataForAdapter() {
        val yearsList = mutableListOf<String>()
        val titlesList = mutableListOf<String>()
        val imageAddressList = mutableListOf<Int>()
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

    // This could be a lambda, but the function name provides clarity to its purpose and syntax
    fun areThereAnyNonZeroWeights(): Boolean = albumWeights.count { it == "0" } != albumWeights.size

    fun calibrateRedundantWeighting() {
        for (index in 0 until albumWeights.size) {

            if (albumWeights.all { it == albumWeights[index] || it == "0" }) {
                for (index2 in 0 until albumWeights.size) {
                    if (albumWeights[index2] != "0") {
                        albumWeights[index2] = "1"
                    }
                }
                return
            } else if (albumWeights.all { it.toInt() % 2 == 0 }) {
                for (index2 in 0 until albumWeights.size) {
                    albumWeights[index] = (albumWeights[index].toInt() / 2).toString()
                }
                return
            }
        }
    }

    val albumWeightsMap = mutableMapOf(
        "Fush Yu Mang" to 1,
        "Astro Lounge" to 1,
        "Smash Mouth" to 1,
        "Get the Picture" to 1,
        "All Star Smash Hits" to 1,
        "The Gift of Rock" to 1,
        "Summer Girl" to 1,
        "Magic" to 1
    )

    fun calibrateRedundantWeighting2() {
        val temp = albumWeightsMap.filter { entry -> entry.value != 0 }
        if (temp.values.all { it == temp.values.elementAt(0) }) {
            val temp2 = temp.map { it.value / it.value }
        }
    }

}



