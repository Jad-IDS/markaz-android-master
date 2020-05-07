package com.ids.markaz.controller.Activities


import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterPortfolioPosition
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTabInside
import com.ids.markaz.model.ItemCashInside
import com.ids.markaz.model.Portfolio
import com.ids.markaz.model.ResponseHoldingPosition
import com.ids.markaz.model.ResponsePortfolioCash
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_account_inside.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityAccountSummaryInside : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {

    }



    private lateinit var adapterHolding: AdapterPortfolioPosition
    private var portfolioId=0
    private var arrayHolding=java.util.ArrayList<ResponseHoldingPosition>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_inside)
        init()
        setHolding()
        getHolding()
    }

    private fun init(){
        selectedTabInside=AppConstants.TAB_POSITION
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.portfolio)
        tvPortfolioValue.text=MyApplication.selectedHolding.cashPosition.toString()
        portfolioId=intent.getIntExtra(AppConstants.SELECTED_PORTFOLIO_ID,0)
        tvTabPositions.setOnClickListener{
            setTab(AppConstants.TAB_POSITION)
            tab_performance_header.visibility=View.GONE
            tab_gain_header.visibility=View.GONE
            tab_income_header.visibility=View.GONE
            tab_positions.visibility=View.VISIBLE
            setHolding()

        }

        tvTabGainLoss.setOnClickListener{
            setTab(AppConstants.TAB_GAIN_LOSS)
            tab_positions.visibility=View.GONE
            tab_gain_header.visibility=View.VISIBLE
            tab_performance_header.visibility=View.GONE
            tab_income_header.visibility=View.GONE
            setHolding()
        }

        tvTabIncome.setOnClickListener{
            setTab(AppConstants.TAB_INCOME)
            tab_positions.visibility=View.GONE
            tab_gain_header.visibility=View.GONE
            tab_income_header.visibility=View.GONE
            tab_income_header.visibility=View.VISIBLE
            setHolding()
        }

        tvTabPerformance.setOnClickListener{
            setTab(AppConstants.TAB_PERFORMANCE)
            tab_positions.visibility=View.GONE
            tab_performance_header.visibility=View.VISIBLE
            tab_gain_header.visibility=View.GONE
            tab_income_header.visibility=View.GONE
            setHolding()
        }

        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }


    private fun setTab(index:Int){
        selectedTabInside=index
        tvTabPositions.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabGainLoss.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabIncome.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabPerformance.setBackgroundResource(R.drawable.rounded_border_secondary)

        AppHelper.setTextColor(this,tvTabPositions,R.color.secondary)
        AppHelper.setTextColor(this,tvTabGainLoss,R.color.secondary)
        AppHelper.setTextColor(this,tvTabIncome,R.color.secondary)
        AppHelper.setTextColor(this,tvTabPerformance,R.color.secondary)

        when (index) {
            AppConstants.TAB_POSITION -> {
                tvTabPositions.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabPositions,R.color.white)
            }
            AppConstants.TAB_GAIN_LOSS -> {
                tvTabGainLoss.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabGainLoss,R.color.white)
            }
            AppConstants.TAB_INCOME -> {
                tvTabIncome.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabIncome,R.color.white)
            }
            AppConstants.TAB_PERFORMANCE -> {
                tvTabPerformance.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabPerformance,R.color.white)
            }
        }

    }



    private fun getHolding(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getHoldingPosition(
                portfolioId,
                MyApplication.currencyId,
                date,
                ""
            )?.enqueue(object : Callback<ArrayList<ResponseHoldingPosition>> {
                override fun onResponse(call: Call<ArrayList<ResponseHoldingPosition>>, response: Response<ArrayList<ResponseHoldingPosition>>) {
                    try{
                        arrayHolding.clear()
                        arrayHolding.addAll(response.body()!!)
                        adapterHolding.notifyDataSetChanged()


                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseHoldingPosition>>, throwable: Throwable) {
                }
            })

    }


    private fun setHolding(){

        adapterHolding= AdapterPortfolioPosition(arrayHolding,this, selectedTabInside)
        val glm = GridLayoutManager(this, 1)
        rvPortfolioData.adapter=adapterHolding
        rvPortfolioData.layoutManager=glm
        rvPortfolioData.isNestedScrollingEnabled=false
    }




}
