package com.ids.markaz.controller.Activities

import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterPerformanceInside
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTabInside
import com.ids.markaz.model.PerformanceInside
import com.ids.markaz.model.ResponsePerformanceInside
import com.ids.markaz.model.ResponseTransaction
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.android.synthetic.main.activity_performance_inside.*

import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ActivityPerfomanceInside : AppCompactBase() , RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }


    private var portfolioId=0

    private lateinit var adapterPerformance: AdapterPerformanceInside
    private var arrayPerformance=java.util.ArrayList<ResponsePerformanceInside>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_inside)
        init()
        getTopHolding()
    }

    private fun init(){
        try{portfolioId=MyApplication.selectedPerformance.id!!}catch (e:Exception){}
        try{
            if(MyApplication.selectedPerformance.nameEn!=null && MyApplication.selectedPerformance.nameEn!!.isNotEmpty())
                tvPortfolioValue.text=MyApplication.selectedPerformance.nameEn

        }catch (e:Exception){tvPortfolioValue.text="-"}

        selectedTabInside=AppConstants.TAB_HOLDING
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.performance)

        tvTabTopHolding.setOnClickListener{
            clearAdapter()
            setTab(AppConstants.TAB_TOP_HOLDING)
            getTopHolding()
        }

        tvTabTopGainers.setOnClickListener{
            clearAdapter()
            setTab(AppConstants.TAB_TOP_GAINER)
            getTopGainers()
        }

        tvTabTopLooser.setOnClickListener{
            clearAdapter()
            setTab(AppConstants.TAB_TOP_LOSERS)
            getTopLosers()
        }

        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }

    private fun clearAdapter(){
        try {
            arrayPerformance.clear()
            adapterPerformance.notifyDataSetChanged()
        }catch (e:Exception){}
    }


    private fun setTab(index:Int){
        selectedTabInside=index
        tvTabTopHolding.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabTopGainers.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabTopLooser.setBackgroundResource(R.drawable.rounded_border_secondary)


        AppHelper.setTextColor(this,tvTabTopHolding,R.color.secondary)
        AppHelper.setTextColor(this,tvTabTopGainers,R.color.secondary)
        AppHelper.setTextColor(this,tvTabTopLooser,R.color.secondary)


        when (index) {
            AppConstants.TAB_TOP_HOLDING -> {
                tvTabTopHolding.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabTopHolding,R.color.white)
            }
            AppConstants.TAB_TOP_GAINER -> {
                tvTabTopGainers.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabTopGainers,R.color.white)
            }
            AppConstants.TAB_TOP_LOSERS -> {
                tvTabTopLooser.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabTopLooser,R.color.white)
            }

        }

    }


    private fun setPerformance(){
        adapterPerformance= AdapterPerformanceInside(arrayPerformance,this,selectedTabInside)
        val glm = GridLayoutManager(this, 1)
        rvPerformance.adapter=adapterPerformance
        rvPerformance.layoutManager=glm

    }




    private fun getTopLosers(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getBottomHoldingByRerun(
                portfolioId,
                MyApplication.currencyId,
                //date,
                "2019-11-11",
                ""
            )?.enqueue(object : Callback<ArrayList<ResponsePerformanceInside>> {
                override fun onResponse(call: Call<ArrayList<ResponsePerformanceInside>>, response: Response<ArrayList<ResponsePerformanceInside>>) {
                    loading.visibility=View.GONE
                    try{

                        arrayPerformance.clear()
                        arrayPerformance.addAll(response.body()!!)
                        setPerformance()


                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePerformanceInside>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getTopGainers(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getTopHoldingByRerun(
                portfolioId,
                MyApplication.currencyId,
                date,
                //"2019-11-11",
                ""
            )?.enqueue(object : Callback<ArrayList<ResponsePerformanceInside>> {
                override fun onResponse(call: Call<ArrayList<ResponsePerformanceInside>>, response: Response<ArrayList<ResponsePerformanceInside>>) {
                    loading.visibility=View.GONE
                    try{

                        arrayPerformance.clear()
                        arrayPerformance.addAll(response.body()!!)
                        setPerformance()


                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePerformanceInside>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getTopHolding(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getTopHoldingByWeight(
                portfolioId,
                MyApplication.currencyId,
                date,
                //"2019-11-11",
                ""
            )?.enqueue(object : Callback<ArrayList<ResponsePerformanceInside>> {
                override fun onResponse(call: Call<ArrayList<ResponsePerformanceInside>>, response: Response<ArrayList<ResponsePerformanceInside>>) {
                    loading.visibility=View.GONE
                    try{

                        arrayPerformance.clear()
                        arrayPerformance.addAll(response.body()!!)
                        setPerformance()


                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePerformanceInside>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


}

