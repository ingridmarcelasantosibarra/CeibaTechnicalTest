package com.ingridsantos.ceibatechnicaltest.presentation.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ingridsantos.ceibatechnicaltest.databinding.ItemUserBinding
import com.ingridsantos.ceibatechnicaltest.domain.entities.User

typealias showPosts = ((Int) -> Unit)

class UsersAdapter(
 private val showPosts: showPosts
) : ListAdapter<User, UsersAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), showPosts)
    }

    class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User, showPosts: showPosts) {
            binding.apply {
                txvEmail.text = item.email
                txvPhone.text = item.phone
                txvUsername.text = item.username
                txvShowPosts.setOnClickListener {
                    showPosts(item.id)
                }
            }
        }
    }
}

private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ) = oldItem == newItem
}
