package view

import com.google.gson.annotations.SerializedName
import model.Exercise
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/*
    private val apiKey = "OnPG334qLDJxAOOBMja8p4MCJQa8laGelIzQkhCZXlKiwrGT1mGFAXvlFuebGG9Q"

    {
        "collection":"exercises",
        "database":"edukids",
        "dataSource":"Cluster0",
        "filter": {"_id": 1}
    }

*/

interface DataInterface {
    @GET("exercises")
    fun getData(): Call<ResponseBody>
}
