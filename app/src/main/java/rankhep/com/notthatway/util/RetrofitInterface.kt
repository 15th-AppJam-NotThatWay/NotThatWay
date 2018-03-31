package rankhep.com.dhlwn.utils


import rankhep.com.notthatway.model.LogModel
import rankhep.com.notthatway.model.StateCode
import rankhep.com.notthatway.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by choi on 2017. 7. 15..
 */

interface RetrofitInterface {
    @POST("/auth/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("id") id: String,
                 @Field("password") pwd: String,
                 @Field("type") type: Int,
                 @Field("guardiantoken") guardianToken: String = ""): Call<StateCode>

    @POST("/auth/login")
    @FormUrlEncoded
    fun login(@Field("id") id: String,
              @Field("password") pwd: String): Call<User>

    @POST("/auth/fcmupdate")
    @FormUrlEncoded
    fun fcmUpdate(@Field("id") id: String,
                  @Field("fcmtoken") token: String): Call<StateCode>

    @POST("/test")
    @FormUrlEncoded
    fun test(@Field("fcmtoken") token: String): Call<StateCode>

    @POST("/danger/push")
    @FormUrlEncoded
    fun sendPush(@Field("id") id: String,
                 @Field("latitude") latitude: Double,
                 @Field("longitude") longitude: Double): Call<StateCode>

    @POST("/danger/list")
    @FormUrlEncoded
    fun getList(@Field("id") id: String): Call<ArrayList<LogModel>>
//    일반유저 가입시
//    username, id, password, type, guardiantoken(보호자 유저토큰)

//    보호자 가입시
//    username, id, password, type(보호자 : true, 일반유저 : false)

}