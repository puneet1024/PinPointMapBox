package com.example.puneet.gotennatest.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.puneet.gotennatest.MainActivity
import com.example.puneet.gotennatest.R
import com.example.puneet.gotennatest.models.Data

class MainAdapter(val data: MainActivity, private val arrayList: List<Data>, onItemListener: OnItemListener): RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {
    private val mOnItemListener:OnItemListener
    //    val list = mutableListOf(Data(1,"shjkdhad", 231.45F, 213.65F,"dsfsf"),Data(2,"dshasjdhask",231.12F,213.45F,"dsfsf"),Data(2,"dshasjdhask",231.12F,213F,"dsfsf"))
    init {
        this.mOnItemListener = onItemListener
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        Log.d(TAG, "inside on create ViewHolder")
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(cellForRow,mOnItemListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Log.d(TAG, "inside on bind ViewHolder")
        val data: Data = arrayList.get(position)
        holder?.bind(data)
    }

    class CustomViewHolder(view: View, onItemListener: OnItemListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var mName: TextView? = null
        var mLatitude: TextView? = null
        var mLongitude: TextView? = null
        var mDescription: TextView? = null
        var mOnItemListener:OnItemListener? = null
        init {
            mName = view.findViewById(R.id.name)
            mLatitude = view.findViewById(R.id.latitude)
            mLongitude = view.findViewById(R.id.longitude)
            mDescription = view.findViewById(R.id.description)
            mOnItemListener = onItemListener
            view.setOnClickListener(this)
        }

        fun bind(data: Data) {
            mName?.text = data.name
            mLongitude?.text = data.longitude.toString()
            mLatitude?.text = data.latitude.toString()
            mDescription?.text = data.description
        }

        override fun onClick(v: View?) {
            Log.d(TAG, "onClick " + adapterPosition)
            mOnItemListener!!.onItemClick(adapterPosition)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    companion object {
        val TAG = MainAdapter::class.qualifiedName
    }
}