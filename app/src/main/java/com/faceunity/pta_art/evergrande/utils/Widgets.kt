package com.faceunity.pta_art.evergrande.utils

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faceunity.pta_art.FUApplication
import com.faceunity.pta_art.R
import com.faceunity.pta_art.evergrande.net.NetException
import com.faceunity.pta_art.utils.ToastUtil
import kotlinx.android.synthetic.main.widget_toolbar.*

/**
 * extension 方法，便于给 activity 初始化 toolbar
 * 自动使用 {layout="@layout/widget_toolbar"} 中 id = toolbarWidgetCommon 的 toolbar 实例
 */
fun AppCompatActivity.initCommonToolbar(
        @DrawableRes homeResId: Int? = R.drawable.ic_back_new, onHomeClickListener: () -> Boolean = { false }
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

fun Fragment.findNavController(): NavController =
        NavHostFragment.findNavController(this)


fun Fragment.toast(@StringRes textRes: Int) {
    ToastUtil.showCenterToast(context, getString(textRes))
}

fun toastError(e:Throwable) {
    if (e is NetException) {
        ToastUtil.showCenterToast(FUApplication.getInstance(), "errorCode=${e.msgCode} & error is ${e.msg}")
    }else {
        ToastUtil.showCenterToast(FUApplication.getInstance(), e.toString())
    }
}
