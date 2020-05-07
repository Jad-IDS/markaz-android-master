package com.ids.markaz.controller.Activities


import android.os.Bundle

import android.view.View
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterNotifications
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase

import com.ids.markaz.model.*


import kotlinx.android.synthetic.main.toolbar.*


class ActivityNotificationInside : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {

    }



    private lateinit var adapterNotifications: AdapterNotifications
    private var arrayNotifications=java.util.ArrayList<Notifications>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_inside)
        init()



    }

    private fun init(){
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.notificarions)


        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }


/*

    private fun getNotifications(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getUserPortfolios(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseUserPortfolio>> {
                override fun onResponse(call: Call<ArrayList<ResponseUserPortfolio>>, response: Response<ArrayList<ResponseUserPortfolio>>) {
                    try{
                        MyApplication.arrayUserPortfolios.clear()
                        MyApplication.arrayUserPortfolios=response.body()!!
                        setNotifications()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseUserPortfolio>>, throwable: Throwable) {
                }
            })

    }
*/











}
