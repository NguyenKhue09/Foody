package com.khue.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.khue.foody.R
import com.khue.foody.adapter.PagerAdapter
import com.khue.foody.databinding.ActivityDetailsBinding
import com.khue.foody.ui.fragments.ingredients.IngredientsFragment
import com.khue.foody.ui.fragments.instructions.InstructionsFragment
import com.khue.foody.ui.fragments.overview.OverviewFragment

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private var _mBinding: ActivityDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(_mBinding!!.root)

        setSupportActionBar(_mBinding!!.toolbar)
        _mBinding!!.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        // nhận safe args rồi cho vào cái này để chuyển cho tab
        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)
        Log.d("Vlvlvl", resultBundle.toString())

        // tab layout
        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        // tab layout
        _mBinding!!.viewPager2.apply {
            adapter = pagerAdapter
        }
        // tab layout
        TabLayoutMediator(_mBinding!!.tabLayout, _mBinding!!.viewPager2) {
            tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // qoay về trang trước
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}