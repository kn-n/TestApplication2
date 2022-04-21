package ru.kn_n.testapplication2.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.kn_n.testapplication2.presentation.main.MainFragment
import ru.kn_n.testapplication2.presentation.repository.RepositoryFragment

object Screens {
    fun Main() = FragmentScreen {MainFragment()}
    fun Repository(fragment: RepositoryFragment) = FragmentScreen {fragment}
}