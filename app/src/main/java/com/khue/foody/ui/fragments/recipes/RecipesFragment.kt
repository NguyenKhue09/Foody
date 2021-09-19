package com.khue.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khue.foody.MainViewModel
import com.khue.foody.adapter.RecipesAdapter
import com.khue.foody.databinding.FragmentRecipesBinding
import com.khue.foody.util.Constants.Companion.API_KEY
import com.khue.foody.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var mBinding: FragmentRecipesBinding? = null
    private val mAdapter by lazy {
        RecipesAdapter()
    }
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentRecipesBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecyclerView()
        requestApiData()

        return mBinding!!.root
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        Log.i("vlvlvlvl", "$it")
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipesInformation"] = "true"
        queries["fillIngredients"] = "true"
        queries["addRecipeNutrition"] = "true"
        //?number=1&apiKey=81455f24ea284e348fc9bcd3822aa152&type=fingerfood&diet=vegan&addRecipesInformation=true&fillIngredients=true

        return queries
    }

    private fun setupRecyclerView() {
        mBinding!!.recyclerView.adapter = mAdapter
        mBinding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()

    }

    private fun showShimmerEffect() {
        mBinding!!.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mBinding!!.recyclerView.hideShimmer()
    }

}