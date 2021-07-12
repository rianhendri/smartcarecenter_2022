package com.smartcarecenter.SendNotificationPack;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAADwaJqjU:APA91bF0ZiITzpXQwXhOC5VQLcXl0vJlRVC3okO0Un3-NZ1bd1MaQjSkX4QvGZufu2_MxxnNGGtZQL-2rWsDRouhvT-OUVSvcMFyV7WrhrdkeHqLn2lkpf8DK8HFhKfGtqSV1c6kdtdL" // Your server key refer to video for finding your server key
            }
    )
    @POST("fcm/send")
    public Call<JsonObject> sendNotifcation(@Body JsonObject var1);
//    @POST("fcm/send")
//    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

