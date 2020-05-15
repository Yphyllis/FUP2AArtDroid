package com.faceunity.pta_art.hd.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.faceunity.pta_art.R
import com.faceunity.pta_art.fragment.BaseFragment

class MyFragment : BaseFragment() {

    companion object{
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
    }

}
