package org.mk.basketballmanager.ui.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import timber.log.Timber
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import org.mk.basketballmanager.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var loginBinding: LoginFragmentBinding
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                loginRegisterViewModel.setUser(it)
                loggedInViewModel.loggedOut.value = false
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private fun setupFirebaseUICallback(){
        loginBinding.firebaseUIButton.setOnClickListener{
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
            )

            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .setTheme(R.style.Theme_BasketballManager)
                .build()

            signInLauncher.launch(signInIntent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = LoginFragmentBinding.inflate(inflater, container, false)


        setupFirebaseUICallback()
        return loginBinding.root

    }

    override fun onStart() {
        super.onStart()

        loginRegisterViewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)

        loginRegisterViewModel.liveFirebaseUser.observe(this, Observer
        { firebaseUser ->
            if (firebaseUser != null) {
                navigateToMain()
            }
        })

        loginRegisterViewModel.firebaseAuthManager.errorStatus.observe(this, Observer
        { status -> checkStatus(status) })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun navigateToMain() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        NavHostFragment.findNavController(this).navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    private fun checkStatus(error: Boolean) {
        if (error)
            Toast.makeText(
                context,
                getString(R.string.auth_failed),
                Toast.LENGTH_LONG
            ).show()
    }


}