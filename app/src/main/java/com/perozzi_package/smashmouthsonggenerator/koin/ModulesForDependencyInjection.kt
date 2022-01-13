package com.perozzi_package.smashmouthsonggenerator.koin

import android.app.Application
import com.perozzi_package.smashmouthsonggenerator.data.DiscographyRepository
import com.perozzi_package.smashmouthsonggenerator.data.GeneratedLyricsRepository
import com.perozzi_package.smashmouthsonggenerator.ui.album_weights.WeightAssignmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // same Application object every time
    single { androidApplication().applicationContext }

    // new ImportantClass object every time
    factory { ImportantClass() }

    single { DiscographyRepository() }

    single { GeneratedLyricsRepository() }

    viewModel { WeightAssignmentViewModel(get(), get(), get(), get()) }

}
