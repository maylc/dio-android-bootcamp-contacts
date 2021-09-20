package io.github.maylcf.application

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.maylcf.application.model.Contact

class MainActivity : AppCompatActivity() {

    private val requestContact = 19

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val readContactsPermission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (readContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                requestContact
            )
        } else {
            setContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requestContact) {
            setContacts()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setContacts() {
        val contactList: ArrayList<Contact> = ArrayList()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactList.add(
                    Contact(
                        name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    )
                )
            }
            cursor.close()
        }

        val adapter = ContactAdapter(contactList)

        val contactListView = findViewById<RecyclerView>(R.id.contacts_recycler_view)
        contactListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        contactListView.adapter = adapter
    }
}