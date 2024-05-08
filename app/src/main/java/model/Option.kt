package model


data class Option (
    private val id: Int,
    private val description: String
){
    fun readOption(){
        TODO()
    }

    fun getId() : Int{
        return this.id
    }

    companion object{
    }

}