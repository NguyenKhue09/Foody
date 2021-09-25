package com.khue.foody.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// https://www.udemy.com/course/modern-food-recipes-app-android-development-with-kotlin/learn/lecture/22965714#content

/**Sửa lỗi retrieve data nhiều lần trong video trên vì observer thấy
 * data thay đổi là get data*/
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }

    })
}