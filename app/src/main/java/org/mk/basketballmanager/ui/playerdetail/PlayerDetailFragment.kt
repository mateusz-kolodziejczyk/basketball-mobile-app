package org.mk.basketballmanager.ui.playerdetail

import android.content.Intent
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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.PlayerDetailFragmentBinding
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.helpers.showImagePicker
import timber.log.Timber

class PlayerDetailFragment : Fragment() {

    lateinit var binding: PlayerDetailFragmentBinding
    private val args by navArgs<PlayerDetailFragmentArgs>()
    private val playerDetailViewModel: PlayerDetailViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var selectedPosition: Position = Position.None
    private val positions = Position.values()
    private var keepData = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerImagePickerCallback()
        // Inflate the layout for this fragment
        binding = PlayerDetailFragmentBinding.inflate(inflater, container, false)

        // Observe changes to viewmodel to update image
        playerDetailViewModel.observablePlayer.observe(viewLifecycleOwner){ player ->
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
                playerDetailViewModel.updatePosition(parent?.selectedItem as Position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

        binding.buttonUpdate.setOnClickListener { currentView ->
            if(binding.name.text.toString().isEmpty()){
                Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                playerDetailViewModel.updatePlayer()
                findNavController().navigateUp()
            }
        }
        binding.buttonPickImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    private fun render() {
        binding.playerDetailVm = playerDetailViewModel
        Timber.i("binding.addPlayerVM == ${binding.playerDetailVm}")
    }
    private fun navigateToPlayerList(){
        val action = AddPlayerFragmentDirections.actionAddPlayerFragmentToPlayerList()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if(!keepData){
            playerDetailViewModel.getPlayer(args.id)
        }
        keepData = false
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
                            playerDetailViewModel.updateImage(image)
                            keepData = true
                            Timber.i("Image path: ${image}")
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}