package com.ids.markaz.controller

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.multidex.MultiDex
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.ids.markaz.BuildConfig
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.ForceUpdateChecker
import java.util.ArrayList

class MyApplication : Application() {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var sharedPreferencesEditor: SharedPreferences.Editor
        lateinit var instance: MyApplication

        lateinit var responseLogin: ResponseLogin
        lateinit var selectedHolding: ResponseAccountSummary
        var arrayUsersClients = ArrayList<ResponseUser>()
        var arrayUserPortfolios = ArrayList<ResponseUserPortfolio>()
        var arrayPortfolioReports = ArrayList<ResponsePortfolioReport>()
        var arrayCurrencies = ArrayList<ResponseCurrency>()
        var investioUserId=0
        var expDate=""
        var expDateTimestamp:Long = 0
        var selectedTab=0
        var selectedTabInside=0
        lateinit var selectedPerformance: ResponsePerformance

         var remoteConfigDefaults: Map<String, Any> = HashMap()

        var isFirstTimeHint: Boolean
            get() = sharedPreferences.getBoolean(AppConstants.IS_FIRST_TIME_HINT, true)
            set(value) {
                sharedPreferencesEditor.putBoolean(AppConstants.IS_FIRST_TIME_HINT, value).apply()
            }



        var isLoggedIn : Boolean
            get() = sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false)
            set(value) { sharedPreferencesEditor.putBoolean(AppConstants.IS_LOGGED_IN, value).apply() }

        var cashedUserName : String?
            get() = sharedPreferences.getString(AppConstants.CASHED_USERNAME, "")
            set(value) { sharedPreferencesEditor.putString(AppConstants.CASHED_USERNAME, value).apply() }

        var cashedPassword : String?
            get() = sharedPreferences.getString(AppConstants.CASHED_PASSWORD, "")
            set(value) { sharedPreferencesEditor.putString(AppConstants.CASHED_PASSWORD, value).apply() }

        var languageCode : String?
            get() = sharedPreferences.getString(AppConstants.SELECTED_LANGUAGE, AppConstants.LANG_ENGLISH)
            set(value) { sharedPreferencesEditor.putString(AppConstants.SELECTED_LANGUAGE, value).apply() }

        var enableNotifications : Boolean
            get() = sharedPreferences.getBoolean(AppConstants.ENABLE_NOTIFICATION, true)
            set(value){sharedPreferencesEditor.putBoolean( AppConstants.ENABLE_NOTIFICATION, value).apply()}

        var enableBiometric : Boolean
            get() = sharedPreferences.getBoolean(AppConstants.ENABLE_BIOMETRIC, true)
            set(value){sharedPreferencesEditor.putBoolean( AppConstants.ENABLE_BIOMETRIC, value).apply()}

        var currencyId : Int
            get() = sharedPreferences.getInt(AppConstants.CURRENCY_ID, 0)
            set(value){sharedPreferencesEditor.putInt( AppConstants.CURRENCY_ID, value).apply()}

        var clientId : Int
            get() = sharedPreferences.getInt(AppConstants.CLIENT_ID, 0)
            set(value){sharedPreferencesEditor.putInt( AppConstants.CLIENT_ID, value).apply()}
    }
    override fun onCreate() {
        super.onCreate()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPreferencesEditor = sharedPreferences.edit()
        instance=this


        //initializeFirebase()



    }

    private fun initializeFirebase() {
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this, FirebaseOptions.fromResource(this))
        }
        val config = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
            config.setConfigSettings(configSettings)
    }



}
