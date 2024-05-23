package model

class Activity (
    private val _id : Int,
    private val description : String,
    private val isCompleted : Boolean,
    private val isReady : Boolean,
    private val media : Media
){

    fun getMedia() : Media{
        return this.media
    }

    fun getDescription() : String{
        return description
    }

    fun complete(password: String) : Boolean{
        return password=="1234"
    }
}