package model

data class Activity(
    private val description: String,
    private var isCompleted: Boolean = false,
    private var isReady: Boolean = false,
    private val type: ActivityType,
    private var media: Media?
){
    fun complete(){
        this.isCompleted = true
    }

    fun linkMedia(media: Media){
        this.media = media
        this.isReady = true
    }

    fun unlinkMedia(){
        this.media = null
        this.isReady = false
    }
}
