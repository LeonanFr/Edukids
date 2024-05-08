package view

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import com.example.app.R
import controller.ExerciseController

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var menuButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var jumpButton: ImageButton

    private  var mediaFragment = MediaFragment()
    private var questionFragment = QuestionFragment()
    private var activityFragment = ActivityFragment()

    private var exerciseController = ExerciseController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener(this)

        likeButton = findViewById(R.id.like_button)

        jumpButton = findViewById(R.id.jump_button)

        setFragment(mediaFragment)



    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onClick(v: View) {

    }

}
