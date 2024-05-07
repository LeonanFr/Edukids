package model

data class Exercise(
    private val id: Int,
    private val description: String,
    private var correctOption: Option?,
    private val isReady: Boolean = false,
    private val points: Int,
    private val medias: MutableList<Media> = mutableListOf(),
    private val options: MutableList<Option> = mutableListOf()
){

    fun answerExercise(option: Option) : Boolean{
        return (option == correctOption)
    }

    fun addOption(option: Option, correct: Boolean) : Boolean{
        if(options.size < 3){
            options.add(option)

            if(correct)
                this.correctOption = option

            return true
        }
        return false
    }

    fun linkMedia(media: Media) : Boolean{
        if(medias.size<3){
            medias.add(media)
            return true
        }
        return false
    }

    fun unlinkMedia(idMedia: Int): Boolean{
        for(i in medias.indices)
        {
            val media = medias[i]
            if(media.getId()==idMedia)
            {
                medias.removeAt(i)
                return true
            }
        }
        return false
    }

    fun readQuestion(){
        println(this.description)
    }

}
