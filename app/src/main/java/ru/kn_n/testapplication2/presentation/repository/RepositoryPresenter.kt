package ru.kn_n.testapplication2.presentation.repository

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.presentation.navigation.Screens.Main
import javax.inject.Inject

@InjectViewState
class RepositoryPresenter @Inject constructor(private val router: Router) :  MvpPresenter<RepositoryView>(){
    fun backToMain(){
        router.backTo(Main())
    }
}