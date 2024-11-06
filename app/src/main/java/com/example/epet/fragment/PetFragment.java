package com.example.epet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.adapter.PetAdapter;
import com.example.epet.adapter.PetNameAdapter;
import com.example.epet.api.GetTask;
import com.example.epet.api.PostTask;
import com.example.epet.calback.GetCallback;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.SessionManager;
import com.example.epet.view.AddPetActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PetFragment extends Fragment implements PostCallback {


    private FloatingActionButton fabAddPet;
    private RecyclerView rvPet;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet, container, false);

        fabAddPet = view.findViewById(R.id.fab_add_pet);


        fabAddPet.setOnClickListener(this::addPetAction);

        rvPet = view.findViewById(R.id.rv_pet);

        retrievePets();

        return view;
    }

    private void retrievePets() {



        String userId = String.valueOf(SessionManager.getInstance(getContext()).getSession().getPetOwnerId());

        JSONObject data = new JSONObject();

        try {
            data.put("USER_ID", userId);

            new PostTask(getContext(), this, "Error", "retrieve-pet.php").execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addPetAction(View view) {
        Intent intent = new Intent(getActivity(), AddPetActivity.class);
        startActivity(intent);
    }


    @Override
    public void onPostSuccess(String responseData) {


        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Pet>>(){}.getType();

        List<Pet> pets = gson.fromJson(responseData, listType);

        rvPet.setLayoutManager(new LinearLayoutManager(getContext()));
        PetAdapter petAdapter = new PetAdapter(pets, getContext());
        rvPet.setAdapter(petAdapter);

    }

    @Override
    public void onPostError(String errorMessage) {

    }
}