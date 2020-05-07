package com.ids.markaz.controller.Adapters


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener


internal class AdapterStartPager(private val context: Context, val arrayList: ArrayList<Int>, private val itemClickListener: RVOnItemClickListener) :
    PagerAdapter() {
    private val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        var view = layoutInflater.inflate(R.layout.fragment_start_1, container, false)

        when (position) {
            0 -> view = layoutInflater.inflate(R.layout.fragment_start_1, container, false)
            1 -> view = layoutInflater.inflate(R.layout.fragment_start_2, container, false)
            2 -> view = layoutInflater.inflate(R.layout.fragment_start_3, container, false)
        }



        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}