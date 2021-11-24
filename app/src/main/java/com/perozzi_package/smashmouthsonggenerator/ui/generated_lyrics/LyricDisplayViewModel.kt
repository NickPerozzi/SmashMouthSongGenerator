package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.perozzi_package.smashmouthsonggenerator.Lyrics

class LyricDisplayViewModel(application: Application) : AndroidViewModel(application) {

    var lyrics: MutableLiveData<String> = MutableLiveData("Your generated lyrics will go here!")
    // var lyrics: String = "Your generated lyrics will go here!"

/*
    fun putLyricsIntoList(): MutableList<Lyrics> {
        return mutableListOf(Lyrics(lyrics))
    }
*/

    var lyricGridLayoutManager: GridLayoutManager? =
        GridLayoutManager(application, 1, LinearLayoutManager.VERTICAL, false)
}