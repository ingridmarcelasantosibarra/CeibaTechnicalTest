package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridsantos.ceibatechnicaltest.databinding.FragmentUsersBinding
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.presentation.users.adapter.UsersAdapter
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.LocalUsersState
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.UsersState
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.FilterUsersViewModel
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.UsersViewModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : ScopeFragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val usersViewModel: UsersViewModel by viewModel()
    private val filterUsersViewModel: FilterUsersViewModel by viewModel()
    private val usersAdapter by lazy { UsersAdapter(::showPost) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.getLocalUsers()
        setUpAdapter()
        setupObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.scvFindUser.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        val users = filterUsersViewModel.getFilterReference(
                            filter = it,
                            users = usersAdapter.itemsUsers
                        )
                        showUsersFilter(users)
                    }

                    return true
                }
                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let {
                        val users = filterUsersViewModel.getFilterReference(
                            filter = it,
                            users = usersAdapter.itemsUsers
                        )
                        showUsersFilter(users)
                    }

                    return true
                }
            }
        )
    }

    private fun showUsersFilter(users: List<User>) {
        if (users.isNotEmpty()) {
            binding.incEmptyList.root.visibility = View.GONE
            binding.rcvUsers.visibility = View.VISIBLE
            usersAdapter.filterQuery(users)
        } else {
            binding.rcvUsers.visibility = View.GONE
            binding.incEmptyList.root.visibility = View.VISIBLE
        }
    }

    private fun setUpAdapter() {
        binding.rcvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    private fun setupObservers() {
        usersViewModel.localUserState.observe(viewLifecycleOwner) {
            when (it) {
                LocalUsersState.EmptyUsers -> {
                    usersViewModel.getUsers()
                }
                is LocalUsersState.SuccessUsers -> {
                    usersAdapter.itemsUsers = it.users
                }
            }
        }
        usersViewModel.usersState.observe(viewLifecycleOwner) {
            handleUsersState(it)
        }
    }

    private fun handleUsersState(usersState: UsersState) {
        binding.scvFindUser.setQuery(String(), false)
        when (usersState) {
            UsersState.HideLoading -> binding.pgbUsers.visibility = View.GONE
            UsersState.Loading -> {
                binding.incEmptyList.root.visibility = View.GONE
                binding.pgbUsers.visibility = View.VISIBLE
            }
            is UsersState.Success -> {
                binding.incEmptyList.root.visibility = View.GONE
                usersAdapter.itemsUsers = usersState.users
            }
            is UsersState.Error -> {
                binding.incEmptyList.root.visibility = View.GONE
                val builder = AlertDialog.Builder(context)
                builder.setCancelable(false)
                builder.setMessage(usersState.message)
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.cancel()
                }
                builder.create().show()
            }
            is UsersState.EmptyUsers -> {
                binding.rcvUsers.visibility = View.GONE
                binding.incEmptyList.root.visibility = View.VISIBLE
            }
        }
    }

    private fun showPost(userId: Int) {
        val action = UsersFragmentDirections.actionUserFragmentToPostFragment(
            userId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
