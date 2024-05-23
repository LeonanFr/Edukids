package view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import Edukids.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import model.MediaType


class MediaFragment(tts: TextToSpeech) : Fragment(){

    companion object{
        fun newInstance(tts: TextToSpeech, mediaUrl : String, mediaType: String, mediaTitle : String) : MediaFragment{

            val fragment = MediaFragment(tts)

            val args = Bundle().apply {
                putString("mediaUrl", mediaUrl)
                putString("mediaType", mediaType)
                putString("mediaTitle", mediaTitle)
            }

            fragment.arguments = args
            return fragment

        }
    }

    private var ytPlayer: YouTubePlayer? = null
    private lateinit var mediaType : MediaType
    private lateinit var mediaUrl : String
    private lateinit var mediaTitle : String
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private var videoView: YouTubePlayerView? = null
    private var tts : TextToSpeech? = tts

    private val mediaResolver: Map<MediaType, () -> Unit> = mapOf(
        MediaType.IMAGE to { showImage() },
        MediaType.TEXT to { showText() },
        MediaType.VIDEO to { showVideo() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_media, container, false)

        textView = view.findViewById(R.id.textView)
        imageView = view.findViewById(R.id.imageView)
        videoView = view.findViewById(R.id.videoView)

        mediaUrl = arguments?.getString("mediaUrl").toString()
        mediaType = arguments?.getString("mediaType")?.let { MediaType.valueOf(it) }!!
        mediaTitle = arguments?.getString("mediaTitle").toString()

        showMedia()

        val jumpBtn = (activity as MainActivity).findViewById<ImageView>(R.id.jump_button)

        jumpBtn.setImageResource(R.drawable.jumpbutton_unclickable)
        jumpBtn.isClickable = false

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speakOut()
    }

    private fun showMedia() {
        val mediaResolved = mediaResolver[mediaType]
        mediaResolved?.let { it() }
    }

    private fun showImage(){
        imageView.visibility = View.VISIBLE
        Glide.with(this)
            .load(mediaUrl)
            .into(imageView)
    }

    private fun showText(){
        textView.visibility = View.VISIBLE
        textView.text = mediaTitle
    }

    private fun showVideo(){

        videoView!!.visibility = View.VISIBLE

        videoView!!.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                ytPlayer = youTubePlayer
                ytPlayer!!.loadVideo(mediaUrl, 0f)
                ytPlayer!!.play()
            }
        })
    }

    private fun speakOut(){
        if(mediaType==MediaType.TEXT){
            tts?.speak(mediaTitle, TextToSpeech.QUEUE_FLUSH, null,"")
        }
    }

}