package ru.kn_n.testapplication2.presentation.view.repository

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.kn_n.testapplication2.domain.containers.Repository

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoryView : MvpView{
    fun addToSaved(id: String, repo:Repository)
    fun deleteFromSaved(id: String, repo:Repository)
    fun back(id: String)
}