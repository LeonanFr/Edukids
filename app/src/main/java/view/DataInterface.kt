package view

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface DataInterface {
    @GET("exercises")
    fun getExercises(): Call<ResponseBody>

    @GET("activities")
    fun getActivities():Call<ResponseBody>

}
