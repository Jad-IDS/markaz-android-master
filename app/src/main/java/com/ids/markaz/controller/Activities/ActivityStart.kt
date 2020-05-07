package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ids.controller.Activities.ActivityChooseLanguage

import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterStartPager
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import kotlinx.android.synthetic.main.activity_start.*


class ActivityStart : ActivityBase(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }

    private lateinit var adapter: AdapterStartPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }


    private fun init(){
         setAds()
      }


    private fun setAds(){
        adapter = AdapterStartPager(this, arrayListOf(),this)
        vpStart.adapter = adapter
        tlImage.setupWithViewPager(vpStart)

    }

    fun skip(v:View){
        MyApplication.isFirstTimeHint=false
         startActivity(Intent(this, ActivityChooseLanguage::class.java))

    }



}
