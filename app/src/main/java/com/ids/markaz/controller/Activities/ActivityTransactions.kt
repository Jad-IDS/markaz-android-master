package com.ids.markaz.controller.Activities

import android.os.Bundle

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner

import com.ids.markaz.controller.Adapters.AdapterTransactions
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityTransactions : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {

    }

    private var selectedTab=0
    private var filterSelectedId=0
    private var portfolioId=1

    private lateinit var adapterTrans: AdapterTransactions
    private var arrayTrans=java.util.ArrayList<ResponseTransaction>()


    lateinit var adapterFilter: AdapterGeneralSpinner
    private var arrayFilterItems= java.util.ArrayList<ItemSpinner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)
        init()
        getTransactions()
        setFilterArray()
        if(MyApplication.arrayUserPortfolios.size==0)
            getUserPortfolios()
        else
            setFilterArray()
    }

    private fun init(){
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.activities_transactions)

        tvTabLastDay.setOnClickListener{
            setTab(AppConstants.TAB_LAST_DAY)
            getTransactions()

        }

        tvTabWtd.setOnClickListener{
            setTab(AppConstants.TAB_WTD)
            getTransactions()
        }

        tvTabMtd.setOnClickListener{
            setTab(AppConstants.TAB_MTD)
            getTransactions()
        }


        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }



    private fun getUserPortfolios(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getUserPortfolios(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseUserPortfolio>> {
                override fun onResponse(call: Call<ArrayList<ResponseUserPortfolio>>, response: Response<ArrayList<ResponseUserPortfolio>>) {
                    try{
                        MyApplication.arrayUserPortfolios.clear()
                        MyApplication.arrayUserPortfolios=response.body()!!
                        setFilterArray()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseUserPortfolio>>, throwable: Throwable) {
                }
            })

    }

    private fun setTab(index:Int){
        selectedTab=index
        tvTabLastDay.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabWtd.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabMtd.setBackgroundResource(R.drawable.rounded_border_secondary)

        AppHelper.setTextColor(this,tvTabLastDay,R.color.secondary)
        AppHelper.setTextColor(this,tvTabWtd,R.color.secondary)
        AppHelper.setTextColor(this,tvTabMtd,R.color.secondary)

        when (index) {
            AppConstants.TAB_LAST_DAY -> {
                tvTabLastDay.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabLastDay,R.color.white)
            }
            AppConstants.TAB_WTD -> {
                tvTabWtd.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabWtd,R.color.white)
            }
            AppConstants.TAB_MTD -> {
                tvTabMtd.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(this,tvTabMtd,R.color.white)
            }

        }

    }


    private fun setDataTrans(){

        adapterTrans= AdapterTransactions(arrayTrans,this,selectedTab)
        val glm = GridLayoutManager(this, 1)
        rvTransactions.adapter=adapterTrans
        rvTransactions.layoutManager=glm

    }







    private fun setFilterArray(){
        arrayFilterItems.clear()

        for (i in MyApplication.arrayUserPortfolios.indices)
            arrayFilterItems.add(ItemSpinner(MyApplication.arrayUserPortfolios[i].id,MyApplication.arrayUserPortfolios[i].nameEn))

        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item, arrayFilterItems)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spFilter!!.adapter = adapter;
        spFilter!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                //filterSelectedId=item!!.id!!
                portfolioId=item!!.id!!
                getTransactions()

            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }



    private fun getTransactions(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getTransactions(
                portfolioId,
                MyApplication.currencyId,
                date,
                //"2019-11-11",
                ""
            )?.enqueue(object : Callback<ArrayList<ResponseTransaction>> {
                override fun onResponse(call: Call<ArrayList<ResponseTransaction>>, response: Response<ArrayList<ResponseTransaction>>) {
                    loading.visibility=View.GONE
                    try{

                        arrayTrans.clear()
                        arrayTrans.addAll(response.body()!!)
                        setDataTrans()


                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseTransaction>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }



}
