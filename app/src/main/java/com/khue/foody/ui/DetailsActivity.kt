package com.khue.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.khue.foody.R
import com.khue.foody.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private var _mBinding: ActivityDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(_mBinding!!.root)

        setSupportActionBar(_mBinding!!.toolbar)
        _mBinding!!.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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