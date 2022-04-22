package ru.kn_n.testapplication2.presentation.presenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.data.db.SQLiteHelper
import ru.kn_n.testapplication2.domain.model.User
import ru.kn_n.testapplication2.presentation.navigation.Screens.Main
import ru.kn_n.testapplication2.presentation.view.main.MainFragment
import ru.kn_n.testapplication2.presentation.view.main.USER_ID
import ru.kn_n.testapplication2.presentation.view.signin.SignInView
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(private val router: Router) : MvpPresenter<SignInView>() {

//    fun signIn(id: String) {
//        router.navigateTo(Main())
//    }

//    fun signIn(id: String, context: Context) {
//        val user = User(id)
//        val helper = SQLiteHelper(context)
//        helper.insertUser(user)
//        router.navigateTo(Main())
//    }

    fun signInWithGoogle(result: ActivityResult, context: Context){
        val data: Intent? = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val helper = SQLiteHelper(context)
            val account = task.getResult(ApiException::class.java)

            val googleId = account.id.toString()
            val user = User(googleId)

            helper.insertUser(user)

            val fragment = MainFragment()
            fragment.arguments = Bundle().apply { putString(USER_ID, googleId) }
            router.navigateTo(Main(fragment))

        } catch (e: ApiException) {
            viewState.showError("Google sign in failed $e")
        }
    }
}