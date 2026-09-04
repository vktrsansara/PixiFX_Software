package com.vktrsansara.app.pixifx

import android.app.Application
import com.vktrsansara.app.pixifx.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class PixiFxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.INFO)
            androidContext(this@PixiFxApp)
        }
    }
}
