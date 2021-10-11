package com.khue.foody.ui.fragments.foodjoke

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.khue.foody.R
import com.khue.foody.databinding.FragmentFoodJokeBinding
import com.khue.foody.util.Constants.Companion.API_KEY
import com.khue.foody.util.NetworkResult
import com.khue.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    // có thể lm giống trong recipe fagment cũng dc
    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(
            viewLifecycleOwner,
            {   response ->
                when(response) {
                    is NetworkResult.Success -> {
                        binding.foodJokeTextView.text = response.data?.text
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is NetworkResult.Loading -> {
                        Log.d("FoodJokeFragment", "Loading")
                    }
                }
            }
        )

        return binding.root
    }

    private  fun loadingFromCache() {

    }
}