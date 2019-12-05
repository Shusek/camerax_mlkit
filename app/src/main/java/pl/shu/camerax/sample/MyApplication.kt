package pl.shu.camerax.sample

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import timber.log.Timber

class MyApplication : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig(this)
    }
}
