package com.example.btncounterapp.data_binding_view_binding_app

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    if (number == 0)
        view.visibility = View.GONE
    else
        view.visibility = View.VISIBLE
}


@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)

}

