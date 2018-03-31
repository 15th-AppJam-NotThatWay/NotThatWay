package rankhep.com.notthatway.blindness

import android.R.id.edit
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import rankhep.com.dhlwn.utils.NetworkHelper
import rankhep.com.notthatway.R
import rankhep.com.notthatway.model.StateCode
import rankhep.com.notthatway.model.User
import rankhep.com.notthatway.util.DataManager
import rankhep.com.notthatway.util.GpsInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Compiler.enable


/**
 * Created by choi on 2018. 4. 1..
 */
class BlindnessService : Service() {
    var locationLatitude: Double? = 0.0
    var locationLongitude: Double? = 0.0
    var bool = true
    var locationManager :GpsInfo? = null
    lateinit var bt: BluetoothSPP
    var user: User? = null



    override fun onCreate() {
        super.onCreate()
        bt = BluetoothSPP(this)
        locationManager = GpsInfo(applicationContext)
        if (!bt.isBluetoothEnabled) {
            bt.enable()
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                setup()
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        user = DataManager(applicationContext).getUserData()

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                Toast.makeText(applicationContext, "블루투스와 연결이 해제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnected(name: String?, address: String?) {
                Toast.makeText(applicationContext, "블루투스에 연결되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() {
                Toast.makeText(applicationContext, "블루투스와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

        })

        bt.setAutoConnectionListener(object : BluetoothSPP.AutoConnectionListener {
            override fun onNewConnection(name: String, address: String) {

            }

            override fun onAutoConnectionStarted() {

            }
        })


        bt.setOnDataReceivedListener { data, message ->
            var msg: Int = Integer.parseInt(message)
            Log.e("asd", "+" + msg)
            if (msg == 1) {
                if (bool) {
                    Log.e("asd", "asdasdasdas")
                    locationLongitude = locationManager?.getLocation()?.longitude
                    locationLatitude = locationManager?.getLocation()?.latitude
                    sendPush(user?.id!!, locationLatitude!!, locationLongitude!!)
                }
                bool = false
            } else
                bool = true
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun sendPush(id: String, longitude: Double, latitude: Double) {
        NetworkHelper.networkInstance.sendPush(id, latitude, longitude).enqueue(object : Callback<StateCode> {
            override fun onFailure(call: Call<StateCode>?, t: Throwable?) {
                Log.e("Error++", t?.message)
            }

            override fun onResponse(call: Call<StateCode>?, response: Response<StateCode>?) {
                Log.e("asd", "asd")
            }

        })
    }


    private fun setup() {
        bt.autoConnect("TEST_PHONE")
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onBind(p0: Intent?): IBinder? = null

}