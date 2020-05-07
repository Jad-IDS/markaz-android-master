package com.ids.markaz.controller.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterAccountCashInside
import com.ids.markaz.controller.Adapters.AdapterPerformance
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemCashInside
import com.ids.markaz.model.Performance
import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.model.ResponsePortfolioCash
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_account_summary.*
import kotlinx.android.synthetic.main.activity_inside_cash.*
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityInsideCash : ActivityBase(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }

    private lateinit var adapterCash: AdapterAccountCashInside
    private var arrayCash=java.util.ArrayList<ItemCashInside>()
    private var portfolioId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_cash)

        portfolioId=intent.getIntExtra(AppConstants.SELECTED_PORTFOLIO_ID,0)
        tvToolbarTitle.text=getString(R.string.cash)
        btBack.setOnClickListener{
            super.onBackPressed()
        }
        getCashDetails()
    }



    private fun getCashDetails(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getCashDetails(
                portfolioId,
                MyApplication.currencyId,
                MyApplication.investioUserId
            )?.enqueue(object : Callback<ResponsePortfolioCash> {
                override fun onResponse(call: Call<ResponsePortfolioCash>, response: Response<ResponsePortfolioCash>) {
                    try{
                        arrayCash.clear()
                        arrayCash.add(ItemCashInside(1,getString(R.string.cash_aft),response.body()!!.balanceCashAFT.toString()))
                        arrayCash.add(ItemCashInside(2,getString(R.string.deposits),response.body()!!.balanceDeposits.toString()))
                        arrayCash.add(ItemCashInside(3,getString(R.string.blocked),response.body()!!.balanceBlocked.toString()))
                        arrayCash.add(ItemCashInside(4,getString(R.string.div_due),response.body()!!.receivableDividends.toString()))
                        arrayCash.add(ItemCashInside(5,getString(R.string.other_receivable),response.body()!!.receivableOthers.toString()))
                        arrayCash.add(ItemCashInside(6,getString(R.string.mngt_fees),response.body()!!.payableFees.toString()))
                        arrayCash.add(ItemCashInside(7,getString(R.string.other_payable),response.body()!!.payableOthers.toString()))
                        arrayCash.add(ItemCashInside(8,getString(R.string.tcash_position),response.body()!!.totalCashPosition.toString()))

                       setCashValues()


                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ResponsePortfolioCash>, throwable: Throwable) {
                }
            })

    }



    private fun setCashValues(){

        adapterCash= AdapterAccountCashInside(arrayCash,this,0)
        val glm = GridLayoutManager(this, 1)
        rvCashInside.adapter=adapterCash
        rvCashInside.layoutManager=glm

    }

}
