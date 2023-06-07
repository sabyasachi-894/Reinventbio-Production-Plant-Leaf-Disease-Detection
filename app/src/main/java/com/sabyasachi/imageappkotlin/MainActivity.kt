package com.sabyasachi.imageappkotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

private const val FILE_NAME="photo.jpg"
private const val REQUEST_CODE=42
private lateinit var photoFile : File
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTakePicture = findViewById<Button>(R.id.btnTakePicture)
        btnTakePicture.setOnClickListener{
            val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile=getPhotoFile(FILE_NAME)
            val fileProvider=FileProvider.getUriForFile(this,"com.sabyasachi.fileprovider",photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider)
            if(takePictureIntent.resolveActivity(packageManager) != null)
            {
                startActivityForResult(takePictureIntent,REQUEST_CODE)
            }
            else{
                Toast.makeText(this,"Unable to Open Camera",Toast.LENGTH_SHORT)
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageFile=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK)
        {
            //val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage=BitmapFactory.decodeFile(photoFile.absolutePath)
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(takenImage)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}