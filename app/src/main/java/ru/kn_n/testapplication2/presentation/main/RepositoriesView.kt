package ru.kn_n.testapplication2.presentation.main

import ru.kn_n.testapplication2.domain.containers.Repository
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoriesView: MvpView {
    fun search(query: String)
    fun searchInBD(query: String)
    fun showResult(listRepository: ArrayList<Repository>)
    fun showError(t: Throwable)
    fun signOut()
    fun deleteAllSaved()
}