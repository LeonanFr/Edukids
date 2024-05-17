package view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Activity
import model.Exercise
import model.Media
import model.MediaType
import okhttp3.ResponseBody
import java.util.Locale

class ActivityFragment : Fragment(), OnInitListener {
    companion object{
        fun newInstance(activityList: ResponseBody): ActivityFragment{
            val fragment = ActivityFragment()
            val args = Bundle().apply {
                putString("activityList", activityList.string())
            }

            fragment.arguments = args
            return fragment

        }
    }

    private lateinit var activityList: List<Activity>
    private lateinit var activityImage : ImageView
    private lateinit var activityText : TextView
    private lateinit var ttsButton : ImageButton
    private lateinit var overlayHolder : View
    private lateinit var answerOverlayHolder : View
    private lateinit var exitButton: ImageButton
    private lateinit var answerNavOverlay : View
    private lateinit var validateBtn : ImageButton
    private lateinit var validateImage : ImageView
    private lateinit var validateText : TextView
    private lateinit var continueBtn : Button
    private lateinit var password : EditText
    private var currentActivity : Activity? = null
    private var tts : TextToSpeech? = null


    private fun displayActivity(){

        currentActivity = activityList.getOrNull(activityList.indices.random())

        if(currentActivity!=null){
            showImage(currentActivity!!.getMedia())
            activityText.text = currentActivity!!.getDescription()
        } else{
            (activity as MainActivity).setFragment(EndFragment())
        }

    }

    private fun showImage(media : Media){
        if(media.getType() == MediaType.IMAGE)
            Glide.with(this)
                .load(media.getUrl())
                .into(activityImage)
    }

    fun validateActivity(){

        overlayHolder.visibility = View.VISIBLE
        answerNavOverlay.visibility = View.VISIBLE

        validateBtn.setOnClickListener{
            overlayHolder.visibility = View.GONE
            answerOverlayHolder.visibility = View.VISIBLE

            if(currentActivity!!.complete(password.text.toString())){
                validateText.setText(R.string.finished_activity)
                validateImage.setImageResource(R.drawable.correctanswerimage)
                continueBtn.setText(R.string.button_right_text)

                continueBtn.setOnClickListener{
                    (activity as MainActivity).setFragment(EndFragment())
                }

            } else{
                validateText.setText(R.string.wrong_password)
                validateImage.setImageResource(R.drawable.wronganswerimage)
                continueBtn.setText(R.string.back)
                password.text.clear()

                continueBtn.setOnClickListener{
                    answerOverlayHolder.visibility = View.GONE
                    answerNavOverlay.visibility = View.GONE
                }

            }
        }

    }

    private fun speakOut(){

        if(currentActivity!=null){
            tts?.speak(currentActivity!!.getDescription(), TextToSpeech.QUEUE_FLUSH, null,"")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonResponse = arguments?.getString("activityList")
        activityList  = Gson().fromJson(jsonResponse, object : TypeToken<List<Activity>>() {}.type)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_activity, container, false)

        (activity as MainActivity).updateNextButtonVisibility(this)
        activityImage = view.findViewById(R.id.activity_main_image)
        activityText = view.findViewById(R.id.activity_text)
        validateImage = view.findViewById(R.id.validate_image)
        validateText = view.findViewById(R.id.validate_text)
        password = view.findViewById(R.id.password_field)

        (activity as MainActivity).findViewById<LinearLayout>(R.id.nav_main_buttons).visibility = View.GONE
        (activity as MainActivity).findViewById<LinearLayout>(R.id.nav_activity_buttons).visibility = View.VISIBLE

        overlayHolder = view.findViewById(R.id.overlay_holder)
        answerNavOverlay = (activity as MainActivity).findViewById(R.id.nav_overlay)
        answerOverlayHolder = view.findViewById(R.id.finished_activity_view)

        exitButton = view.findViewById(R.id.exit_button)
        validateBtn = view.findViewById(R.id.validate_activity_button)
        continueBtn = view.findViewById(R.id.btn_continue)

        exitButton.setOnClickListener{
            overlayHolder.visibility = View.GONE
            answerNavOverlay.visibility = View.GONE
        }

        answerNavOverlay.setOnClickListener{
        }

        ttsButton = view.findViewById(R.id.tts_button_activity)

        tts = TextToSpeech(activity, this);

        displayActivity()

        return view
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.forLanguageTag("pt-BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "A linguagem especificada não é suportado ou faltam dados")
            } else {
                ttsButton.setOnClickListener{
                    speakOut()
                }
                speakOut()
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
