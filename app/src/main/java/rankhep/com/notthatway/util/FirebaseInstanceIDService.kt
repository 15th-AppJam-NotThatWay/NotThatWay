package rankhep.com.notthatway.util


/**
 * Created by choi on 2018. 4. 1..
 */

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class FirebaseInstanceIDService : FirebaseInstanceIdService() {

    // [START refresh_token]
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val token = FirebaseInstanceId.getInstance().token

    }

    companion object {

        private val TAG = "MyFirebaseIIDService"
    }

}