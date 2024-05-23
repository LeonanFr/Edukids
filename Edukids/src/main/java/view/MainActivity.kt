package view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import Edukids.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.TextView
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Exercise
import model.UserAnswer
import okhttp3.ResponseBody
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


class MainActivity : AppCompatActivity(), View.OnClickListener, DataInterface,  TextToSpeech.OnInitListener{

    private lateinit var menuButton: ImageButton
    private lateinit var activityMenuButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var jumpButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var navMainButtons : LinearLayout
    private lateinit var navActivityButtons : LinearLayout
    private lateinit var validateButton : ImageButton
    private lateinit var activityFragment: ActivityFragment
    private var tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()

        tts = TextToSpeech(this, this)

        menuButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener(this)

        activityMenuButton = findViewById(R.id.activity_menu_button)
        activityMenuButton.setOnClickListener(this)

        likeButton = findViewById(R.id.like_button)
        likeButton.setOnClickListener(this)

        jumpButton = findViewById(R.id.jump_button)
        jumpButton.setOnClickListener(this)

        nextButton = findViewById(R.id.downbutton)
        nextButton.setOnClickListener(this)

        validateButton = findViewById(R.id.validate_button)
        validateButton.setOnClickListener(this)

        navMainButtons = findViewById(R.id.nav_main_buttons)
        navActivityButtons = findViewById(R.id.nav_activity_buttons)

    }


    override fun getExercises() : Call<ResponseBody>{
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiKey)
            .build()
            .create(DataInterface::class.java)

        return retrofitBuilder.getExercises()
    }

    override fun getActivities() : Call<ResponseBody>{
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiKey)
            .build()
            .create(DataInterface::class.java)

        return retrofitBuilder.getActivities()
    }

    private fun fetchData() {
        initActivityFragment()
        initExerciseFragment()
    }

    private fun initExerciseFragment(){

        getExercises().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {

                    val exercises = response.body()!!

                    val exerciseFragment = ExerciseFragment.newInstance(tts!!, exercises)
                    setFragment(exerciseFragment)
                    navMainButtons.visibility = View.VISIBLE
                    navActivityButtons.visibility = View.GONE
                } else {
                    Log.e("MainActivity", "Request failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
    }


    private fun initActivityFragment() {
        getActivities().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    val activities = response.body()!!
                    activityFragment = ActivityFragment.newInstance(tts!!, activities)
                } else {
                    Log.e("MainActivity", "Request failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun displayActivityFragment(){
        setFragment(activityFragment)
    }

    fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
            }
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun updateNextButtonVisibility(fragment: Fragment){
        if(fragment is MediaFragment){
            nextButton.visibility = View.VISIBLE
        } else{
            nextButton.visibility = View.GONE
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onClick(v: View) {
        when(v.id){

            R.id.downbutton -> {
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (currentFragment is ExerciseFragment){
                    currentFragment.displayNextFragment()
                }
            }
            R.id.jump_button -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                if (currentFragment is ExerciseFragment)
                    currentFragment.displayNextFragment()

            }

            R.id.like_button ->{
                val textView = findViewById<TextView>(R.id.liked_view)
                textView.visibility = View.VISIBLE
                likeButton.setImageResource(R.drawable.likeheart_unable)
                likeButton.isClickable = false
                GlobalScope.launch {
                    delay(2000)
                    runOnUiThread {
                        findViewById<TextView>(R.id.liked_view).animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction {
                                textView.visibility = View.GONE
                                textView.alpha = 1f
                            }
                    }
                }

            }

            R.id.menu_button ->{
                startActivity(Intent(this, HomeActivity::class.java))
            }

            R.id.activity_menu_button ->{
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.validate_button ->{
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                if(currentFragment is ActivityFragment){
                        currentFragment.validateActivity()
                }
            }

        }

        }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.forLanguageTag("pt-BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "A linguagem especificada não é suportado ou faltam dados")
            }
        } else {
            Log.e("TTS", "A inicialização falhou")
        }
    }

    override fun onPause() {
        super.onPause()
            tts?.stop()
    }

}
