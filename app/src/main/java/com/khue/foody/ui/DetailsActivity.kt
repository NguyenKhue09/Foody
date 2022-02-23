package com.khue.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.khue.foody.R
import com.khue.foody.adapter.PagerAdapter
import com.khue.foody.data.database.entities.FavoritesEntity
import com.khue.foody.databinding.ActivityDetailsBinding
import com.khue.foody.ui.fragments.ingredients.IngredientsFragment
import com.khue.foody.ui.fragments.instructions.InstructionsFragment
import com.khue.foody.ui.fragments.overview.OverviewFragment
import com.khue.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import com.khue.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private var _mBinding: ActivityDetailsBinding? = null

    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem: MenuItem

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
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        // tab layout
        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        _mBinding!!.viewPager2.isUserInputEnabled = false
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
        } else if(item.itemId == R.id.save_to_favorite_menu && !recipeSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorite_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return true
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedRecipeId,
            args.result
        )

        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        recipeSaved = false
        savedRecipeId = 0
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            _mBinding!!.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favorite_menu)
        checkSavedRecipes(menuItem)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this
        ) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        Log.d("Vl", "${savedRecipe.result.id} + ${args.result.id}")
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailActivity", e.message.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
        changeMenuItemColor(menuItem, R.color.white)
    }
}