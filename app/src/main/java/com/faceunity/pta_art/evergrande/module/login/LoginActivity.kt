package com.faceunity.pta_art.evergrande.module.login

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.faceunity.pta_art.R
import com.faceunity.pta_art.evergrande.utils.initCommonToolbar

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.login_activity)
        initCommonToolbar()
    }


}