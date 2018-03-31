package rankhep.com.notthatway

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import rankhep.com.dhlwn.utils.NetworkHelper
import rankhep.com.notthatway.model.User
import rankhep.com.notthatway.protector.ProtectorMainActivity
import rankhep.com.notthatway.util.DataManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        login.setOnClickListener {
            var id = id.text.toString()
            var pwd = pwd.text.toString()
            if (id != "" && pwd != "")
                sendLoginData(id, pwd)
        }
    }

    private fun sendLoginData(id: String, pwd: String) {
        NetworkHelper.networkInstance.login(id, pwd).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                var user: User = response?.body()!!
                DataManager(applicationContext).setUserData(user)
                if (user.usertype)
                    startActivity(Intent(applicationContext, ProtectorMainActivity::class.java))
                else
                    startActivity(Intent(applicationContext, BlindnessActivity::class.java))
                finish()
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.e("Error", t?.message)
            }
        })
    }

}
