package view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://sa-east-1.aws.data.mongodb-api.com/app/data-ytyyaqu/endpoint/"
class MainActivity : AppCompatActivity(), View.OnClickListener, DataInterface{

    private lateinit var menuButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var jumpButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var navMainButtons : LinearLayout
    private lateinit var navActivityButtons : LinearLayout
    private lateinit var validateButton : ImageButton


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

        setFragment(LoadingFragment())

        initExerciseFragment()
    }


    override fun getExercises() : Call<ResponseBody>{
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DataInterface::class.java)

        return retrofitBuilder.getExercises()
    }

    override fun getActivities() : Call<ResponseBody>{
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DataInterface::class.java)

        return retrofitBuilder.getActivities()
    }

    private fun initExerciseFragment(){

        getExercises().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    val exercises = response.body()!!
                    val exerciseFragment = ExerciseFragment.newInstance(exercises)
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

    fun initActivityFragment() {
        getActivities().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    val activities = response.body()!!
                    val activityFragment = ActivityFragment.newInstance(activities)
                    setFragment(activityFragment)
                } else {
                    setFragment(EndFragment())
                    Log.e("MainActivity", "Request failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
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
    }
