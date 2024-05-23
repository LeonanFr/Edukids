package view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Edukids.R
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val exercisesButton = findViewById<LinearLayout>(R.id.exerciseButton)
        val homeOverlay = findViewById<LinearLayout>(R.id.home_exercise_overlay)
        val rootView = findViewById<View>(android.R.id.content)


        val delayMillis: Long = 2500

        exercisesButton.setOnClickListener{
            rootView.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
            }, delayMillis)
            homeOverlay.visibility = View.VISIBLE;
            homeOverlay.setOnClickListener{
            }
        }

    }

    override fun onPause() {
        super.onPause()
        findViewById<LinearLayout>(R.id.home_exercise_overlay).visibility = View.GONE
    }
}