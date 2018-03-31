package rankhep.com.notthatway

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import rankhep.com.dhlwn.utils.NetworkHelper
import rankhep.com.notthatway.model.StateCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    var isProtector = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        checkCode.visibility = View.GONE
        protector.setOnClickListener(this)
        blindness.setOnClickListener(this)

        complete.setOnClickListener {
            var name = name.text.toString()
            var pwd = pwd.text.toString()
            var id = id.text.toString()
            var checkCode = checkCode.text.toString()
            if (isProtector == 1) {
                if (name != "" &&
                        pwd != "" &&
                        id != "") {
                    sendUserData(name, id, pwd, isProtector)
                } else
                    Toast.makeText(applicationContext, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (name != "" &&
                        pwd != "" &&
                        id != "" &&
                        checkCode != "") {
                    sendUserData(name, id, pwd, isProtector, checkCode)
                } else
                    Toast.makeText(applicationContext, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            protector -> {
                isProtector = 1
                protector.run {
                    backgroundTintList = AppCompatResources.getColorStateList(applicationContext, R.color.colorPrimary)
                    setTextColor(Color.parseColor("#ffffff"))
                }
                blindness.run {
                    backgroundTintList = null
                    setTextColor(Color.parseColor("#8384B4"))
                    setBackgroundResource(R.drawable.ic_register_select)
                }
                checkCode.visibility = View.GONE
            }
            blindness -> {
                isProtector = 0
                protector.run {
                    backgroundTintList = null
                    setBackgroundResource(R.drawable.ic_register_select)
                    setTextColor(Color.parseColor("#8384B4"))
                }
                blindness.run {
                    backgroundTintList = AppCompatResources.getColorStateList(applicationContext, R.color.colorPrimary)
                    setTextColor(Color.parseColor("#ffffff"))
                }
                checkCode.visibility = View.VISIBLE
            }
        }
    }

    private fun sendUserData(name: String, id: String, pwd: String, type: Int, guardiantoken: String = "") {
        NetworkHelper.networkInstance.register(name, id, pwd, type).enqueue(object : Callback<StateCode> {
            override fun onFailure(call: Call<StateCode>?, t: Throwable?) {
                Log.e("Error", t?.message)
            }

            override fun onResponse(call: Call<StateCode>?, response: Response<StateCode>?) {
                Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                finish()
            }

        })
    }
}
