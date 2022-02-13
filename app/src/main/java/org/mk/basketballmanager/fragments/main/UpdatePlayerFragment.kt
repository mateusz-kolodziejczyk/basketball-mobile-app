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
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentAddUpdatePlayerBinding
import org.mk.basketballmanager.helpers.showImagePicker
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PLAYER = "player"


class UpdatePlayerFragment : Fragment() {
    private var player: PlayerModel? = null
    lateinit var binding: FragmentAddUpdatePlayerBinding

    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var imageURI = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            player = it.getParcelable(ARG_PLAYER)
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

        activity.setActionBarTitle("Update Player")
        binding.btnAdd.text = resources.getString(R.string.update)
        player?.let{
            imageURI = it.image

            Picasso.get()
                .load(imageURI)
                .into(binding.image)

            binding.name.setText(it.name)
            binding.btnAdd.setOnClickListener { currentView ->
                val updatedPlayer = PlayerModel(
                    id = it.id,
                    name = binding.name.text.toString(),
                    image = imageURI
                )
                if(updatedPlayer.name.isEmpty()){
                    Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.players.update(updatedPlayer)
                    navigateToRoster()
                }
            }
            binding.buttonPickImage.setOnClickListener { currentView ->
                showImagePicker(imageIntentLauncher)
            }
        }
    }
    private fun navigateToRoster(){
        val action = UpdatePlayerFragmentDirections.actionUpdatePlayerFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(player: PlayerModel) =
            UpdatePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYER, player)
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