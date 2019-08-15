package com.example.puneet.gotennatest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.puneet.gotennatest.adapter.MainAdapter
import com.example.puneet.gotennatest.models.Data
import com.example.puneet.gotennatest.viewmodels.MainActivityViewModels
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.list_item.*


class MainActivity : AppCompatActivity(), MainAdapter.OnItemListener {
    private val TAG = MainActivity::class.qualifiedName
    private var mainActivityViewModels: MainActivityViewModels?=null
    private var adapter: MainAdapter?= null
    private var recyclerView:RecyclerView?=null
    private var arrayList: LiveData<List<Data>>? = null
    private val POSITION : String = "position"
    private val LATITUDE : String = "latitude"
    private val LONGITUDE : String = "longitude"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        setContentView(R.layout.layout_list)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mainActivityViewModels = ViewModelProviders.of(this).get(MainActivityViewModels::class.java)
        mainActivityViewModels!!.init()
        arrayList = mainActivityViewModels!!.getLocationRepository()
        Log.d(TAG,"arrayList " + arrayList)
        mainActivityViewModels!!.getLocationRepository()?.observe(this, Observer { mainActivityViewModels ->
            adapter = MainAdapter(this@MainActivity, mainActivityViewModels!!,this)
            recyclerView!!.setLayoutManager(LinearLayoutManager(this@MainActivity))
            recyclerView!!.setAdapter(adapter)
            adapter!!.notifyDataSetChanged()
            })
    }

    override fun onItemClick(position: Int) {
        // navigate to another activity
        Log.d(TAG,"inside onClick " + position)

        val lat: Float = arrayList!!.value!![position].latitude
        Log.d(TAG,"lat " + lat)
        val lon: Float = arrayList!!.value!![position].longitude
        Log.d(TAG,"lon " + lon)
        val name : String = arrayList!!.value!![position].name
        Log.d(TAG,"name " + name)
        val intent = Intent(this,MapActivity::class.java)
        intent.putExtra(POSITION,name)
        intent.putExtra(LATITUDE,lat)
        intent.putExtra(LONGITUDE,lon)
        startActivity(intent)
    }
//    fun fetchJson() {
//        Log.d(TAG,"inside fetch json")
//        val url = "https://annetog.gotenna.com/development/scripts/get_map_pins.php"
//        val request = Request.Builder().url(url).build()
//
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body?.string()
//                Log.d(TAG,"body " + body)
//                try {
//                    val gson = GsonBuilder().create()
//                    Log.d(TAG,"gson " + gson)
//                    val data: List<Data> = gson.fromJson(body, Array<Data>::class.java).toList()
//                    Log.d(TAG,"data " + data)
//                    for (element in data) {
//                        Log.d(TAG,"element name " + element.name)
//                        Log.d(TAG,"element latitude " + element.latitude)
//                        Log.d(TAG,"element longitude " + element.longitude)
//                        Log.d(TAG,"element description " + element.description)
//
//                    }
//                } catch (e: Exception) {
//                    Log.d(TAG, "error " + e)
//                    e.stackTrace
//                }
//                runOnUiThread {
////                    recycler_view.adapter = MainAdapter(data)
//                }
//            }
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG,"failed to execute request")
//            }
//        })
//    }

}

