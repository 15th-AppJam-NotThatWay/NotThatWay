package rankhep.com.notthatway.blindness

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import rankhep.com.notthatway.R

class BlindnessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blindness)

        var intent = Intent(applicationContext, BlindnessService::class.java)
        startService(intent)
    }
}
