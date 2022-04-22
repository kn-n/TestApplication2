package ru.kn_n.testapplication2.presentation.view.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kn_n.testapplication2.databinding.FragmentSigninBinding
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.presentation.presenter.SignInPresenter
import ru.kn_n.testapplication2.utils.makeClient
import toothpick.Toothpick

class SignInFragment : MvpAppCompatFragment(), SignInView {

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @ProvidePresenter
    fun providerSignInPresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(SignInPresenter::class.java)

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)

        binding.signInWithGoogle.setOnClickListener { signInWithGoogle() }

        return binding.root
    }

    private val launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            signInPresenter.signInWithGoogle(result, requireContext())
        }

    override fun signInWithGoogle() {
        val context: Context = requireContext()
        mGoogleSignInClient = makeClient(context)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        launchSomeActivity.launch(signInIntent)
    }

    override fun showError(errorText: String) {
        Log.d("api", errorText)
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }
}