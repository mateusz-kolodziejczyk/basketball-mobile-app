package org.mk.basketballmanager.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signinButton.setOnClickListener {
            val username = binding.username.text.toString()
            if(username.isNotEmpty()){
                navigateToMain(username)
            }
            else{
                Snackbar.make(it, R.string.error_no_username, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun navigateToMain(username: String){
        val action = SignInFragmentDirections.actionSignInFragmentToMainActivity(username)
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}