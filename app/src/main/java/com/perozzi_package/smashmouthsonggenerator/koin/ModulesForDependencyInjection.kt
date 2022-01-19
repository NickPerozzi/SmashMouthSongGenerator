package com.perozzi_package.smashmouthsonggenerator.koin

import android.content.Context
import com.perozzi_package.smashmouthsonggenerator.data.*
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

val savedSongDatabaseModule = module{
    fun provideSavedSongDatabase(context: Context): SavedSongDatabase {
        return SavedSongDatabase.getDatabase(context)
    }
    single { provideSavedSongDatabase(androidApplication()) }

    fun provideSavedSongDao(database: SavedSongDatabase): SavedSongDao {
        return database.savedSongDao()
    }
    single { provideSavedSongDao(get()) }
}

val savedSongRepositoryModule = module{
    fun provideSavedSongRepository(savedSongDao: SavedSongDao): SavedSongRepository {
        return SavedSongRepository(savedSongDao)
    }
    single { provideSavedSongRepository(get()) }
}

val weightAssignmentViewModelModule = module {
    viewModel { WeightAssignmentViewModel(get(), get(), get(), get()) }
}

/*
val dataStoreModule = module {
    fun provideDataStore(): DataStore<Preferences> {
        return DataStore<Preferences>
    }
    single { DataStore }
}
*/

val lyricDisplayViewModelModule = module {
    viewModel { LyricDisplayViewModel(get(), get()) }
}

val savedSongsViewModelModule = module {
    viewModel { SavedSongsViewModel(get()) }
}

val selectedSavedSongViewModelModule = module {
    viewModel { SelectedSavedSongViewModel(get()) }
}

val aboutPageViewModelModule = module {
    viewModel { AboutPageViewModel(get(), get())}
}

val modulesForDependencyInjection = listOf(
    appModule,
    savedSongDatabaseModule,
    savedSongRepositoryModule,
    weightAssignmentViewModelModule,
    lyricDisplayViewModelModule,
    savedSongsViewModelModule,
    selectedSavedSongViewModelModule,
    aboutPageViewModelModule
)