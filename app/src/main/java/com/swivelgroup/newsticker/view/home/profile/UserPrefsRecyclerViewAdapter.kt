package com.swivelgroup.newsticker.view.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swivelgroup.newsticker.databinding.ItemUserPrefBinding

class UserPrefsRecyclerViewAdapter(private val userPrefs: ArrayList<String>) :
    RecyclerView.Adapter<UserPrefsRecyclerViewAdapter.UserPrefViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPrefViewHolder {
        val context = parent.context

        val inflater = LayoutInflater.from(context)
        val contactView = ItemUserPrefBinding.inflate(
            inflater, parent, false)


        return UserPrefViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return userPrefs.size
    }

    override fun onBindViewHolder(viewHolder: UserPrefViewHolder, position: Int) {

        if (userPrefs.size > 0) {
            val itemView = viewHolder.userPrefBinding
            itemView.txtUserPref.text = userPrefs[position]
        }
    }

    inner class UserPrefViewHolder(val userPrefBinding: ItemUserPrefBinding) :
        RecyclerView.ViewHolder(userPrefBinding.root)
}