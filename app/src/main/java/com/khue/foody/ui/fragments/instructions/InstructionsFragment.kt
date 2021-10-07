package com.khue.foody.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.khue.foody.R
import com.khue.foody.databinding.FragmentInstructionsBinding
import com.khue.foody.models.Result
import com.khue.foody.util.Constants


class InstructionsFragment : Fragment() {

    private var _mBinding: FragmentInstructionsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        _mBinding!!.instructionsWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle!!.sourceUrl
        _mBinding!!.instructionsWebView.loadUrl(websiteUrl)

        return _mBinding!!.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }

}