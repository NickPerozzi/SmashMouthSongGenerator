package com.perozzi_package.smashmouthsonggenerator.koin

import com.perozzi_package.smashmouthsonggenerator.data.DiscographyRepository
import com.perozzi_package.smashmouthsonggenerator.data.GeneratedLyricsRepository
import com.perozzi_package.smashmouthsonggenerator.ui.about_page.AboutPageViewModel
import com.perozzi_package.smashmouthsonggenerator.ui.album_weights.WeightAssignmentViewModel
import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.LyricDisplayViewModel
import com.perozzi_package.smashmouthsonggenerator.ui.saved_songs.SavedSongsViewModel
import com.perozzi_package.smashmouthsonggenerator.ui.selected_saved_song.SelectedSavedSongViewModel
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

    single { androidApplication().applicationContext.resources }

}

val weightAssignmentViewModelModule = module {
    viewModel { WeightAssignmentViewModel(get(), get(), get(), get()) }

}

val lyricDisplayViewModel = module {
    viewModel { LyricDisplayViewModel(get())}
}

val savedSongsViewModelModule = module {
    viewModel { SavedSongsViewModel(get()) }

}

val selectedSavedSongViewModelModule = module {
    viewModel { SelectedSavedSongViewModel(get()) }

}

val aboutPageViewModel = module {
    viewModel { AboutPageViewModel(get(), get())}
}

/*
val dataStoreModule = module {
    single { DataStore }
}
*/

val modulesForDependencyInjection = listOf(
    appModule,
    weightAssignmentViewModelModule,
    lyricDisplayViewModel,
    savedSongsViewModelModule,
    selectedSavedSongViewModelModule,
    aboutPageViewModel
)