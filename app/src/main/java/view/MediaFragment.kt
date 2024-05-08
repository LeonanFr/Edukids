package view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.example.app.R
import model.AgeRange
import model.Media
import model.MediaType




class MediaFragment : Fragment() {

    private lateinit var media : Media
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView


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

        media = getMediaFromExercise()

        showMedia()

        return view
    }

    private fun getMediaFromExercise(): Media {
        return Media(0, "Test", AgeRange.INFANT, MediaType.IMAGE, "")
    }

    private fun showMedia() {
        val mediaResolved = mediaResolver[media.getType()]
        mediaResolved?.let { it() }
    }

    private fun showImage(){
    }

    private fun showText(){

    }

    private fun showVideo(){
    }
    
}