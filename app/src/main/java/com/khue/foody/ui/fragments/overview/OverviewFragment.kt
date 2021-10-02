package com.khue.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.khue.foody.R
import com.khue.foody.databinding.FragmentOverviewBinding
import com.khue.foody.models.Result
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _mBinding: FragmentOverviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        _mBinding!!.mainImageView.load(myBundle?.image)
        _mBinding!!.titleTextView.text  = myBundle?.title
        _mBinding!!.likesTextView.text = myBundle?.aggregateLikes.toString()
        _mBinding!!.timeTextView.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary = Jsoup.parse(it!!).text()
            _mBinding!!.summaryTextView.text = summary
        }

        if (myBundle?.vegetarian == true) {
            _mBinding!!.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.vegan == true) {
            _mBinding!!.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            _mBinding!!.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            _mBinding!!.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            _mBinding!!.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true) {
            _mBinding!!.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            _mBinding!!.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return _mBinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }

}