package rankhep.com.notthatway.protector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.iid.FirebaseInstanceId
import rankhep.com.dhlwn.utils.NetworkHelper
import rankhep.com.notthatway.R
import rankhep.com.notthatway.model.StateCode
import rankhep.com.notthatway.model.User
import rankhep.com.notthatway.util.DataManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.NetworkInterface
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_protector_main.*
import rankhep.com.notthatway.adapter.LogAdapter
import rankhep.com.notthatway.model.LogModel


class ProtectorMainActivity : AppCompatActivity(), OnMapReadyCallback, LogAdapter.OnItemClickListener {
    lateinit var mMap: GoogleMap
    var items = ArrayList<LogModel>()
    var mAdapter: LogAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_main)
        var token: String? = FirebaseInstanceId.getInstance().token
        var user: User? = DataManager(applicationContext).getUserData()
        sendFcmToken(user?.id, token)
        val fragmentManager = fragmentManager
        val mapFragment = fragmentManager
                .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        userToken.text = "보호자코드 : "+user?.usertoken
        mAdapter = LogAdapter(items,this)
        logList.adapter = mAdapter
        getLog(user?.id!!)
    }

    private fun sendFcmToken(id: String?, token: String?) {
        NetworkHelper.networkInstance.fcmUpdate(id!!, token!!).enqueue(object : Callback<StateCode> {
            override fun onResponse(call: Call<StateCode>?, response: Response<StateCode>?) {
                Toast.makeText(applicationContext, "Hi", Toast.LENGTH_SHORT).show()
                Log.e("asdf", "asd: $id asdf: $token")
            }

            override fun onFailure(call: Call<StateCode>?, t: Throwable?) {
                Log.e("Error", t?.message)
            }

        })
    }

    fun getLog(id:String){
        NetworkHelper.networkInstance.getList(id).enqueue(object :Callback<ArrayList<LogModel>>{
            override fun onFailure(call: Call<ArrayList<LogModel>>?, t: Throwable?) {
                Log.e("Error", t?.message)
            }

            override fun onResponse(call: Call<ArrayList<LogModel>>?, response: Response<ArrayList<LogModel>>?) {
                items.clear()
                items.addAll(response?.body()!!)
                mAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun test(token: String) {
        NetworkHelper.networkInstance.test(token).enqueue(object : Callback<StateCode> {
            override fun onFailure(call: Call<StateCode>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<StateCode>?, response: Response<StateCode>?) {
                Log.e("asd", "sadfsdfa")
            }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val SEOUL = LatLng(37.56, 126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_2rsz_ic_marker))
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        map.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    override fun onItemClickListener(itemView: View, latitude: Double, longitude: Double) {
        val location = LatLng(longitude, latitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_2rsz_ic_marker))
        mMap.addMarker(markerOptions)
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }
}
