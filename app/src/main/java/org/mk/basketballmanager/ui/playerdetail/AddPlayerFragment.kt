package org.mk.basketballmanager.ui.playerdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.databinding.AddPlayerFragmentBinding
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.fragments.main.UpdatePlayerFragment
import org.mk.basketballmanager.helpers.showImagePicker
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

class AddPlayerFragment : Fragment() {

    lateinit var binding: AddPlayerFragmentBinding
    private val addPlayerViewModel: AddPlayerViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var imageURI: Uri = Uri.EMPTY
    var selectedPosition: Position = Position.None
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerImagePickerCallback()
        // Inflate the layout for this fragment
        binding = AddPlayerFragmentBinding.inflate(inflater, container, false)
        addPlayerViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        return binding.root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.error_add_player), Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity
        // Set action bar title

        activity.setActionBarTitle("Add Player")
        val positions = Position.values()
        // Code from https://stackoverflow.com/a/21169383
        // This code allows using an enum for a spinner
        val positionAdapter: ArrayAdapter<Position>? =
            this.context?.let { ArrayAdapter<Position>(it, R.layout.position_spinner_text, positions) }

        val userSpinner = binding.positionSpinner
        userSpinner.adapter = positionAdapter
        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedPosition = parent.selectedItem as Position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.buttonAdd.setOnClickListener { currentView ->
            val newPlayer = PlayerModel(
                name = binding.name.text.toString(),
                position = selectedPosition,
            )
           if(newPlayer.name.isEmpty()){
                Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                addPlayerViewModel.addPlayer(newPlayer)
                navigateToPlayerList()
            }
        }
        binding.buttonPickImage.setOnClickListener { currentView ->
            showImagePicker(imageIntentLauncher)
        }
    }
    private fun navigateToPlayerList(){
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            UpdatePlayerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            imageURI = result.data!!.data!!
                            Picasso.get()
                                .load(imageURI)
                                .into(binding.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}