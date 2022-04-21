package ru.kn_n.testapplication2.presentation.repository

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoryView : MvpView{
    fun showInformation()
    fun addToSaved()
    fun deleteFromSaved()
    fun back()
}