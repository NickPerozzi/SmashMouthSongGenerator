package com.perozzi_package.smashmouthsonggenerator.koin

import android.app.Application
import com.perozzi_package.smashmouthsonggenerator.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class someBODY: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@someBODY)
            modules(listOf(appModule))
        }
    }
}
