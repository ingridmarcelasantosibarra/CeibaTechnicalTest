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
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    var listFilter: List<User> = emptyList()
        private set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemsUsers: List<User> = mutableListOf()
        set(value) {
            field = value
            listFilter = value
        }

    fun filterQuery(orders: List<User>) {
        listFilter = orders
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(showPosts, listFilter)
    }

    open class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(showPosts: showPosts, list: List<User>) {
            val item = list[adapterPosition]
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

    override fun getItemCount(): Int {
        return listFilter.size
    }
}
