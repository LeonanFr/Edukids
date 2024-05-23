package view

import model.UserAnswer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DataInterface {
    @GET("exercises")
    fun getExercises(): Call<ResponseBody>
    @GET("activities")
    fun getActivities():Call<ResponseBody>
}
