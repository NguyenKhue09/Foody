package com.khue.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.khue.foody.adapter.IngredientAdapter
import com.khue.foody.databinding.FragmentIngredientsBinding
import com.khue.foody.models.Result
import com.khue.foody.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientAdapter by lazy {
        IngredientAdapter()
    }
    private var _mBinding: FragmentIngredientsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }

        return _mBinding!!.root
    }

    private fun setupRecyclerView() {
        _mBinding!!.ingredientRecyclerView.adapter = mAdapter
        _mBinding!!.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }
}