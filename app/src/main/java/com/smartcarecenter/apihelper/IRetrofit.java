/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  java.lang.Object
 *  okhttp3.MultipartBody
 *  okhttp3.MultipartBody$Part
 *  okhttp3.RequestBody
 *  retrofit2.Call
 *  retrofit2.http.Body
 *  retrofit2.http.Headers
 *  retrofit2.http.Multipart
 *  retrofit2.http.POST
 *  retrofit2.http.Part
 */
package com.smartcarecenter.apihelper;

import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IRetrofit {
    @POST("api/FormRequest/Cancel")
    public Call<JsonObject> postRawJSONcancelform(@Body JsonObject var1);

    @POST("api/Account/ChangeLanguage")
    public Call<JsonObject> postRawJSONchangelanguage(@Body JsonObject var1);

    @POST("api/Account/ChangePassword")
    public Call<JsonObject> postRawJSONchangepassword(@Body JsonObject var1);

    @POST("api/Account/Config")
    public Call<JsonObject> postRawJSONconfig(@Body JsonObject var1);

    @POST("api/FormRequest/Confirm")
    public Call<JsonObject> postRawJSONconfirm(@Body JsonObject var1);

    @POST("api/FormRequest/AddNoImage")
    public Call<JsonObject> postRawJSONformadd(@Body JsonObject var1);

    @POST("api/FormRequest/Get")
    public Call<JsonObject> postRawJSONgetform(@Body JsonObject var1);

    @POST("api/Notification/Get")
    public Call<JsonObject> postRawJSONgetnotifget(@Body JsonObject var1);

    @POST("api/Notification/List")
    public Call<JsonObject> postRawJSONgetnotiflist(@Body JsonObject var1);

    @POST("api/Account/ChangeLanguage")
    public Call<JsonObject> postRawJSONlanguage(@Body JsonObject var1);

    @POST("api/FormRequest/List")
    public Call<JsonObject> postRawJSONlistform(@Body JsonObject var1);

    @POST("api/Account/Login")
    public Call<JsonObject> postRawJSONlogin(@Body JsonObject var1);

    @POST("api/Account/Logout")
    public Call<JsonObject> postRawJSONlogout(@Body JsonObject var1);

    @POST("api/news/List")
    public Call<JsonObject> postRawJSONnews(@Body JsonObject var1);

    @POST("api/Account/Ping")
    public Call<JsonObject> postRawJSONping(@Body JsonObject var1);

    @POST("api/Press/List")
    public Call<JsonObject> postRawJSONpresslist(@Body JsonObject var1);

    @POST("api/FOCOrder/GetItemList")
    public Call<JsonObject> list_add_item_foc(@Body JsonObject var1);

    @POST("api/store/getdaftarkota")
    Call<JsonObject> postRawJSON(@Body JsonObject locationPost);

    @Multipart
    @POST("api/FormRequest/Add")
    public Call<JsonObject> uploadImage(@Part MultipartBody.Part multipart,
                                        @Part("sessionId") RequestBody sessionId,
                                        @Part("pressId") RequestBody pressId,
                                        @Part("description") RequestBody description);
}

