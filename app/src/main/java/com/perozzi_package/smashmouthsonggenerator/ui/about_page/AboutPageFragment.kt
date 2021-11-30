package com.perozzi_package.smashmouthsonggenerator.ui.about_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.sueMeButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=xvFZjo5PgG0")
                )
            )
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val body = resources.getString(R.string.download_pitch)
            val sub = "https://play.google.com/store/apps/details?id=com.perozzi_package.smashmouthsonggenerator"
            // val sub = "https://www.youtube.com/watch?v=xvFZjo5PgG0"
            intent.putExtra(Intent.EXTRA_TEXT, "$body $sub")
            // intent.putExtra(Intent.EXTRA_TEXT, sub)
            startActivity(Intent.createChooser(intent, "Share using"))
        }
    }
}