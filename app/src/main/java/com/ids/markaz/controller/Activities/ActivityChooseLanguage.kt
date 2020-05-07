package com.ids.controller.Activities

import android.content.Intent
import android.content.res.ColorStateList

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat


import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityChooseApp
import com.ids.markaz.controller.Activities.ActivityLogin
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.activity_choose_app.*
import kotlinx.android.synthetic.main.activity_choose_language.*

class ActivityChooseLanguage : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        init()
    }

    private fun init(){
        linearEnglish.setOnClickListener{setEnglish()}
        linearArabic.setOnClickListener{setArabic()}
        linearOk.setOnClickListener{
            MyApplication.isFirstTimeHint=false
            startActivity(Intent(this,ActivityLogin::class.java))
        }
    }


    private fun setEnglish(){


         linearEnglishRadio.setBackgroundResource(R.drawable.circle_secondary)
          ivEnglishRadio.setTint(R.color.white)



         linearArabicRadio.setBackgroundResource(R.drawable.circle_blue_lite_border)
         ivArabicRadio.setTint(R.color.blue_lite)

    }

    private fun setArabic(){

        ivEnglishRadio.setTint(R.color.blue_lite)
        linearEnglishRadio.setBackgroundResource(R.drawable.circle_blue_lite_border)

        ivArabicRadio.setTint(R.color.white)
        linearArabicRadio.setBackgroundResource(R.drawable.circle_secondary)

    }

    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
    }
}
