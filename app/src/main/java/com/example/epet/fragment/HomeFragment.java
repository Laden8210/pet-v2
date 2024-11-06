package com.example.epet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epet.Model.MissingPet;
import com.example.epet.R;
import com.example.epet.adapter.CardMissingPetAdapter;
import com.example.epet.api.PostTask;
import com.example.epet.calback.GetCallback;
import com.example.epet.calback.PostCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

public class HomeFragment extends Fragment implements GetCallback, PostCallback {


    private RecyclerView rvMissingPet;
    private CardMissingPetAdapter cardMissingPet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvMissingPet = view.findViewById(R.id.rvMissingPet);
        rvMissingPet.setLayoutManager(new LinearLayoutManager(getContext()));

        new PostTask(getContext(), this, "missing_pet", "retrieve-report.php").execute(new JSONObject());

        return view;
    }


    @Override
    public void onGetSuccess(String responseData) {

    }

    @Override
    public void onGetError(String errorMessage) {

    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        List<MissingPet> missingPetList = gson.fromJson(responseData, new TypeToken<List<MissingPet>>(){}.getType());

        cardMissingPet = new CardMissingPetAdapter(getContext(), missingPetList);
        rvMissingPet.setAdapter(cardMissingPet);




    }

    @Override
    public void onPostError(String errorMessage) {

    }
}
