package view

import com.google.gson.annotations.SerializedName
import model.Exercise
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DataInterface {
    @GET("exercises")
    fun getExercises(): Call<ResponseBody>

    @GET("activities")
    fun getActivities():Call<ResponseBody>

}
