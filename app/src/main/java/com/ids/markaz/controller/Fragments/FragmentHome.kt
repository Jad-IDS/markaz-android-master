package com.ids.markaz.controller.Fragments


import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityAccountSummary
import com.ids.markaz.controller.Adapters.AdapterAllInvestment
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTab
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.ResponseAllInvestment
import com.ids.markaz.model.ResponseInvestmentInfo
import com.ids.markaz.model.ResponseUser
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class FragmentHome : Fragment() ,RVOnItemClickListener {

    lateinit var adapterInvestment:AdapterAllInvestment
    var arrayAllInvestment=java.util.ArrayList<ResponseAllInvestment>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_home, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


    }

    public fun init(){
        selectedTab=AppConstants.TAB_ASSET
        setListeners()
        setInvestment()
        getInvesmentInfo()
        getInvestmentAsset()
    }


    private fun setListeners(){
        tvTabAsset.setOnClickListener{

            setTab(AppConstants.TAB_ASSET)
            getInvestmentAsset()
        }
        tvTabGeographical.setOnClickListener{

            setTab(AppConstants.TAB_GEOGRAPHICAL)
            getInvestmentCountry()
        }
        tvTabSector.setOnClickListener{

            setTab(AppConstants.TAB_SECTOR)
            getInvestmentSector()
        }
        tvTabCurrency.setOnClickListener{

            setTab(AppConstants.TAB_CURRENCY)
            getInvestmentCurrency()
        }

        linearPortfolioCash.setOnClickListener{startActivity(Intent(activity!!,ActivityAccountSummary::class.java))}
        linearPortfolioHolding.setOnClickListener{startActivity(Intent(activity!!,ActivityAccountSummary::class.java))}
        linearPortfolioLiability.setOnClickListener{startActivity(Intent(activity!!,ActivityAccountSummary::class.java))}
        linearportfolioAssets.setOnClickListener{startActivity(Intent(activity!!,ActivityAccountSummary::class.java))}


    }



    private fun getInvesmentInfo(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())

        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentInfo(
                MyApplication.clientId,
                MyApplication.currencyId,
                date



            )?.enqueue(object : Callback<ResponseInvestmentInfo> {
                override fun onResponse(call: Call<ResponseInvestmentInfo>, response: Response<ResponseInvestmentInfo>) {
                    try{
                       setInfo(response.body())
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ResponseInvestmentInfo>, throwable: Throwable) {
                }
            })

    }


    private fun setInfo(response: ResponseInvestmentInfo?) {
        try{tvCashValue.text=response!!.cash.toString()}catch (e:Exception){tvCashValue.text="-"}
        try{tvHoldingValue.text=response!!.holdingValue.toString()}catch (e:Exception){tvHoldingValue.text="-"}
        try{tvTotalAssetsValue.text=response!!.totalAssets.toString()}catch (e:Exception){tvTotalAssetsValue.text="-"}
        try{tvTotalLiabilityValue.text=response!!.totalLiabilities.toString()}catch (e:Exception){tvTotalLiabilityValue.text="-"}


        try{tvCashPercentage.text="- %"}catch (e:Exception){}
        try{tvHoldingPercentage.text="- %"}catch (e:Exception){}
        try{tvTotalAssetsPercentage.text="- %"}catch (e:Exception){}
        try{tvTotalLiabilityPercentage.text="- %"}catch (e:Exception){}


        try{tvYesterdayValue.text=response!!.yesterdayPerformance.toString()+" %"}catch (e:Exception){tvYesterdayValue.text="- %"}
        try{tvYtdValue.text=response!!.ytdPerformance.toString()+" %"}catch (e:Exception){tvYtdValue.text="- %"}
        try{tvMtdValue.text=response!!.mtdPerformance.toString()+" %"}catch (e:Exception){tvMtdValue.text="- %" }

        try{tvLastLogin.text=MyApplication.expDate}catch (e:Exception){}

        val item: ResponseUser? = MyApplication.arrayUsersClients.find { it.id == MyApplication.clientId }
         var index=MyApplication.arrayUsersClients.indexOf(item)

        try{tvWelcome.text=getString(R.string.welcome_mr)+" "+MyApplication.arrayUsersClients[index].nameEn}catch (e:Exception){
            tvWelcome.text=getString(R.string.welcome_mr)+" "+MyApplication.arrayUsersClients[0].nameEn
        }
    }



    override fun onItemClicked(view: View, position: Int) {


    }



    private fun setTab(index:Int){
        selectedTab=index
        tvTabAsset.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabGeographical.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabSector.setBackgroundResource(R.drawable.rounded_border_secondary)
        tvTabCurrency.setBackgroundResource(R.drawable.rounded_border_secondary)

        AppHelper.setTextColor(activity!!,tvTabAsset,R.color.secondary)
        AppHelper.setTextColor(activity!!,tvTabGeographical,R.color.secondary)
        AppHelper.setTextColor(activity!!,tvTabSector,R.color.secondary)
        AppHelper.setTextColor(activity!!,tvTabCurrency,R.color.secondary)

        when (index) {
            AppConstants.TAB_ASSET -> {
                tvTabAsset.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(activity!!,tvTabAsset,R.color.white)
            }
            AppConstants.TAB_GEOGRAPHICAL -> {
                tvTabGeographical.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(activity!!,tvTabGeographical,R.color.white)
            }
            AppConstants.TAB_SECTOR -> {
                tvTabSector.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(activity!!,tvTabSector,R.color.white)
            }
            AppConstants.TAB_CURRENCY -> {
                tvTabCurrency.setBackgroundResource(R.drawable.rounded_corner_secondary)
                AppHelper.setTextColor(activity!!,tvTabCurrency,R.color.white)
            }
        }

    }




    private fun setInvestment(){

        adapterInvestment= AdapterAllInvestment(arrayAllInvestment,this,selectedTab)
        val glm = GridLayoutManager(activity, 1)
        rvData.adapter=adapterInvestment
        rvData.layoutManager=glm
        rvData.isNestedScrollingEnabled=false
    }





    private fun setPieChart(type:Int){
        chart.setUsePercentValues(false)

        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
      //  chart.centerText = generateCenterSpannableText()
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 80f
        chart.transparentCircleRadius = 61f
        chart.setDrawCenterText(true)

        when (type) {
            AppConstants.TAB_ASSET -> chart.centerText=getString(R.string.asset_class)
            AppConstants.TAB_GEOGRAPHICAL -> chart.centerText=getString(R.string.geographical)
            AppConstants.TAB_SECTOR -> chart.centerText=getString(R.string.sector)
            AppConstants.TAB_CURRENCY -> chart.centerText=getString(R.string.currency)

            //chart.setOnChartValueSelectedListener(this)
        }

        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        //chart.setOnChartValueSelectedListener(this)
        chart.animateY(1400, Easing.EaseInOutQuad)
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
        chart.legend.isEnabled=false

    }




    private fun setData() {
        val entries = ArrayList<PieEntry>()


        for (i in 0 until arrayAllInvestment.size) {
           entries.add(PieEntry(arrayAllInvestment[i].totalPercentage!!.toFloat()))

        }

        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.selectionShift = 0f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.TRANSPARENT)


        try{
        for (i in 0 until arrayAllInvestment.size) {
            if(i < arrayAllInvestment.size)
               arrayAllInvestment[i].color=dataSet.colors[i]
            else
                arrayAllInvestment[i].color=dataSet.colors[0]
        }
        }catch (e:Exception){
            Log.wtf("exception",e.toString())
        }


        chart.data = data

        // undo all highlights
        chart.highlightValues(null)

        chart.invalidate()
    }






    private fun getInvestmentAsset(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByAsset(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
         )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                      arrayAllInvestment.clear()
                      arrayAllInvestment.addAll(response.body()!!)
                      adapterInvestment.notifyDataSetChanged()

                      setPieChart(AppConstants.TAB_ASSET)
                      setData()

                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                }
            })

    }

    private fun getInvestmentCountry(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByCountry(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_GEOGRAPHICAL)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                }
            })

    }


    private fun getInvestmentSector(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentsBySector(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_SECTOR)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                }
            })

    }


    private fun getInvestmentCurrency(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByCurrency(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_CURRENCY)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                }
            })

    }


}


