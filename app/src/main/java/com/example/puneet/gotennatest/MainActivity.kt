package com.example.puneet.gotennatest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.puneet.gotennatest.adapter.MainAdapter
import com.example.puneet.gotennatest.viewmodels.MainActivityViewModels
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.qualifiedName
    private lateinit var mapView:MapView
    private lateinit var map: MapboxMap
    private var destinationMarker: Marker?=null
    private var mainActivityViewModels: MainActivityViewModels?=null
    private var adapter: MainAdapter?= null
    private var recyclerView:RecyclerView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        setContentView(R.layout.layout_list)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mainActivityViewModels = ViewModelProviders.of(this).get(MainActivityViewModels::class.java)
        mainActivityViewModels!!.init()

        mainActivityViewModels!!.getLocationRepository()?.observe(this, Observer { mainActivityViewModels ->
            adapter = MainAdapter(this@MainActivity, mainActivityViewModels!!)
            recyclerView!!.setLayoutManager(LinearLayoutManager(this@MainActivity))
            recyclerView!!.setAdapter(adapter)
            adapter!!.notifyDataSetChanged()
            })

//        mapView = findViewById(R.id.mapView)
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync { mapboxMap ->
//            map = mapboxMap
//        }
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
//    fun onMapClick(point: LatLng) {
//        destinationMarker = map.addMarker(MarkerOptions().position(point))
//    }

//    public override fun onResume() {
//        super.onResume()
//        mapView?.onResume()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        mapView?.onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView?.onStop()
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        mapView?.onPause()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView?.onLowMemory()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView?.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView?.onSaveInstanceState(outState)
//    }

}

