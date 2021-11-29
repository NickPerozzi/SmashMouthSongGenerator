package com.perozzi_package.smashmouthsonggenerator.ui.about_page

import android.content.Intent
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.perozzi_package.smashmouthsonggenerator.BuildConfig
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentAboutPageBinding
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSavedSongsBinding

class AboutPageFragment : Fragment() {

    private lateinit var binding: FragmentAboutPageBinding
    private lateinit var apViewModel: AboutPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutPageBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apViewModel = AboutPageViewModel(requireActivity().application)

        binding.versionNumber.text = resources.getString(R.string.version_number, BuildConfig.VERSION_CODE.toString())

        binding.sueMeButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xvFZjo5PgG0")))
        }
    }
}