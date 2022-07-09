package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingridsantos.ceibatechnicaltest.databinding.FragmentPostsBinding
import com.ingridsantos.ceibatechnicaltest.presentation.users.adapter.PostAdapter
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
    }

    override fun onResume() {
        super.onResume()
        subscribeToPostState()
    }

    private fun subscribeToPostState() {
        job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            postsViewModel.postsFlow.collect(::handlePostState)
        }
    }

    private fun handlePostState(postState: PostsState) {
        when (postState) {
            PostsState.HideLoading -> binding.pgbPosts.visibility = View.GONE
            PostsState.Loading -> binding.pgbPosts.visibility = View.VISIBLE
            is PostsState.Success -> {
                postAdapter.submitList(postState.posts)
            }
            is PostsState.Error -> {
            }
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
