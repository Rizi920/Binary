package com.example.jobportal.network;


import com.example.jobportal.models.Contacts.ContactsResponse;
import com.example.jobportal.models.insertContact.InsertUserResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiServices {

    @GET("/api/v1/user")
    Call<ContactsResponse> getContacts();

    @POST("/api/v1/user")
    Call<InsertUserResponse> insertContact(@Body RequestBody requestBody);

}

