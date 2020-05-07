package com.ids.markaz.controller.Adapters


import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.Asset
import com.ids.markaz.model.ResponseAllInvestment
import com.ids.markaz.utils.AppConstants
import java.lang.Exception


import java.util.*


class AdapterAllInvestment(val itemAsset: ArrayList<ResponseAllInvestment>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterAllInvestment.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_asset, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {

        when (MyApplication.selectedTab) {
            AppConstants.TAB_ASSET -> try{holder.tvTitle.text = itemAsset[position].typeEn}catch (e:Exception){}
            AppConstants.TAB_GEOGRAPHICAL -> try{holder.tvTitle.text = itemAsset[position].countryEn}catch (e:Exception){}
            AppConstants.TAB_SECTOR -> try{holder.tvTitle.text = itemAsset[position].sectorEn}catch (e:Exception){}
            AppConstants.TAB_CURRENCY -> try{holder.tvTitle.text = itemAsset[position].currencyEn}catch (e:Exception){}
        }

        try{holder.tvPercentage.text=String.format("%.2f", itemAsset[position].totalPercentage!!*100)+" %"}catch (e:Exception){}

        try{holder.circle_color.background.setColorFilter(itemAsset[position].color!!, PorterDuff.Mode.SRC_ATOP)}catch (e:Exception){}


    }

    override fun getItemCount(): Int {
        return itemAsset.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var circle_color: LinearLayout = itemView.findViewById(com.ids.markaz.R.id.circle_color) as LinearLayout
        var tvTitle: TextView = itemView.findViewById(com.ids.markaz.R.id.tvTitle) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
