package ru.kn_n.testapplication2.presentation.presenter

import android.content.Context
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.kn_n.testapplication2.presentation.navigation.Screens
import ru.kn_n.testapplication2.presentation.view.main.MainView
import ru.kn_n.testapplication2.utils.makeClient
import javax.inject.Inject

class MainPresenter @Inject constructor(private val router: Router) : MvpPresenter<MainView>()  {

    fun signOut(context: Context){
        makeClient(context).signOut()
        router.navigateTo(Screens.SignIn())
    }
}