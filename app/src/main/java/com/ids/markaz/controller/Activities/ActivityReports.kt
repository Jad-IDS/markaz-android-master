package com.ids.markaz.controller.Activities

import android.Manifest
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.ResponsePortfolioReport
import com.ids.markaz.model.ResponseUserPortfolio
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ActivityReports : AppCompactBase() {

    private lateinit var fromdatelistener: DatePickerDialog.OnDateSetListener
    private lateinit var fromDateCalendar: Calendar
    private var selectedFromDate = ""

    private lateinit var toDateDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var toDateCalendar: Calendar
    private var selectedToDate = ""

    private var selectedPortfolioId=0
    private var selectedReportId=0
    private var selectedReportnumber=0
    private var arrayFilterItemsPortfolio= java.util.ArrayList<ItemSpinner>()
    private var arrayFilterItemsReport= java.util.ArrayList<ItemSpinner>()

    private var downloadManager: DownloadManager? = null
    private var currentPosition = 0
    val PERMISSION_WRITE = 3425
    var selectedUrl=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)
        init()
    }

    private fun init(){
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.reports)
        btBack.setOnClickListener{
            super.onBackPressed()
        }

        if(MyApplication.arrayUserPortfolios.size==0)
            getUserPortfolios()
        else
            setSpinnnerPortfolio()

        if(MyApplication.arrayPortfolioReports.size==0)
            getPortfolioReports()
        else
            setReportsType()


        setDatePickers()
        setReportsType()
        setSpinnnerPortfolio()

        btExcel.setOnClickListener{prepareLinkAndDownload(AppConstants.REPORT_EXCEL)}
        btPdf.setOnClickListener{prepareLinkAndDownload(AppConstants.REPORT_PDF)}
        btWord.setOnClickListener{prepareLinkAndDownload(AppConstants.REPORT_WORD)}
    }

    private fun prepareLinkAndDownload(type:String){
        when {
            selectedFromDate.isEmpty() -> Toast.makeText(applicationContext,"please choose from date",Toast.LENGTH_LONG).show()
            selectedToDate.isEmpty() -> Toast.makeText(applicationContext,"please choose To date",Toast.LENGTH_LONG).show()
            else -> {
                selectedUrl=RetrofitClientAuth.BASE_URL+"client/Report/GetClientReport"+  "?id=" + selectedReportId + "&onlineUserId=" + MyApplication.investioUserId + "&portfolios=" + selectedPortfolioId + "&numbers=" + selectedReportnumber + "&clientId=0&fundIds=0&fromDate=" + selectedFromDate + "&toDate=" + selectedToDate + "&currencyId=" + MyApplication.arrayCurrencies[0].id!!+ "&bankAccountIds=&tradingAccountIds=&holdingIds=&isEnglish=true&isUsingClosePrice=true&costBasis=MTMNetAVG&isConsolidated=false&renderType=" + type
                Log.wtf("selected_url",selectedUrl)
                startDownload(selectedUrl)
            }
        }
    }

    private fun setDatePickers(){
        fromDateCalendar = Calendar.getInstance()
        fromdatelistener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            fromDateCalendar.set(Calendar.YEAR, year)
            fromDateCalendar.set(Calendar.MONTH, monthOfYear)
            fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            tvDateFrom.text = AppHelper.dateFormatProfile.format(fromDateCalendar.time)
            selectedFromDate = AppHelper.dateFormatReport.format(fromDateCalendar.time)
        }
        linearFrom.setOnClickListener{
            DatePickerDialog(this, fromdatelistener, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(
                Calendar.MONTH), fromDateCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        toDateCalendar = Calendar.getInstance()
        toDateDateListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            toDateCalendar.set(Calendar.YEAR, year)
            toDateCalendar.set(Calendar.MONTH, monthOfYear)
            toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            tvDateTo.text = AppHelper.dateFormatProfile.format(toDateCalendar.time)
            selectedToDate = AppHelper.dateFormatReport.format(toDateCalendar.time)
        }
        linearTo.setOnClickListener{
            DatePickerDialog(this, toDateDateListener, toDateCalendar.get(Calendar.YEAR), toDateCalendar.get(
                Calendar.MONTH), toDateCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }


    private fun setReportsType(){
        arrayFilterItemsReport.clear()

        for (i in MyApplication.arrayPortfolioReports.indices)
            arrayFilterItemsReport.add(ItemSpinner(MyApplication.arrayPortfolioReports[i].id,MyApplication.arrayPortfolioReports[i].titleEn,MyApplication.arrayPortfolioReports[i].number))

        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item, arrayFilterItemsReport)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spReportType!!.adapter = adapter;
        spReportType!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedReportId=item!!.id!!
                selectedReportnumber=item.number!!

            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }


    private fun setSpinnnerPortfolio(){

        arrayFilterItemsPortfolio.clear()

        for (i in MyApplication.arrayUserPortfolios.indices)
            arrayFilterItemsPortfolio.add(ItemSpinner(MyApplication.arrayUserPortfolios[i].id,MyApplication.arrayUserPortfolios[i].nameEn))



        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item, arrayFilterItemsPortfolio)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spPortfolio!!.adapter = adapter;
        spPortfolio!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedPortfolioId=item!!.id!!

            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
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
                        setSpinnnerPortfolio()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseUserPortfolio>>, throwable: Throwable) {
                }
            })

    }


    private fun getPortfolioReports(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioReport(
            )?.enqueue(object : Callback<ArrayList<ResponsePortfolioReport>> {
                override fun onResponse(call: Call<ArrayList<ResponsePortfolioReport>>, response: Response<ArrayList<ResponsePortfolioReport>>) {
                    try{
                        MyApplication.arrayPortfolioReports.clear()
                        MyApplication.arrayPortfolioReports=response.body()!!
                        setReportsType()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePortfolioReport>>, throwable: Throwable) {
                }
            })

    }




    override
    fun onResume() {
        super.onResume()
        downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        this.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        this.registerReceiver(
            onNotificationClick,
            IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        )
    }


    override
    fun onPause() {
        super.onPause()
        this.unregisterReceiver(onComplete)
        this.unregisterReceiver(onNotificationClick)
    }


    override
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){


            PERMISSION_WRITE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        startDownload(selectedUrl)
                    else
                        Toast.makeText(
                            application,
                            getString(R.string.download_failed),
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }




    }



    fun startDownload(url: String) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!AppHelper.hasPermissions(this, *permissions)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, PERMISSION_WRITE)
            }
        }

        else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs()
            val urlSplit = url.trim { it <= ' ' }.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Toast.makeText(application, getString(R.string.downloading), Toast.LENGTH_LONG).show()
            //lastDownload =
            downloadManager!!.enqueue(
                DownloadManager.Request(Uri.parse(url))
                    .addRequestHeader("Authorization", "Bearer "+MyApplication.responseLogin.accessToken.toString())
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(urlSplit[urlSplit.size - 1])
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        urlSplit[urlSplit.size - 1]
                    )
            )
        }
    }

    internal var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, getString(R.string.download_complete), Toast.LENGTH_LONG).show()
        }
    }

    internal var onNotificationClick: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, getString(R.string.downloading), Toast.LENGTH_LONG).show()
        }
    }

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    fun onPageSelected(position: Int) {
        currentPosition = position

    }

    fun onPageScrollStateChanged(state: Int) {

    }

    fun onPageClicked(v: View) {

    }





}
