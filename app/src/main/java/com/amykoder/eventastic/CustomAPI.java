package com.amykoder.eventastic;

import com.amykoder.eventastic.model.EventModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CustomAPI {
    @GET("index.php?fetchAll=true")
    Call<ArrayList<EventModel>> modelCall();
}
