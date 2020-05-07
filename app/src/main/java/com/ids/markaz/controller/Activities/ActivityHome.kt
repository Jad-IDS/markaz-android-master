package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.core.view.GravityCompat

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.Fragments.FragmentHome
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper.Companion.fragmentAvailable
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.app_bar_activity_home.*
import kotlinx.android.synthetic.main.drawer_menu.*
import java.lang.Exception

class ActivityHome : AppCompactBase(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    lateinit var fragmentManager: FragmentManager
    private lateinit var  toggle: ActionBarDrawerToggle

    private var arrayClients= java.util.ArrayList<ItemSpinner>()
    private var selectedClientId=0

    private var isFirstTime=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        isFirstTime=true
        setSpinnerClients()
        if(MyApplication.clientId!=0){
            try{
                val item: ItemSpinner? = arrayClients.find { it.id == MyApplication.clientId }
                spCurrencies.setSelection(arrayClients.indexOf(item),false)}
            catch (e: Exception){}
        }


        setDrawer()
        fragmentManager = supportFragmentManager
        defaultFragment()
        setMenu()
    }



    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            backClicked()
        }
    }

    private fun backClicked(){
        super.onBackPressed()
    }


    private fun setDrawer(){

        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            toggle.drawerArrowDrawable.color=getColor(R.color.primary)
        else
            toggle.drawerArrowDrawable.color=resources.getColor(R.color.primary)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        supportActionBar!!.title = null;

    }


    private fun reloadDataHome(){
        try{ val fragment = fragmentManager.findFragmentByTag(AppConstants.HOME_FRAG) as FragmentHome?
            fragment!!.init()}catch (e:Exception){}
    }


    private fun defaultFragment(){


        fragmentAvailable= AppConstants.HOME
        val frag = FragmentHome()
        fragmentManager.beginTransaction()
            .add(R.id.container, frag, AppConstants.HOME_FRAG)
            .commit()
    }

    private fun setMenu(){
        linearAccountSummary.setOnClickListener{
            startActivity(Intent(this,ActivityAccountSummary::class.java))
        }

        linearActivityTrans.setOnClickListener{
            startActivity(Intent(this,ActivityTransactions::class.java))
        }

        linearPerformance.setOnClickListener{
            startActivity(Intent(this,ActivityPerformance::class.java))
        }

        linearReports.setOnClickListener{
            startActivity(Intent(this,ActivityReports::class.java))
        }

        linearContact.setOnClickListener{
            startActivity(Intent(this,ActivityContactUs::class.java))
        }

        linearNotifications.setOnClickListener{
            startActivity(Intent(this,ActivityNotification::class.java))
        }

        linearSettings.setOnClickListener{
            startActivity(Intent(this,ActivitySettings::class.java))
        }

    }




    private fun setSpinnerClients(){

        arrayClients.clear()

        for (i in MyApplication.arrayUsersClients.indices)
            arrayClients.add(
                ItemSpinner(
                    MyApplication.arrayUsersClients[i].id,
                    MyApplication.arrayUsersClients[i].nameEn)
            )

        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item, arrayClients)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spUsers!!.adapter = adapter;
        spUsers!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                if(!isFirstTime) {
                    selectedClientId = item!!.id!!
                    MyApplication.clientId = item.id!!
                    reloadDataHome()
                }
                isFirstTime=false

            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }

}
