package com.ids.markaz.controller.Activities

import android.content.Intent

import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterPerformance
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTab
import com.ids.markaz.model.Performance
import com.ids.markaz.model.ResponsePerformance
import com.ids.markaz.model.ResponseTransaction
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityPerformance : AppCompactBase(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {
        MyApplication.selectedPerformance=adapterPerformance.itemPerformance[position]
        startActivity(Intent(this,ActivityPerfomanceInside::class.java))
    }


    private lateinit var adapterPerformance: AdapterPerformance
    private var arrayPerformance=java.util.ArrayList<ResponsePerformance>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
        init()
        getPerformanceDetails()

    }

    private fun init(){
        selectedTab=AppConstants.TAB_WTD
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.performance)

        tvTabWtd.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE_WTD)
            setPerformance()
        }

        tvTabYtd.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE_YTD)
            setPerformance()
        }

        tvTabMtd.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE_MTD)
            setPerformance()
        }

        tvTabInception.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE_INCEPTION)
            setPerformance()
        }

        tvTabChart.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE_CHART)
            setPerformance()
        }

        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }



    private fun setTab(index:Int){
        selectedTab=index
        tvTabWtd.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabYtd.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabMtd.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabInception.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabChart.setBackgroundResource(R.drawable.rounded_border_secondary)

        AppHelper.setTextColor(this,tvTabWtd,R.color.secondary)
        AppHelper.setTextColor(this,tvTabYtd,R.color.secondary)
        AppHelper.setTextColor(this,tvTabMtd,R.color.secondary)
        AppHelper.setTextColor(this,tvTabInception,R.color.secondary)
        AppHelper.setTextColor(this,tvTabChart,R.color.secondary)

        when (index) {
            AppConstants.TAB_PERFORMANCE_WTD -> {
                tvTabWtd.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabWtd,R.color.white)
            }
            AppConstants.TAB_PERFORMANCE_YTD -> {
                tvTabYtd.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabYtd,R.color.white)
            }
            AppConstants.TAB_PERFORMANCE_MTD -> {
                tvTabMtd.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabMtd,R.color.white)
            }
            AppConstants.TAB_PERFORMANCE_INCEPTION -> {
                tvTabInception.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabInception,R.color.white)
            }

            AppConstants.TAB_PERFORMANCE_CHART -> {
                tvTabChart.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabChart,R.color.white)
            }
        }

    }


    private fun setPerformance(){

        adapterPerformance= AdapterPerformance(arrayPerformance,this,selectedTab)
        val glm = GridLayoutManager(this, 1)
        rvTransactions.adapter=adapterPerformance
        rvTransactions.layoutManager=glm

    }






    private fun getPerformanceDetails(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioPerformanceDetails(
                MyApplication.investioUserId,
                date

            )?.enqueue(object : Callback<ArrayList<ResponsePerformance>> {
                override fun onResponse(call: Call<ArrayList<ResponsePerformance>>, response: Response<ArrayList<ResponsePerformance>>) {
                    loading.visibility=View.GONE
                    try{

                        arrayPerformance.clear()
                        arrayPerformance.addAll(response.body()!!)
                        setPerformance()


                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePerformance>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }




}
