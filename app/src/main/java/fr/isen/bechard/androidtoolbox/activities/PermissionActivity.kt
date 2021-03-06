package fr.isen.bechard.androidtoolbox.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bechard.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity() {

    val contacts = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        getContacts()

        contactRecycler.adapter = ContactAdapter(contacts.sorted())
        contactRecycler.layoutManager = LinearLayoutManager(this)


        imageCamera.setOnClickListener {
            showPictureDialog();
        }
    }

    private fun showPictureDialog() {
        val pictureDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(
            "Select photo from gallery",
            "Capture photo from camera"
        )
        pictureDialog.setItems(pictureDialogItems,
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> pickImageFromGallery()
                    1 -> takePictureIntent()
                }
            })
        pictureDialog.show()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, 1001)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            imageCamera.setImageURI(data?.data)
        }else if (resultCode == Activity.RESULT_OK && requestCode == 1001) {
            var bmp = data?.extras?.get("data") as Bitmap
            imageCamera.setImageBitmap(bmp)
        }
    }

    private fun getContacts() {
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add("$name")
            }
        } else {
            Toast.makeText(applicationContext, "No contacts available!", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
    }
}