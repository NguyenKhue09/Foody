package com.khue.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.khue.foody.R
import com.khue.foody.viewmodels.MainViewModel
import com.khue.foody.adapter.RecipesAdapter
import com.khue.foody.databinding.FragmentRecipesBinding
import com.khue.foody.util.NetworkListener
import com.khue.foody.util.NetworkResult
import com.khue.foody.util.observeOnce
import com.khue.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy {
        RecipesAdapter()
    }

    private lateinit var networkListener: NetworkListener

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        //recipes-menu
        setHasOptionsMenu(true)

        setupRecyclerView()

        recipesViewModel.readBackOnline.asLiveData().observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })

        // thay launch bằng launchWhen.. để ko bị crash app do chuyển sang
        // fragment khác khi tắt wifi đi
       lifecycleScope.launchWhenStarted {
           networkListener = NetworkListener()
           networkListener.checkNetworkAvailability(requireContext())
               .collect { status ->
                   Log.d("NetworkListener", status.toString())
                   recipesViewModel.networkStatus = status
                   recipesViewModel.showNetworkStatus()
                   readDatabase()
               }
       }

        binding.recipesFab.setOnClickListener {
            if(recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }

        }


        return _binding!!.root
    }

    private fun setupRecyclerView() {
        _binding!!.recyclerView.adapter = mAdapter
        _binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()

    }

    // recipes-menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    // search
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    // search
    override fun onQueryTextChange(newText: String?): Boolean {
//        if (newText != null) {
//            searchApiData(newText)
//        }
       return true
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                    // fix lỗi ở video 147(chỉ lưu dữ liệu thật sự khi
                    // có dữ liệu trả về)
                    recipesViewModel.saveMealAndDietType()
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(
          viewLifecycleOwner, { response ->
                when(response) {
                    is NetworkResult.Success -> {
                        hideShimmerEffect()
                        val foodRecipe = response.data
                        foodRecipe?.let {
                            mAdapter.setData(it)
                        }
                    }
                    is NetworkResult.Error -> {
                        hideShimmerEffect()
                        loadDataFromCache()
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

          }
        )
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }


    private fun showShimmerEffect() {
        _binding!!.shimmerFrameLayout.startShimmer()
        _binding!!.recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        _binding!!.shimmerFrameLayout.stopShimmer()
        _binding!!.shimmerFrameLayout.visibility = View.INVISIBLE
        _binding!!.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}