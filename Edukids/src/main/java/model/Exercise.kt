package model


data class Exercise(
    private val _id: Int,
    private val description: String,
    private var correctOptionId: Int,
    private val isReady: Boolean,
    private val points: Int,
    private val medias: MutableList<Media>,
    private val options: MutableList<Option>
){

    fun answerExercise(option: Option) : Boolean{
        return (option == options[correctOptionId])
    }

    fun getId() : Int{
        return this._id
    }

    fun getDescription() : String{
        return this.description
    }

    fun getMedia() : List<Media>{
        return this.medias
    }

    fun getOptions() : List<Option>{
        return this.options
    }

    fun getCurrentOptionId() : Int{
        return this.correctOptionId
    }

}
