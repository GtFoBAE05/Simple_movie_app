package com.imannuel.simple_movie_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.imannuel.simple_movie_app.R
import com.imannuel.simple_movie_app.databinding.FragmentHomeBinding
import com.imannuel.simple_movie_app.ui.base.BaseFragment
import com.imannuel.simple_movie_app.utilities.extension.gone
import com.imannuel.simple_movie_app.utilities.extension.showToast
import com.imannuel.simple_movie_app.utilities.extension.visible
import com.imannuel.simple_movie_app.utilities.network.NetworkResult
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenresAdapter

    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeToolbar.title = "Movie Genres"

        checkNetworkConnection {
            setupAdapter()
            setupObserver()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genreState.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding.homeProgressBar.visible()
                        }

                        is NetworkResult.Success -> {
                            binding.homeProgressBar.gone()
                            adapter.setData(result.data.genres)
                        }

                        is NetworkResult.Error -> {
                            binding.homeProgressBar.gone()
                            showToast(result.msg)
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.homeGenresRv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = GenresAdapter {
            val bundle = Bundle()
            bundle.putString("genreId", it.id.toString())
            bundle.putString("genreTitle", it.name)
            findNavController().navigate(R.id.action_homeFragment_to_discoverMovieFragment, bundle)
        }
        binding.homeGenresRv.adapter = adapter
    }

}