package com.faceunity.pta_art.evergrande.utils

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.faceunity.pta_art.R

/**
 * extension 方法，便于给 activity 初始化 toolbar
 * 自动使用 {layout="@layout/widget_toolbar"} 中 id = toolbarWidgetCommon 的 toolbar 实例
 */
fun AppCompatActivity.initCommonToolbar(
        @DrawableRes homeResId: Int? = R.drawable.ic_back, onHomeClickListener: () -> Boolean = { false }
) {
    if (this.toolbarWidgetCommon == null) {
        throw IllegalStateException("Please include  layout=\"@layout/widget_toolbar\" to your layout xml")
    }
    setSupportActionBar(toolbarWidgetCommon)
    supportActionBar?.let {
        kotlin.with(it) {
            setDisplayHomeAsUpEnabled(true)
            homeResId?.run {
                setHomeAsUpIndicator(this)
            }
        }
    }
    title = "" // 默认页面无标题

}

fun Fragment.setTitle(@StringRes id: Int) = activity?.setTitle(id)
fun Fragment.setTitle(title: String) {
    activity?.title = title
}

fun Fragment.setNavigationIcon(@DrawableRes id: Int) {
    activity?.run {
        (this as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(id)
    }
}