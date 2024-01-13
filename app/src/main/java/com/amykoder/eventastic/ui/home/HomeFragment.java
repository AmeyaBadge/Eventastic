package com.amykoder.eventastic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amykoder.eventastic.CustomRecyclerEventsAdapter;
import com.amykoder.eventastic.R;
import com.amykoder.eventastic.databinding.FragmentHomeBinding;
import com.amykoder.eventastic.model.EventModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    RecyclerView recyEventsHome;
    ArrayList<EventModel> arrLstEvents = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view is the root returned by the onCreateView method
        //Write code that you usually write in onCreate method of an Activity

        recyEventsHome = binding.recyEventsHome;
        recyEventsHome.setLayoutManager(new LinearLayoutManager(getContext()));

//        arrLstEvents.add(new EventModel(
//                1,
//                "Disco Night",
//                "https://source.unsplash.com/random/612x408/?event",
//                "This is one",
//                "29 May'23",
//                "12:00 PM"));
//        arrLstEvents.add(new EventModel(
//                2,
//                "Bhajan",
//                "https://source.unsplash.com/random/612x408/?event",
//                "This is two",
//                "29 May'23",
//                "12:00 PM"));
//        arrLstEvents.add(new EventModel(
//                3,
//                "Kirtan",
//                "https://source.unsplash.com/random/612x408/?event",
//                "This is three",
//                "29 May'23",
//                "12:00 PM"));

        homeViewModel.getEventsArrList().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventModel>>() {
            @Override
            public void onChanged(ArrayList<EventModel> eventModels) {
                CustomRecyclerEventsAdapter recyclerEventsAdapter = new CustomRecyclerEventsAdapter(getContext(), eventModels);
                recyEventsHome.setAdapter(recyclerEventsAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}