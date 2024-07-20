package com.bedirhan.muuvi.feature.list_movies.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bedirhan.muuvi.databinding.FragmentHomeFeedBinding
import com.bedirhan.muuvi.feature.list_movies.presentation.adapter.HomeFeedPopularMovieRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeed : Fragment() {
    private var _binding: FragmentHomeFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeFeedViewModel by viewModels()
    private val recyclerAdapter: HomeFeedPopularMovieRecyclerAdapter by lazy {
        HomeFeedPopularMovieRecyclerAdapter()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewHomeFeed.adapter = recyclerAdapter
        viewModel.moviesLiveData.observe(viewLifecycleOwner) { articles ->
            recyclerAdapter.submitList(articles)
        }
        viewModel.getTopRatedMovies()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}