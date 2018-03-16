package com.walinns.walinnsinnovation.waltics_test.DataBase;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by walinnsinnovation on 20/02/18.
 */

public interface ApiClient {
    @POST("users/register")
    Call<ResponseBody> user_register(@Body HashMap<String, String> hashMap);

    @POST("users/social/facebook/sdk/login")
    Call<ResponseBody> fb_login(@Body String hashMap);

    @POST("users/login")
    Call<ResponseBody> user_login(@Body HashMap<String, String> hashMap);

    @POST("data/Post_Data")
    Call<ResponseBody> post_data(@Header("user-token") String user_token, @Body HashMap<String, String> hashMap);

    @GET("data/Post_Data")
    Call<ResponseBody> get_post_data(@Header("user-token") String user_token);

    @Multipart
    @POST
    Call<ResponseBody> uploadAttachment(@Url String url, @Header("user-token") String user_token, @Part MultipartBody.Part filePart);

    @GET("files/my/uploads")
    Call<ResponseBody>getPostDataFile(@Header("user-token") String user_token);
}
