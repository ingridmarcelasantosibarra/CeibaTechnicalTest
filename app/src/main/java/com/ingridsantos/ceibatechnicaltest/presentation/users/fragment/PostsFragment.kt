package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridsantos.ceibatechnicaltest.databinding.FragmentPostsBinding
import com.ingridsantos.ceibatechnicaltest.presentation.users.adapter.PostAdapter
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.InfoUserState
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.PostsState
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.PostsViewModel
import kotlinx.coroutines.Job
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : ScopeFragment() {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val postsViewModel: PostsViewModel by viewModel()
    private val postAdapter by lazy { PostAdapter() }
    private var job: Job? = null
    var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PostsFragmentArgs.fromBundle(requireArguments()).let {
            userId = it.userId
        }
        postsViewModel.getPosts(userId)
        setUpAdapter()
        initObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.tbPosts.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handlePostState(postState: PostsState) {
        when (postState) {
            PostsState.HideLoading -> binding.pgbPosts.visibility = View.GONE
            PostsState.Loading -> binding.pgbPosts.visibility = View.VISIBLE
            is PostsState.Success -> {
                postAdapter.submitList(postState.posts)
                postsViewModel.getUser(userId)
            }
            is PostsState.Error -> {
            }
        }
    }

    private fun initObservers() {
        postsViewModel.infoUserState.observe(viewLifecycleOwner) {
            if (it is InfoUserState.SuccessUser) {
                with(binding) {
                    txvPhonePost.text = it.user.phone
                    txvEmailPost.text = it.user.email
                    txvUsernamePost.text = it.user.username
                }
            }
        }
        postsViewModel.postsState.observe(viewLifecycleOwner) {
            handlePostState(it)
        }
    }
    private fun setUpAdapter() {
        binding.rcvPost.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
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
