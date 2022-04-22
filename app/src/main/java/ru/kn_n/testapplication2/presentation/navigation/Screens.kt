package ru.kn_n.testapplication2.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.kn_n.testapplication2.presentation.view.main.MainFragment
import ru.kn_n.testapplication2.presentation.view.repository.RepositoryFragment
import ru.kn_n.testapplication2.presentation.view.signin.SignInFragment

object Screens {
    fun Main(fragment: MainFragment) = FragmentScreen {fragment}
    fun Repository(fragment: RepositoryFragment) = FragmentScreen {fragment}
    fun SignIn() = FragmentScreen {SignInFragment()}
}