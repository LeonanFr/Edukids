package model


data class Option (
    private val id: Int,
    private val description: String
){

    fun getId() : Int{
        return this.id
    }

    fun getDescription() : String{
        return this.description
    }

}