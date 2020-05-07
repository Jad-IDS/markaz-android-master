package com.ids.markaz.controller.Activities


import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.AdapterView
import com.google.firebase.iid.FirebaseInstanceId
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterNotifications
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.*
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.activity_settings.*


import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception


class ActivitySettings : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {

    }

    private lateinit var adapterNotifications: AdapterNotifications
    private var arrayNotifications=java.util.ArrayList<Notifications>()
    private var arrayCurrenciesSpinner= java.util.ArrayList<ItemSpinner>()
    private var selectedCurrencyId=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        setSpinnerCurrencies()
        try{
        val item: ItemSpinner? = arrayCurrenciesSpinner.find { it.id == MyApplication.currencyId }
        spCurrencies.setSelection(arrayCurrenciesSpinner.indexOf(item))}catch (e:Exception){}



    }

    private fun init(){
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.settings)


        btBack.setOnClickListener{
            super.onBackPressed()
        }

        swShowNotifications.isChecked = MyApplication.enableNotifications
        swBiometricLogin.isChecked = MyApplication.enableBiometric

        swShowNotifications.setOnCheckedChangeListener { buttonView, isChecked ->
            MyApplication.enableNotifications = isChecked
         }

        swBiometricLogin.setOnCheckedChangeListener { buttonView, isChecked ->
            MyApplication.enableBiometric = isChecked
        }

    }




    private fun setSpinnerCurrencies(){

        arrayCurrenciesSpinner.clear()

        for (i in MyApplication.arrayCurrencies.indices)
            arrayCurrenciesSpinner.add(ItemSpinner(
                MyApplication.arrayCurrencies[i].id,
                MyApplication.arrayCurrencies[i].nameEn))



        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item, arrayCurrenciesSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spCurrencies!!.adapter = adapter;
        spCurrencies!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedCurrencyId=item!!.id!!
                MyApplication.currencyId=item.id!!


            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }

}
