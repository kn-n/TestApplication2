package ru.kn_n.testapplication2.presentation.view

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kn_n.testapplication2.R
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.presentation.presenter.MainActivityPresenter
import ru.kn_n.testapplication2.utils.makeClient
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var activityPresenter: MainActivityPresenter

    @ProvidePresenter
    fun provideActivityPresenter() = Toothpick.openScope(Scopes.APP_SCOPE).getInstance(
        MainActivityPresenter::class.java)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@MainActivity, Toothpick.openScope(Scopes.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityPresenter.startActivity()
    }

    private val navigator = AppNavigator(this, R.id.container)

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        makeClient(this).signOut()
        super.onDestroy()
    }
}