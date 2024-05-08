package model


data class Media(
    private val id: Int,
    private val title: String,
    private val ageRange: AgeRange,
    private val mediaType: MediaType,
    private val url: String
){
    fun getId() : Int
    {
        return this.id
    }

    fun getType() : MediaType{
        return this.mediaType
    }

    companion object{
    }

}