package com.example.movieandusers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.commit451.coiltransformations.CropTransformation
import com.example.movieandusers.databinding.ItemUserBinding

class UserAdapter(private var userList: List<ItemUser>) :
    RecyclerView.Adapter<UserViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            holder.bind(userList[position])
        }

        override fun getItemCount(): Int = userList.size

        fun setMovies(data: List<ItemUser>) {
            userList = data
            notifyDataSetChanged()
        }
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemMovie: ItemUser) {
            with(binding) {
                labelName.text = itemMovie.name
                labelAddress.text = "Address: ${itemMovie.address}"
                labelEmail.text = "Email: ${itemMovie.email}"
                labelCelphone.text = "Phone: ${itemMovie.phone}"
                labelNumber.text = "Employee ID: ${itemMovie.id}"

                //if (itemMovie.image.isNotEmpty())
                imageView.load("https://picsum.photos/400")
            }
        }
    }