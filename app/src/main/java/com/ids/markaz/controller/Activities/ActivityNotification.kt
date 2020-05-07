package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterNotifications

import com.ids.markaz.controller.Adapters.AdapterTransactions
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityNotification : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {
        startActivity(Intent(this,ActivityNotificationInside::class.java))
    }



    private lateinit var adapterNotifications: AdapterNotifications
    private var arrayNotifications=java.util.ArrayList<Notifications>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        init()
        setNotifications()


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



    private fun setNotifications(){
        arrayNotifications.clear()
        arrayNotifications.add(Notifications(1,"Your Account is Empty , some option you canot use","It used to be that trading on stocks was limited to experts in finance. But no more.\\n\\nToday, in an ever-growing world of global information, the tools for investing in the stock market are becoming available to anyone with an internet connection and a willing to learn new things. Fortrader offers you the opportunity to invest on a wide selection of stocks from around the world.","16/01/2020"))
        arrayNotifications.add(Notifications(2,"AUSTRALIAN DOLLAR AT RISK AHEAD OF RBA MEETING","The Mexican Peso has been in a consolidation range vs the US Dollar since the start of the yearly with USD/MXN trading just below the yearly open into the start of February trade. The entire 2020 range consists of just 2% and we’re looking for a breakout of this monthly long contraction in price for guidance on our medium-term directional bias. These are the updated targets and invalidation levels that matter on the USD/MXN price charts heading into the monthly open. Review my latest Strategy Webinar for an in-depth breakdown the setupswe’re tracking this week.","08/01/2020"))
        arrayNotifications.add(Notifications(3,"MEXICAN PESO TECHNICAL PRICE OUTLOOK: USD/MXN TRADE LEVELS","The Australian Dollar looks ripe for volatility surrounding the latest RBA monetary policy update on deck for release Tuesday, February 04 at 3:30 GMT\\n\\n\" +\n" +
                "            \"Aussie forex traders may react violently to the RBA interest rate decision with AUD/USD, AUD/JPY and AUD/CAD overnight implied volatility spiking to 5-month highs\\n\" +\n" +
                "            \"Overnight swaps are currently pricing a rough 20% probability that the RBA cuts its official cash rate by 25-bps but dovish guidance could weigh negatively on AUD price outlook\\n\" +\n" +
                "            \"Currency volatility expected in the Australian Dollar is at extremes ahead of the upcoming interest rate decision from the Reserve Bank of Australia (RBA). The RBA is due to release its latest monetary policy update on Tuesday, February 04 at 3:30 GMT and has correspondingly pushed Australian Dollar overnight implied volatility readings to multi-month highs.","08/01/2020"))
        adapterNotifications= AdapterNotifications(arrayNotifications,this,0)
        val glm = GridLayoutManager(this, 1)
        rvNotifications.adapter=adapterNotifications
        rvNotifications.layoutManager=glm

    }







}
