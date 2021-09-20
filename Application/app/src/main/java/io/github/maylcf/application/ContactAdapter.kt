package io.github.maylcf.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.maylcf.application.model.Contact

class ContactAdapter(val contactsList: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindItem(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(contact: Contact) {
        val textName = itemView.findViewById<TextView>(R.id.contact_name)
        val textPhone = itemView.findViewById<TextView>(R.id.contact_phone)

        textName.text = contact.name
        textPhone.text = contact.phoneNumber
    }

}