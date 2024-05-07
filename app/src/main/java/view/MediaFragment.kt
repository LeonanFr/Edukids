package view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import model.AgeRange
import model.Media
import model.MediaType




class MediaFragment : Fragment() {

    private lateinit var media : Media

    private val mediaResolver: Map<MediaType, () -> Unit> = mapOf(
        MediaType.IMAGE to { showImage() }

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_media, container, false)

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
        println("Worked")
    }
    
}