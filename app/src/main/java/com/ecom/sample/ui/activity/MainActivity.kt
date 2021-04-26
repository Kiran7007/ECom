package com.ecom.sample.ui.activity

import android.os.Bundle
import com.ecom.sample.R
import com.ecom.sample.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}