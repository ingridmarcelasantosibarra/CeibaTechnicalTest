package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridsantos.ceibatechnicaltest.databinding.FragmentUsersBinding
import com.ingridsantos.ceibatechnicaltest.presentation.users.adapter.UsersAdapter
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.UsersState
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.UsersViewModel
import kotlinx.coroutines.Job
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : ScopeFragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val usersViewModel: UsersViewModel by viewModel()
    private val usersAdapter by lazy { UsersAdapter(::showPost) }
    private var job: Job? = null

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
        usersViewModel.getUsers()
        setUpAdapter()
        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onResume() {
        super.onResume()
        subscribeToUsersState()
    }

    private fun setUpAdapter() {
        binding.rcvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    private fun subscribeToUsersState() {
        job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            usersViewModel.usersFlow.collect(::handleUsersState)
        }
    }

    private fun handleUsersState(usersState: UsersState) {
        when (usersState) {
            UsersState.HideLoading -> binding.pgbUsers.visibility = View.GONE
            UsersState.Loading -> binding.pgbUsers.visibility = View.VISIBLE
            is UsersState.Success -> {
                usersAdapter.submitList(usersState.users)
            }
            is UsersState.Error -> {}
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

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }
}
