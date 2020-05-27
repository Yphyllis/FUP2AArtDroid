package com.faceunity.pta_art.evergrande.utils.transformers

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.faceunity.pta_art.R
import me.sjun.assholelibrary.Asshole
import org.jetbrains.anko.displayMetrics

/**
 * Created by yanghe on 2019-05-31.
 * <p>
 */
object Waiting {

    @Volatile
    private var showing = false

    fun initialize(context: Context) {
        Asshole.init(context.applicationContext as Application)
        Asshole.setAssViewCreator { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.widget_loading_view, null)
            view.minimumWidth = context.displayMetrics.widthPixels
            view.minimumHeight = context.displayMetrics.heightPixels
            view
        }
    }

    fun show() {
        if (showing) return
        Asshole.show()
        showing = true
    }

    fun showNewWindow() {
        if (showing) return
        Asshole.showNewWindow()
        showing = true
    }

    fun hide() {
        if (!showing) return
        Asshole.hide()
        showing = false
    }

}