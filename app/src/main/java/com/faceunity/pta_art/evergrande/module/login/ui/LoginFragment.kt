package com.faceunity.pta_art.evergrande.module.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.faceunity.pta_art.R
import com.faceunity.pta_art.SelectStyleActivity
import com.faceunity.pta_art.evergrande.module.login.data.UserManager
import com.faceunity.pta_art.evergrande.utils.findNavController
import com.faceunity.pta_art.evergrande.utils.setTitle
import com.faceunity.pta_art.evergrande.utils.toast
import com.faceunity.pta_art.evergrande.utils.toastError
import com.faceunity.pta_art.evergrande.utils.transformers.Transformers
import com.rxjava.rxlife.RxLife

class LoginFragment : Fragment() {

    var editPhone: EditText? = null
    var editPsw: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setTitle(R.string.log_in)
    }

    private fun initView(view: View) {
        view.run {
            editPhone = findViewById(R.id.editUsername)
            editPsw = findViewById(R.id.editPassword)
            findViewById<Button>(R.id.btnLogin).setOnClickListener {
                checkLogin()
            }
            findViewById<TextView>(R.id.tvRegister).setOnClickListener {
                nav2Register()
            }
        }
    }

    private fun nav2Register() {
        val directions = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(directions)
    }

    private fun checkLogin() {
        val phone = editPhone?.text.toString()
        val password = editPsw?.text.toString()
        if (phone.isEmpty() || password.isEmpty()) {
            toast(R.string.input_empty)
            return
        }
        UserManager.login(phone, password)
                .compose(Transformers.asyncAndWaiting<Any>())
                .`as`(RxLife.asOnMain<Any>(viewLifecycleOwner, Lifecycle.Event.ON_STOP))
                .subscribe({
                    toast(R.string.login_success)
                    activity?.run {
                        startActivity(Intent(this, SelectStyleActivity::class.java))
                        finish()
                    }
                }, {
                    toastError(it)
                })
    }

}
