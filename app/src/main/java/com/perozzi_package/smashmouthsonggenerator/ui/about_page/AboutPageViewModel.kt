package com.perozzi_package.smashmouthsonggenerator.ui.about_page

import android.app.Application
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import com.perozzi_package.smashmouthsonggenerator.R

class AboutPageViewModel(application: Application) : AndroidViewModel(application) {

    fun startVideo(richardRoller: VideoView, mc: MediaController, activity: FragmentActivity) {
        richardRoller.setVideoPath("android.resource://" + activity.packageName + "/" + R.raw.richard_roller)
        richardRoller.setMediaController(mc)
        richardRoller.seekTo(1)
        richardRoller.start()
    }

}