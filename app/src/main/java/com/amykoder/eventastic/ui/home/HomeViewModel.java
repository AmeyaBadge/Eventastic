package com.amykoder.eventastic.ui.home;

import android.media.metrics.Event;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.amykoder.eventastic.CustomAPI;
import com.amykoder.eventastic.CustomRecyclerEventsAdapter;
import com.amykoder.eventastic.model.EventModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {

    private String baseURL = "https://amykoder.000webhostapp.com/android/";
    private CustomAPI api;
    RecyclerView recyEventsHome;
    CustomRecyclerEventsAdapter recyEventsAdapter;
    private MutableLiveData<ArrayList<EventModel>> arrLstEvents = new MutableLiveData<>();
//    private final MutableLiveData<ArrayList<EventModel>> mText;

    public HomeViewModel() {
        viewJsonData();
    }
    public LiveData<ArrayList<EventModel>> getEventsArrList(){
        return arrLstEvents;
    }
    private void viewJsonData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(CustomAPI.class);
        Call<ArrayList<EventModel>> arrayListCall  = api.modelCall();
        arrayListCall.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                arrLstEvents.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {

            }
        });

    }

}