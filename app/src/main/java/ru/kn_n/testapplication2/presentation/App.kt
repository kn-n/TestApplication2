package ru.kn_n.testapplication2.presentation

import android.app.Application
import androidx.annotation.VisibleForTesting
import ru.kn_n.testapplication2.BuildConfig
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.di.appModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope(Toothpick.openScope(Scopes.APP_SCOPE))
    }

    @VisibleForTesting
    fun initAppScope(appScope: Scope){
        appScope.installModules(
            appModule(this)
        )
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG){
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }
}