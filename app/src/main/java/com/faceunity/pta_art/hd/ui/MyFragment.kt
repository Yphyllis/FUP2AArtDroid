package com.faceunity.pta_art.hd.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import com.faceunity.pta_art.BuildConfig
import com.faceunity.pta_art.R
import com.faceunity.pta_art.evergrande.module.login.LoginActivity
import com.faceunity.pta_art.evergrande.module.login.data.UserManager
import com.faceunity.pta_art.evergrande.utils.toastError
import com.faceunity.pta_art.evergrande.utils.transformers.Transformers
import com.faceunity.pta_art.fragment.BaseFragment
import com.rxjava.rxlife.RxLife

class MyFragment : BaseFragment() {

    companion object {
        val TAG = "MyFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            onBackPressed()
        }
        view.findViewById<TextView>(R.id.tvLogout).setOnClickListener {
            logoutByClick()
        }
        view.findViewById<TextView>(R.id.tvVersion).text = "Version: ${BuildConfig.VERSION_NAME}"
    }

    private fun logoutByClick() {
        UserManager.logout()
                .compose(Transformers.asyncAndWaiting<Any>())
                .`as`(RxLife.asOnMain<Any>(viewLifecycleOwner, Lifecycle.Event.ON_STOP))
                .subscribe({
                    activity?.run {
                        startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                        finish()
                    }
                }, {
                    toastError(it)
                })
    }

}
