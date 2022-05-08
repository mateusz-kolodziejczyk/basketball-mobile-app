package org.mk.basketballmanager.firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.mk.basketballmanager.models.Identifiable
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import timber.log.Timber
import java.io.ByteArrayOutputStream

object FirebaseImageManager {

    var storage = FirebaseStorage.getInstance().reference

    fun updatePlayerImage(playerModel: PlayerModel){
        val image = playerModel.image
        // Return early if theres is no image
        if(image.isEmpty()){
            return
        }
        Picasso.get().load(image)
            .resize(200, 200)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("DX onBitmapLoaded $bitmap")
                    uploadImage(
                        playerModel,
                        bitmap!!,
                        { player, uri ->
                            FirebaseDBManager.updatePlayerImage(player as PlayerModel, uri)
                        },
                        "player"
                    )
                    //imageView.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("DX onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Timber.i("DX onPrepareLoad $placeHolderDrawable")
                    //uploadImageToFirebase(userid, defaultImageUri.value,updating)
                }
            })
    }

    fun updateTeamImage(teamModel: TeamModel){
        val image = teamModel.image
        // Return early if theres is no image
        if(image.isEmpty()){
            return
        }
        Picasso.get().load(image)
            .resize(200, 200)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?,
                                            from: Picasso.LoadedFrom?
                ) {
                    Timber.i("DX onBitmapLoaded $bitmap")
                    uploadImage(
                        teamModel,
                        bitmap!!,
                        { team, uri ->
                            FirebaseDBManager.updateTeamImage(team as TeamModel, uri)
                        },
                        "team"
                    )
                    //imageView.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: java.lang.Exception?,
                                            errorDrawable: Drawable?) {
                    Timber.i("DX onBitmapFailed $e")
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Timber.i("DX onPrepareLoad $placeHolderDrawable")
                    //uploadImageToFirebase(userid, defaultImageUri.value,updating)
                }
            })
    }

    // Generalise the upload image function using an identifiable interface(i.e. containing an id)
    fun uploadImage(
        identifiable: Identifiable,
        bitmap: Bitmap,
        callback: (Identifiable, String) -> Unit,
        directory: String
    ) {
        // Get the data from an ImageView as bytes
        val imageRef = storage.child("photos").child(directory).child("${identifiable.id}.jpg")
        //val bitmap = (imageView as BitmapDrawable).bitmap

        lateinit var uploadTask: UploadTask

        val data = compressBitmap(bitmap)

        imageRef.metadata.addOnSuccessListener { //File Exists
                uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { ut ->
                    ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                        callback(identifiable, task.result.toString())
                    }
                }
        }.addOnFailureListener { //File Doesn't Exist
            uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener { ut ->
                ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                    callback(identifiable, task.result.toString())
                }
            }
        }
    }

    private fun compressBitmap(bitmap: Bitmap) :ByteArray{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun deletePlayerImage(id: String){
        val imageRef = storage.child("photos").child("player").child("${id}.jpg")
        imageRef.metadata.addOnSuccessListener { //File Exists
                val deleteTask = imageRef.delete()
        }.addOnFailureListener { //File Doesn't Exist, Don't do anything
        }
    }

    fun deleteTeamImage(id: String){
        val imageRef = storage.child("photos").child("team").child("${id}.jpg")
        imageRef.metadata.addOnSuccessListener { //File Exists
            val deleteTask = imageRef.delete()
        }.addOnFailureListener { //File Doesn't Exist, Don't do anything
        }
    }
}