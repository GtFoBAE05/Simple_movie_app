package com.imannuel.simple_movie_app.ui.movie.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.imannuel.simple_movie_app.databinding.FragmentDiscoverMovieBinding
import com.imannuel.simple_movie_app.ui.adapter.LoadingStateAdapter
import com.imannuel.simple_movie_app.ui.base.BaseFragment
import com.imannuel.simple_movie_app.utilities.extension.gone
import com.imannuel.simple_movie_app.utilities.extension.showToast
import com.imannuel.simple_movie_app.utilities.extension.visible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DiscoverMovieFragment : BaseFragment() {

    private var _binding: FragmentDiscoverMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreId: String
    private lateinit var genreTitle: String

    private lateinit var adapter: DiscoverMovieAdapter

    private val viewModel: DiscoverMovieViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreId = requireArguments().getString("genreId").toString()
        genreTitle = requireArguments().getString("genreTitle").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.discoverMovieToolbar.title = "Discover Movie by ${genreTitle} Genre"
        binding.discoverMovieToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        checkNetworkConnection {
            setupAdapter()
            setupObserver()
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.discoverMovieByGenre(genreId).collectLatest { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setupAdapter() {
        binding.discoverMovieRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = DiscoverMovieAdapter {
        }
        binding.discoverMovieRv.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding.discoverMovieProgressBar.visible()
                }

                is LoadState.Error -> {
                    binding.discoverMovieProgressBar.gone()
                    showToast("Error: ${loadState.refresh}")
                }

                else -> {
                    binding.discoverMovieProgressBar.gone()
                }
            }
        }
    }

}