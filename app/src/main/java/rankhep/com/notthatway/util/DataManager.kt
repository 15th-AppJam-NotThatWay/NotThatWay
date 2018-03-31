package rankhep.com.notthatway.util

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import rankhep.com.notthatway.model.User
import android.R.id.edit
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.google.gson.Gson


/**
 * Created by choi on 2018. 4. 1..
 */
class DataManager(var context: Context) {
    var pref: SharedPreferences = context.getSharedPreferences("pref", MODE_PRIVATE)
    lateinit var editor: SharedPreferences.Editor

    init {
        editor = pref.edit()
    }

    fun setUserData(user: User) {
        Log.e("asd",user.username)
        var gson = Gson()
        var json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit()
    }

    fun getUserData(): User? {
        val gson = Gson()
        val json = pref.getString("user", "")
        val obj = gson.fromJson<User>(json, User::class.java)
        return obj
    }
}