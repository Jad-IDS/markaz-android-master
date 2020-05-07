package com.ids.markaz.controller.Fragments
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.ids.inpoint.utils.RetrofitClient
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityHome
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.loading_trans.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executor
import kotlin.collections.ArrayList


class FragmentLogin : Fragment() ,RVOnItemClickListener {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    var currencyRetrieved=false
    var userTypesetrieved=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_login, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        checkBio()
        etUsrname.setText("Administrator")
        etPassword.setText("P@ssw0rd")
    }

    private fun init(){
        btLogin.setOnClickListener{
            if(etUsrname.text.toString().isEmpty() || etPassword.text.toString().isEmpty())
                AppHelper.createDialog(activity!!,getString(R.string.check_empty_fields))
            else
                checkLogin()



        }

    }

    private fun checkLogin(){
        loading.visibility=View.VISIBLE
        RetrofitClient.client?.create(RetrofitInterface::class.java)
            ?.login(
                etUsrname.text.toString(),
                etPassword.text.toString(),
                "vestiowebappspa",
                "password",
                "openid email phone profile offline_access roles clientdashboardapi portfoliodashboardapi funddashboardapi identityserverapi"

            )?.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    try{

                        onLoginRetrieved(response)

                    }catch (E:Exception){
                        AppHelper.createDialog(activity!!,response.errorBody()!!.string().replace("\"", ""))
                    }
                }
                override fun onFailure(call: Call<ResponseLogin>, throwable: Throwable) {
                  AppHelper.createDialog(activity!!,throwable.message.toString())
                }
            })



    }

    private fun onLoginRetrieved(response: Response<ResponseLogin>) {
       // Toast.makeText(activity,response.body()!!.accessToken,Toast.LENGTH_LONG).show()

           MyApplication.responseLogin=response.body()!!
           MyApplication.investioUserId=JWT(response.body()!!.accessToken!!).getClaim("vestiouserid").asInt()!!
           MyApplication.expDate=AppHelper.getDateFromTimestamp(JWT(response.body()!!.accessToken!!).getClaim("exp").asLong()!!)!!
           MyApplication.expDateTimestamp=JWT(response.body()!!.accessToken!!).getClaim("exp").asLong()!!


           Log.wtf("access_token_timestamp",JWT(response.body()!!.accessToken!!).getClaim("exp").asString())
           Log.wtf("access_token",response.body()!!.accessToken)
           Log.wtf("access_token_decode",MyApplication.investioUserId.toString())
           Log.wtf("access_token_exp",MyApplication.expDate)
           getUserClients()
           getCurrencies()
           getUserPortfolios()
           getPortfolioReports()

    }




    private fun getUserClients(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getUserClients(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseUser>> {
                override fun onResponse(call: Call<ArrayList<ResponseUser>>, response: Response<ArrayList<ResponseUser>>) {
                    try{

                        MyApplication.arrayUsersClients=response.body()!!
                        userTypesetrieved=true
                        if(MyApplication.clientId==0)
                            MyApplication.clientId=MyApplication.arrayUsersClients[0].id!!
                        goNext()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseUser>>, throwable: Throwable) {
                }
            })

    }




    private fun getCurrencies(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getCurrencies(
            )?.enqueue(object : Callback<ArrayList<ResponseCurrency>> {
                override fun onResponse(call: Call<ArrayList<ResponseCurrency>>, response: Response<ArrayList<ResponseCurrency>>) {
                    try{
                     MyApplication.arrayCurrencies=response.body()!!
                     currencyRetrieved=true
                        if(MyApplication.currencyId==0)
                            MyApplication.currencyId=response.body()!![0].id!!
                     goNext()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseCurrency>>, throwable: Throwable) {
                }
            })

    }


    private fun getUserPortfolios(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getUserPortfolios(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseUserPortfolio>> {
                override fun onResponse(call: Call<ArrayList<ResponseUserPortfolio>>, response: Response<ArrayList<ResponseUserPortfolio>>) {
                    try{
                        MyApplication.arrayUserPortfolios.clear()
                        MyApplication.arrayUserPortfolios=response.body()!!
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
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePortfolioReport>>, throwable: Throwable) {
                }
            })

    }


    private fun goNext(){
        if(userTypesetrieved && currencyRetrieved){
        loading.visibility=View.GONE
        val i = Intent(activity!!, ActivityHome::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        }
    }

    private fun checkBio(){
        executor = ContextCompat.getMainExecutor(activity)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                    btLogin.performClick()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(activity, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        btFingerPrint.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }




    override fun onItemClicked(view: View, position: Int) {


    }


}


