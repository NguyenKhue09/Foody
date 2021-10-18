package com.khue.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.khue.foody.R
import com.khue.foody.bindingadapter.RecipesRowBinding
import com.khue.foody.databinding.FragmentOverviewBinding
import com.khue.foody.models.Result
import com.khue.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _mBinding: FragmentOverviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        _mBinding!!.mainImageView.load(myBundle.image)
        _mBinding!!.titleTextView.text  = myBundle.title
        _mBinding!!.likesTextView.text = myBundle.aggregateLikes.toString()
        _mBinding!!.timeTextView.text = myBundle.readyInMinutes.toString()
        // old code
//        myBundle?.summary.let {
//            val summary = Jsoup.parse(it!!).text()
//            _mBinding!!.summaryTextView.text = summary
//        }

        // new code
        RecipesRowBinding.parseHtml(_mBinding!!.summaryTextView, myBundle.summary)

        updateColors(myBundle.vegetarian, _mBinding!!.vegetarianTextView, _mBinding!!.vegetarianImageView)
        updateColors(myBundle.vegan, _mBinding!!.veganTextView, _mBinding!!.veganImageView)
        updateColors(myBundle.cheap, _mBinding!!.cheapTextView, _mBinding!!.cheapImageView)
        updateColors(myBundle.dairyFree, _mBinding!!.dairyFreeTextView, _mBinding!!.dairyFreeImageView)
        updateColors(myBundle.glutenFree, _mBinding!!.glutenFreeTextView, _mBinding!!.glutenFreeImageView)
        updateColors(myBundle.veryHealthy, _mBinding!!.healthyTextView, _mBinding!!.healthyImageView)

        return _mBinding!!.root
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

}