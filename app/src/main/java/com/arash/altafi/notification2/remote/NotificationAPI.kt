package com.arash.altafi.notification2.remote

import com.arash.altafi.notification2.utils.Constants.SERVER_KEY
import com.arash.altafi.notification2.models.PushNotification
import com.arash.altafi.notification2.utils.Constants.CONTENT_TYPE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}