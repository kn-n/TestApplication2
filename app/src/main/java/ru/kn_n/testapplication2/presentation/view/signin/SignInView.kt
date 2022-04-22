package ru.kn_n.testapplication2.presentation.view.signin

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface SignInView : MvpView {
//    fun signIn()
    fun signInWithGoogle()
    fun showError(errorText:String)
}