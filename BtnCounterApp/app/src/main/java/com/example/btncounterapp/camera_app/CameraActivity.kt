package com.example.btncounterapp.camera_app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.btncounterapp.R

class CameraActivity : AppCompatActivity() {


    var imageAsBase64: String = ""


    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    var imagePrev: ImageView? = null
    var image_uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        imagePrev = findViewById<ImageView>(R.id.image_prev)


    }


    //    open camera
    fun openCamera(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {
                //permission was not enabled
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission,
                    PERMISSION_CODE
                )
            } else {
                //permission already granted
                open()
            }
        } else {
            //system os is < marshmallow
            open()
        }
    }


    private fun open() {
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        //camera intent
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
//
//

//        TAKE IMAGE
//        var values = ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "NEW PIC")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Description")
//        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
//        startActivityForResult(cameraIntent, IMAGE_PICK_CODE)

//        FROM GALLERY

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == IMAGE_PICK_CODE)
                    imagePrev?.setImageURI(data?.data)
//                    image_prev.setImageURI(image_uri)
            }
        }
    }


}
