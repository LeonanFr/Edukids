package view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import Edukids.R
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import view.HomeActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val delayMillis: Long = 1000
        findViewById<View>(R.id.activity_loading).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, delayMillis)
    }


}