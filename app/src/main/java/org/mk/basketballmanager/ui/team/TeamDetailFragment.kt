package org.mk.basketballmanager.ui.team

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.databinding.TeamDetailFragmentBinding
import org.mk.basketballmanager.helpers.showImagePicker
import timber.log.Timber

class TeamDetailFragment : Fragment() {

    private val teamDetailViewModel: TeamDetailViewModel by activityViewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()

    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var keepData = false
    lateinit var binding: TeamDetailFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerImagePickerCallback()

        // Inflate the layout for this fragment
        binding = TeamDetailFragmentBinding.inflate(inflater, container, false)
        teamDetailViewModel.observableTeam.observe(viewLifecycleOwner, Observer {
            render()
            if(it.image.isNotEmpty()){
                Picasso.get()
                    .load(it.image)
                    .into(binding.image)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity

        binding.updateButton.text = resources.getString(R.string.update)
        binding.updateButton.setOnClickListener { view ->
            if(!binding.name.text.isNullOrEmpty()){
                teamDetailViewModel.updateTeam()
                teamViewModel.getTeam()
                findNavController().navigateUp()
            }
            else{
                binding.name.error = "Please enter a name"
            }
        }
        binding.buttonPickImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    private fun render() {
        binding.teamvm = teamDetailViewModel
        Timber.i("binding.teamvm == ${binding.teamvm}")
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            val image = result.data!!.data!!.toString()
                            teamDetailViewModel.updateImage(image)
                            keepData = true
                            Timber.i("${image}")
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        // Keepdata is only changed to true if image was picked
        if(!keepData){
            teamDetailViewModel.getTeam(loggedInViewModel.liveFirebaseUser.value!!.uid)
        }
        keepData = false
    }
}