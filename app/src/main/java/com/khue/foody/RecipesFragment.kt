package com.khue.foody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khue.foody.databinding.FragmentRecipesBinding


class RecipesFragment : Fragment() {

    private var mBinding: FragmentRecipesBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentRecipesBinding.inflate(inflater, container, false)

        mBinding!!.recyclerView.showShimmer()

        return mBinding!!.root
    }


}