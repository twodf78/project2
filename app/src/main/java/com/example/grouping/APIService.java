package com.example.grouping;

import com.example.grouping.post.PostChatting;
import com.example.grouping.post.PostParty;
import com.example.grouping.post.PostSuggest;
import com.example.grouping.post.PostUser;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("/get/user")
    Call<ResponseBody> getUser(@Query("data") String data);

    @GET("/get/suggest")
    Call<ResponseBody> getSuggest();

    //:id로 찍으면 그냥 인자로 인식해버림
    @GET("/get/suggest/id")
    Call<ResponseBody> getSuggestById(@Query("data") String data);

    @GET("/get/suggest/created_by")
    Call<ResponseBody> getSuggestByUserId(@Query("data") String data);

    @GET("/get/party")
    Call<ResponseBody> getParty(@Query("data") String data);

    @GET("/get/hobby")
    Call<ResponseBody> getHobby(@Query("data") String data);

    @GET("/get/friend")
    Call<ResponseBody> getFriend(@Query("data") String data);

    @GET("/get/title")
    Call<ResponseBody> getTitle(@Query("data") String data);

    @GET("/get/chatting")
    Call<List<PostChatting>> getChatting(@Query("data") String data);



    @POST("/post/user")
    Call<PostUser> postUser(@Body PostUser post);

    @POST("/post/suggest")
    Call<PostSuggest> postSuggest(@Body PostSuggest post);

    @POST("/post/party")
    Call<PostParty> postParty(@Body PostParty post);

    @POST("/post/chatting")
    Call<PostChatting> postChatting(@Body PostChatting post);


    @PUT("/put/user/{id}")
    Call<PostUser> putUser(@Path("id") String id, @Body PostUser post);

    @PUT("/put/suggest/{id}")
    Call<PostSuggest> putSuggest(@Path("id") String id, @Body PostSuggest post);

    @PUT("/put/party/{user_id}")
    Call<PostParty> putParty(@Path("user_id") String user_id, @Body PostParty post);


    @DELETE("/delete/user")
    Call<Void> deleteUser(@Path("id") String id);

    @DELETE("/delete/suggest")
    Call<Void> deleteSuggest(@Path("id") String id);

    @DELETE("/delete/party")
    Call<Void> deleteParty(@Path("id") String id);

}