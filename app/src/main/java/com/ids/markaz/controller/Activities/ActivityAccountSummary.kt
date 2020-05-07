package com.ids.markaz.controller.Activities

import android.content.Intent

import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterAccountSummary
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTab
import com.ids.markaz.model.Portfolio
import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_account_summary.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ActivityAccountSummary : AppCompactBase(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {
        MyApplication.selectedHolding=adapterAccountSummary.itemPortfolio[position]
        if(selectedTab==AppConstants.TAB_CASH)
            startActivity(Intent(this,ActivityInsideCash::class.java).putExtra(AppConstants.SELECTED_PORTFOLIO_ID,adapterAccountSummary.itemPortfolio[position].id))
        else
            startActivity(Intent(this,ActivityAccountSummaryInside::class.java).putExtra(AppConstants.SELECTED_PORTFOLIO_ID,adapterAccountSummary.itemPortfolio[position].id))
    }


    private lateinit var adapterAccountSummary: AdapterAccountSummary
    private lateinit var adapterHolding: AdapterAccountSummary
    private var arrayAccountSummary=java.util.ArrayList<ResponseAccountSummary>()
    private var arrayHolding=java.util.ArrayList<Portfolio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_summary)
        init()
        getAccountSummary()

       // setHolding()
    }

    private fun init(){
         selectedTab=AppConstants.TAB_CASH
         supportActionBar!!.hide()
         tvToolbarTitle.text=getString(R.string.portfolio)
         linearholding.visibility=View.GONE
         tvHeaderTitle.text=getString(R.string.total_cash)
         tabCash.setOnClickListener{
             linearholding.visibility=View.GONE
             setTab(AppConstants.TAB_CASH)
             try{adapterAccountSummary.notifyDataSetChanged()}catch (e:Exception){}
         }

        tabHolding.setOnClickListener{
            linearholding.visibility=View.VISIBLE
            setTab(AppConstants.TAB_HOLDING)
            try{adapterAccountSummary.notifyDataSetChanged()}catch (e:Exception){}
        }

        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }


    private fun setTab(index:Int){
        selectedTab=index

        when (index) {
            AppConstants.TAB_CASH -> {
                tabCash.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabCash,R.color.white)
                tabHolding.setBackgroundResource(R.drawable.rounded_border_secondary)
                AppHelper.setTextColor(this,tvtabHolding,R.color.secondary)
                tvHeaderTitle.text=getString(R.string.total_cash)
            }
            AppConstants.TAB_HOLDING -> {
                tabHolding.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvtabHolding,R.color.white)
                tabCash.setBackgroundResource(R.drawable.rounded_border_secondary)
                AppHelper.setTextColor(this,tvTabCash,R.color.secondary)
                tvHeaderTitle.text=getString(R.string.holdin_value)
            }

        }

    }

    private fun setAccountSummary(){
        adapterAccountSummary= AdapterAccountSummary(arrayAccountSummary,this, selectedTab)
        val glm = GridLayoutManager(this, 1)
        rvCash.adapter=adapterAccountSummary
        rvCash.layoutManager=glm
        rvCash.isNestedScrollingEnabled=false
    }


/*    private fun setHolding(){
        arrayHolding.clear()
        arrayHolding.add(Portfolio(1,"Fund #1","230","20%",2))
        arrayHolding.add(Portfolio(2,"Fund #2","300","30%",2))
        arrayHolding.add(Portfolio(3,"Fund #3","108","40%",2))
        arrayHolding.add(Portfolio(4,"Fund #4","201","10%",2))
        arrayHolding.add(Portfolio(5,"Fund #5","230","20%",2))


        adapterHolding= AdapterAccountSummary(arrayHolding,this,2)
        val glm = GridLayoutManager(this, 1)
        rvHolding.adapter=adapterHolding
        rvHolding.layoutManager=glm
        rvHolding.isNestedScrollingEnabled=false
    }*/




    private fun getAccountSummary(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioAccount(
                MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseAccountSummary>> {
                override fun onResponse(call: Call<ArrayList<ResponseAccountSummary>>, response: Response<ArrayList<ResponseAccountSummary>>) {
                    try{
                        arrayAccountSummary.clear()
                        arrayAccountSummary.addAll(response.body()!!)
                        setAccountSummary()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAccountSummary>>, throwable: Throwable) {
                }
            })

    }


}
