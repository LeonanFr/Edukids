package view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.example.app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Exercise
import model.Option
import java.util.Locale

class QuestionFragment : Fragment(), TextToSpeech.OnInitListener {

    companion object{
        fun newInstance(description: String, optionsJson: String, correctOptionId: Int, exercise: String) : QuestionFragment{
            val fragment = QuestionFragment()
            val args = Bundle().apply {
                putString("description", description)
                putString("optionsJson", optionsJson)
                putInt("correctOptionId", correctOptionId)
                putString("exercise", exercise)
            }

            fragment.arguments = args
            return fragment

        }
    }

    private lateinit var questionDescription : TextView
    private lateinit var exercise : Exercise
    private lateinit var firstOption : RadioButton
    private lateinit var secondOption : RadioButton
    private lateinit var thirdOption : RadioButton
    private lateinit var validateBtn : ImageButton
    private lateinit var ttsBtn : ImageButton
    private lateinit var answerOverlay : View
    private lateinit var answerNavOverlay : View
    private lateinit var optionList : List<Option>
    private var tts : TextToSpeech? = null
    private var selectedId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_question, container, false)

        optionList = Gson().fromJson(arguments?.getString("optionsJson"), object : TypeToken<List<Option>>(){}.type)
        exercise = Gson().fromJson(arguments?.getString("exercise"), object : TypeToken<Exercise>(){}.type)



        questionDescription = view.findViewById(R.id.questionDescription)
        firstOption = view.findViewById(R.id.firstOption)
        secondOption = view.findViewById(R.id.secondOption)
        thirdOption = view.findViewById(R.id.thirdOption)
        validateBtn = view.findViewById(R.id.validateButton)
        ttsBtn = view.findViewById(R.id.tts_button)
        answerOverlay = view.findViewById(R.id.answerOverlay)
        answerNavOverlay = (activity as MainActivity).findViewById(R.id.nav_overlay)


        firstOption.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
                selectedId = 0
        }

        secondOption.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
                selectedId = 1
        }


        thirdOption.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
                selectedId = 2
        }

        validateBtn.setOnClickListener{
            if(selectedId!=-1)
                validateQuestion(view)
        }

        answerOverlay.setOnClickListener{
        }

        answerNavOverlay.setOnClickListener{
        }

        questionDescription.text = arguments?.getString("description")
        firstOption.text = optionList[0].getDescription()
        secondOption.text = optionList[1].getDescription()
        thirdOption.text = optionList[2].getDescription()

        val jumpBtn = (activity as MainActivity).findViewById<ImageView>(R.id.jump_button)

        jumpBtn.setImageResource(R.drawable.jumpbutton_clickable)
        jumpBtn.isClickable = true

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(activity, this)

    }

    private fun turnOverlayOn(){
        answerOverlay.visibility = View.VISIBLE
        answerOverlay.isClickable = true
        answerNavOverlay.visibility = View.VISIBLE
        answerNavOverlay.isClickable = true
    }

    private fun validateQuestion(v: View) {

        turnOverlayOn()

        val isCorrect = (exercise.answerExercise(optionList[selectedId]))

        val textWrongOrRight = v.findViewById<TextView>(R.id.textWrongOrRight)
        val actionWrongOrRight = v.findViewById<TextView>(R.id.textActionWrongOrRight)
        val buttonWrongOrRight = v.findViewById<TextView>(R.id.btnWrongOrRight)
        val imageWrongOrRight = v.findViewById<ImageView>(R.id.imageWrongOrRight)

        if(isCorrect){

            textWrongOrRight.text = getString(R.string.right_text)
            actionWrongOrRight.text = getString(R.string.action_right_text)
            buttonWrongOrRight.text = getString(R.string.button_right_text)
            imageWrongOrRight.setImageResource(R.drawable.correctanswerimage)

            buttonWrongOrRight.setOnClickListener{

                val exerciseFragment = (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.fragment_container) as? ExerciseFragment
                exerciseFragment?.displayNextFragment()
                answerNavOverlay.visibility=View.GONE
                answerNavOverlay.isClickable = false
            }
        } else{
            textWrongOrRight.text = getString(R.string.wrong_text)
            actionWrongOrRight.text = getString(R.string.action_wrong_text)
            buttonWrongOrRight.text = getString(R.string.button_wrong_text)
            imageWrongOrRight.setImageResource(R.drawable.wronganswerimage)

            buttonWrongOrRight.setOnClickListener{

                answerOverlay.visibility=View.GONE
                answerOverlay.isClickable = false
                answerNavOverlay.visibility=View.GONE
                answerNavOverlay.isClickable = false
            }

        }

    }

    private fun speakOut(textToRead : String){

        tts?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null,"")

    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.forLanguageTag("pt-BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "A linguagem especificada não é suportado ou faltam dados")
                ttsBtn.isEnabled = false
            } else {
                speakOut(questionDescription.text.toString())
                ttsBtn.setOnClickListener {
                    speakOut(questionDescription.text.toString())
                }
                firstOption.setOnClickListener {
                    speakOut(optionList[0].getDescription())
                }
                secondOption.setOnClickListener {
                    speakOut(optionList[1].getDescription())
                }
                thirdOption.setOnClickListener {
                    speakOut(optionList[2].getDescription())
                }
                ttsBtn.isEnabled = true
            }
        } else {
            Log.e("TTS", "A inicialização falhou")
            ttsBtn.isEnabled = false
        }
    }

    override fun onPause() {
        super.onPause()
        tts?.stop()
        tts?.shutdown()
    }

}