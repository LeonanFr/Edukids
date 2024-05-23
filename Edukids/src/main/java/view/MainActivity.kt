package view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import Edukids.R
import android.speech.tts.TextToSpeech
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


class MainActivity : AppCompatActivity(), View.OnClickListener, DataInterface,  TextToSpeech.OnInitListener{

    private lateinit var menuButton: ImageButton
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

        menuButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener(this)

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

        tts = TextToSpeech(this, this)

        setFragment(LoadingFragment())

        fetchData()
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

    private fun fetchData(){
        initExerciseFragment()
        initActivityFragment()
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
                    activityFragment = ActivityFragment.newInstance(activities)
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
            tts?.shutdown()
    }

}
