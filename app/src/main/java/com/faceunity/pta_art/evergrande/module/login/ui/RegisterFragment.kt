package com.faceunity.pta_art.evergrande.module.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.faceunity.pta_art.R
import com.faceunity.pta_art.evergrande.module.login.data.UserService
import com.faceunity.pta_art.evergrande.utils.findNavController
import com.faceunity.pta_art.evergrande.utils.setTitle
import com.faceunity.pta_art.evergrande.utils.toast
import com.faceunity.pta_art.evergrande.utils.toastError
import com.faceunity.pta_art.evergrande.utils.transformers.Transformers
import com.rxjava.rxlife.RxLife

class RegisterFragment : Fragment() {

    var btnRegister: Button? = null
    var editPhone: EditText? = null
    var editPsw: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.regitster_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setTitle(R.string.register)
    }

    private fun initView(view: View) {
        view.run {
            btnRegister = findViewById(R.id.btnRegister)
            editPhone = findViewById(R.id.editPhone)
            editPsw = findViewById(R.id.editPassword)
            btnRegister?.setOnClickListener {
                checkRegister()
            }
        }
    }

    private fun checkRegister() {
        val phone = editPhone?.text.toString()
        val password = editPsw?.text.toString()
        if (phone.isEmpty() || password.isEmpty()) {
            toast(R.string.input_empty)
            return
        }
        UserService.register(phone, password)
                .compose(Transformers.waiting())
                .`as`(RxLife.asOnMain(viewLifecycleOwner, Lifecycle.Event.ON_STOP))
                .subscribe({
                    toast(R.string.register_success)
                    findNavController().popBackStack()
                }, {
                    toastError(it)
                })
    }

}