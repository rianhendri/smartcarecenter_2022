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

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {



    public static String baseurl= "https://api.smartcarecenter.id/";
    public static String fcmbase= "https://fcm.googleapis.com/";
    public static String ver = BuildConfig.VERSION_NAME;
//    private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
//                                .connectTimeout(100, TimeUnit.SECONDS)
//                                .readTimeout(150, TimeUnit.SECONDS)
//                                .writeTimeout(150, TimeUnit.SECONDS)
//                                .build();

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        return builder.create(serviceClass);
    }
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

