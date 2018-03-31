package rankhep.com.notthatway.protector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng


class ProtectorMainActivity : AppCompatActivity(), OnMapReadyCallback {
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
    }

    private fun sendFcmToken(id: String?, token: String?) {
        NetworkHelper.networkInstance.fcmUpdate(id!!, token!!).enqueue(object : Callback<StateCode> {
            override fun onResponse(call: Call<StateCode>?, response: Response<StateCode>?) {
                Toast.makeText(applicationContext, "Hi", Toast.LENGTH_SHORT).show()
                Log.e("asdf", "asd: $id asdf: $token")
                test(token)
            }

            override fun onFailure(call: Call<StateCode>?, t: Throwable?) {
                Log.e("Error", t?.message)
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
        val SEOUL = LatLng(37.56, 126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        map.animateCamera(CameraUpdateFactory.zoomTo(10f))
    }
}
