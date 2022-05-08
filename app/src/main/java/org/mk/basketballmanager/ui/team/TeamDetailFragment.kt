package org.mk.basketballmanager.ui.team

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.TeamDetailFragmentBinding
import org.mk.basketballmanager.helpers.showImagePicker
import timber.log.Timber

class TeamDetailFragment : Fragment() {

    private val teamViewModel: TeamDetailViewModel by activityViewModels()
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
        teamViewModel.observableTeam.observe(viewLifecycleOwner, Observer {
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

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Update Team Info")
        binding.updateButton.text = resources.getString(R.string.update)
        binding.updateButton.setOnClickListener { view ->
            teamViewModel.updateTeam()
            findNavController().navigateUp()
        }
        binding.buttonPickImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    private fun render() {
        binding.teamvm = teamViewModel
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
                            teamViewModel.updateImage(image)
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
            teamViewModel.getTeam(loggedInViewModel.liveFirebaseUser.value!!.uid)
        }
        keepData = false
    }
}