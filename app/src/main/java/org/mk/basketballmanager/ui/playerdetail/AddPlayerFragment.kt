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
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.databinding.AddPlayerFragmentBinding
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.helpers.showImagePicker
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.ui.auth.LoginFragmentDirections
import org.mk.basketballmanager.ui.players.PlayerListDirections
import timber.log.Timber

class AddPlayerFragment : Fragment() {

    lateinit var binding: AddPlayerFragmentBinding
    private val addPlayerViewModel: AddPlayerViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var selectedPosition: Position = Position.None
    private val positions = Position.values()

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

        // Observe changes to viewmodel to update image
        addPlayerViewModel.observablePlayer.observe(viewLifecycleOwner){ player ->
            render()
            if(player.image.isNotEmpty()){
                Picasso.get()
                    .load(player.image)
                    .into(binding.image)
            }
            binding.positionSpinner.setSelection(positions.indexOf(player.position))
        }

        // Code from https://stackoverflow.com/a/21169383
        // This code allows using an enum for a spinner

        val positionAdapter: ArrayAdapter<Position>? =
            this.context?.let { ArrayAdapter<Position>(it, R.layout.position_spinner_text, positions) }

        val userSpinner = binding.positionSpinner
        userSpinner.adapter = positionAdapter
        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                addPlayerViewModel.updatePosition(parent?.selectedItem as Position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return binding.root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    // Pop back and clear player viewmodel
                    addPlayerViewModel.reset()
                    // findNavController().popBackStack()
                    navigateToPlayerList()
                }
            }
            false -> Timber.i("status is false")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonAdd.setOnClickListener { currentView ->
           if(binding.name.text.toString().isEmpty()){
               binding.name.error = "Please enter a name"
            }
            else{
                addPlayerViewModel.addPlayer()
            }
        }
        binding.buttonPickImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    private fun render() {
        binding.addPlayerVM = addPlayerViewModel
        Timber.i("binding.addPlayerVM == ${binding.addPlayerVM}")
    }
    private fun navigateToPlayerList(){
        val action = AddPlayerFragmentDirections.actionAddPlayerFragmentToPlayerList()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
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
                            addPlayerViewModel.updateImage(image)
                            Timber.i("${image}")
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}