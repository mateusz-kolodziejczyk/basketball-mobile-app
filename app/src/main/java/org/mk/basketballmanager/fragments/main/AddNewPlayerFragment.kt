package org.mk.basketballmanager.fragments.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentAddUpdatePlayerBinding
import org.mk.basketballmanager.helpers.showImagePicker
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber
import java.util.*
import android.widget.AdapterView

import android.widget.ArrayAdapter
import org.mk.basketballmanager.R
import org.mk.basketballmanager.enums.Position


class AddNewPlayerFragment : Fragment() {
    lateinit var binding: FragmentAddUpdatePlayerBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var imageURI: Uri = Uri.EMPTY
    var selectedPosition: Position = Position.None
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerImagePickerCallback()
        // Inflate the layout for this fragment
        binding = FragmentAddUpdatePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Add Player")
        val positions = Position.values()
        // Code from https://stackoverflow.com/a/21169383
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

        binding.btnAdd.text = resources.getString(R.string.add)
        binding.btnAdd.setOnClickListener { currentView ->
            val newPlayer = PlayerModel(
                id = UUID.randomUUID(),
                name = binding.name.text.toString(),
                image = imageURI,
                position = selectedPosition
            )
            if(newPlayer.name.isEmpty()){
                Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                app.players.add(newPlayer)
                navigateToPlayerList()
            }
        }
        binding.buttonPickImage.setOnClickListener { currentView ->
            showImagePicker(imageIntentLauncher)
        }
    }
    private fun navigateToPlayerList(){
        val action = AddNewPlayerFragmentDirections.actionAddNewPlayerFragmentToListAllPlayersFragment()
        NavHostFragment.findNavController(this).navigate(action)
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