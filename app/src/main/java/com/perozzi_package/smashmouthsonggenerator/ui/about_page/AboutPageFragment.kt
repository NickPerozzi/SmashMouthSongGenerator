package com.perozzi_package.smashmouthsonggenerator.ui.about_page

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.perozzi_package.smashmouthsonggenerator.BuildConfig
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentAboutPageBinding

class AboutPageFragment : Fragment() {

    private lateinit var binding: FragmentAboutPageBinding
    private lateinit var apViewModel: AboutPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutPageBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apViewModel = AboutPageViewModel(requireActivity().application)

        binding.versionNumber.text =
            resources.getString(R.string.version_number, BuildConfig.VERSION_NAME)

        val richardRoller = binding.rickRoll
        val richardView = binding.rickRollConstraintLayout

        richardRoller.setOnCompletionListener {
            richardView.visibility = View.GONE
        }
        val mc = MediaController(requireContext())
        binding.sueMeButton.setOnClickListener {
            richardView.visibility = View.VISIBLE
            richardRoller.setVideoPath("android.resource://" + requireActivity().packageName + "/" + R.raw.richard_roller)
            richardRoller.setMediaController(mc)
            richardRoller.seekTo(1)
            richardRoller.start()
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val body = resources.getString(R.string.download_pitch)
            val link = "https://play.google.com/store/apps/developer?id=Nick+Perozzi"
            // The link below will be used once the app is approved by Google Play Store
            // val link = "https://play.google.com/store/apps/details?id=com.perozzi_package.smashmouthsonggenerator"
            intent.putExtra(Intent.EXTRA_TEXT, "$body $link")
            startActivity(Intent.createChooser(intent, "Share using"))
        }
    }
}