package model


data class Exercise(
    private val id: String,
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

    fun readQuestion(){
        println(this.description)
    }

    fun getDescription() : String{
        return this.description
    }

    companion object{

    }

}
