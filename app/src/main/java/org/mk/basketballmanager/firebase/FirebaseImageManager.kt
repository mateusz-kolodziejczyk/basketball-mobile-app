package org.mk.basketballmanager.firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber
import java.io.ByteArrayOutputStream

object FirebaseImageManager {

    var storage = FirebaseStorage.getInstance().reference

    fun updatePlayerImage(playerModel: PlayerModel, updating: Boolean){
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
                    uploadPlayerImage(playerModel, bitmap!!,updating)
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

    fun uploadPlayerImage(player: PlayerModel, bitmap: Bitmap, updating: Boolean) {
        // Get the data from an ImageView as bytes
        val imageRef = storage.child("photos").child("players").child("${player.id}.jpg")
        //val bitmap = (imageView as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        lateinit var uploadTask: UploadTask

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        imageRef.metadata.addOnSuccessListener { //File Exists
            if(updating) // Update existing Image
            {
                uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { ut ->
                    ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                        FirebaseDBManager.updatePlayerImage(player, task.result!!.toString())
                    }
                }
            }
        }.addOnFailureListener { //File Doesn't Exist
            uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener { ut ->
                ut.metadata!!.reference!!.downloadUrl.addOnCompleteListener { task ->
                    FirebaseDBManager.updatePlayerImage(player, task.result!!.toString())
                }
            }
        }
    }

    fun deletePlayerImage(id: String){
        val imageRef = storage.child("photos").child("players").child("${id}.jpg")
        imageRef.metadata.addOnSuccessListener { //File Exists
                val deleteTask = imageRef.delete()
        }.addOnFailureListener { //File Doesn't Exist, Don't do anything
        }
    }
}