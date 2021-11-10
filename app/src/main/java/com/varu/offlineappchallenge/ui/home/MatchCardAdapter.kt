package com.varu.offlineappchallenge.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.varu.offlineappchallenge.R
import com.varu.offlineappchallenge.data.models.DateDetail
import com.varu.offlineappchallenge.data.models.Location
import com.varu.offlineappchallenge.data.models.Name
import com.varu.offlineappchallenge.data.models.Person

import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import java.util.*

// TODO: add paging adapter to fetch next set of cards
class MatchCardAdapter(private val itemClickListener: (Person, MatchCardButtonAction) -> Unit) :
    ListAdapter<Person, MatchCardAdapter.AdapterVH>(
        object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return Objects.equals(oldItem.id, newItem.id)
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return Objects.equals(oldItem.id, newItem.id)
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_matches, parent, false)
        return AdapterVH(view)
    }

    override fun onBindViewHolder(holder: AdapterVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class AdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.findViewById(R.id.txt_name)
        private val txtAge: TextView = itemView.findViewById(R.id.txt_age)
        private val txtCountry: TextView = itemView.findViewById(R.id.txt_country)
        private val btnDecline: FloatingActionButton = itemView.findViewById(R.id.fab_decline)
        private val btnAccept: FloatingActionButton = itemView.findViewById(R.id.fab_accept)
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_profile_pic)

        fun onBind(person: Person) {
            txtName.text = person.name?.displayName()
            txtAge.text = person.dob?.displayAge()
            txtCountry.text = person.location?.displayLocation()

            person.picture?.large?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .centerCrop()
                    .into(imgProfile)
            }

            btnAccept.setOnClickListener {
                itemClickListener(person, MatchCardButtonAction.ACCEPT)
            }

            btnDecline.setOnClickListener {
                itemClickListener(person, MatchCardButtonAction.DECLINE)
            }
        }
    }
}

fun Name.displayName(): String {
    return "${first ?: ""} ${last ?: ""}"
}

fun DateDetail.displayAge(): String {
    return "${age?.toInt()} Yrs"
}

fun Location.displayLocation(): String {
    return "$city, $country"
}