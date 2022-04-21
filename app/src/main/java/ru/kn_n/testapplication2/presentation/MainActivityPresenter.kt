package ru.kn_n.testapplication2.presentation

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.presentation.navigation.Screens.Main
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val router: Router) : MvpPresenter<MainActivityView>() {
    fun startActivity() {
        router.navigateTo(Main())
    }
}