package fr.isen.bechard.androidtoolbox.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bechard.androidtoolbox.R
import fr.isen.bechard.androidtoolbox.dataClass.RandomUser
import kotlinx.android.synthetic.main.activity_user_information.view.*

class RandomUserAdapter(private val rdnUserList: ArrayList<RandomUser>) :
    RecyclerView.Adapter<RandomUserAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ContactViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_random_user_cell, viewGroup, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val rdnUser = rdnUserList[position]
        holder.name.text = rdnUser.name?.fullName()
        holder.email.text = rdnUser.email
        holder.address.text = rdnUser.location?.address()
        Picasso.get().load(rdnUser.picture?.large).into(holder.avatar)
    }

    override fun getItemCount(): Int = rdnUserList.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.UserName
        var email: TextView = itemView.UserMail
        var address: TextView = itemView.UserAddress
        var avatar: ImageView = itemView.ImageUser
    }
}