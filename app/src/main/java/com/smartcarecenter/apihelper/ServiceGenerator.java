/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  retrofit2.Converter
 *  retrofit2.Converter$Factory
 *  retrofit2.Retrofit
 *  retrofit2.Retrofit$Builder
 *  retrofit2.converter.gson.GsonConverterFactory
 */
package com.smartcarecenter.apihelper;

import com.smartcarecenter.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static String baseurl= "https://api.smartcarecenter.id/";
    public static String ver = BuildConfig.VERSION_NAME;
    private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(150, TimeUnit.SECONDS)
                                .writeTimeout(150, TimeUnit.SECONDS)
                                .build();

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return builder.create(serviceClass);
    }

}

