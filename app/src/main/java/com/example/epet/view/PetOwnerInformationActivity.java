package com.example.epet.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.Model.User;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.api.PostTask;
import com.example.epet.calback.PostCallback;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PetOwnerInformationActivity extends AppCompatActivity implements PostCallback {

    private TextView tvPetName, tvWeight, tvBreed, tvDob, tvGender, tvMedical;
    private TextView fullname, birthdate, gender, address, username, email;
    private ImageView ivPet;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pet_owner_information);


        tvPetName = findViewById(R.id.tv_pet_name);
        tvWeight = findViewById(R.id.tv_weight);
        tvBreed = findViewById(R.id.tv_breed);
        tvDob = findViewById(R.id.tv_dob);
        tvGender = findViewById(R.id.tv_gender);
        tvMedical = findViewById(R.id.tv_medical);
        ivPet = findViewById(R.id.iv_pet);
        fullname = findViewById(R.id.fullname);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        pet = getIntent().getParcelableExtra("pet");

        if (pet != null) {
            if (pet.getImagePath() != null) {
                String url = (ApiAddress.urlImage + pet.getImagePath()).replace(" ", "");
                Glide.with(ivPet.getContext())
                        .load(url)
                        .into(ivPet);
            } else {
                ivPet.setImageResource(R.drawable.logo);
            }

            tvPetName.setText(pet.getPetName());
            tvWeight.setText(pet.getWeight());
            tvBreed.setText(pet.getBreed());

            tvGender.setText(pet.getGender());


            String postData = null;
            try {
                postData = "user_id=" + URLEncoder.encode(String.valueOf(pet.getOwnerId()), "UTF-8");
                String errorMessage = "";


            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        User user = gson.fromJson(responseData, User.class);
        fullname.setText(user.getFirstName() + " " + user.getMiddleName().charAt(0) + ". "  + user.getLastName());
        birthdate.setText(user.getBirthdate());

        username.setText(user.getUserName());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
    }

    @Override
    public void onPostError(String errorMessage) {

    }
}