package org.mk.basketballmanager.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.mk.basketballmanager.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.pickImage.toString())
    intentLauncher.launch(chooseFile)
}