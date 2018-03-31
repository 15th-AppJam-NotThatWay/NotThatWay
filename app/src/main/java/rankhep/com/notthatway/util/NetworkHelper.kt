package rankhep.com.dhlwn.utils

/**
 * Created by choi on 2017. 7. 15..
 */

import android.content.Context
import android.net.ConnectivityManager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper(private val context: Context) {
    companion object {
        val url = "http://soylatte.kr"
        val port = 3000
        var retrofit: Retrofit? = null

        val networkInstance: RetrofitInterface
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                            .baseUrl(url + ":" + port)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retrofit!!.create(RetrofitInterface::class.java)
            }

        fun returnNetworkState(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
        }
    }
}