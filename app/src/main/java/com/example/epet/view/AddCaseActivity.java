package com.example.epet.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.epet.Model.Case;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.adapter.PetAdapter;
import com.example.epet.api.GetTask;
import com.example.epet.api.PostTask;
import com.example.epet.calback.GetCallback;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.example.epet.util.Validition;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AddCaseActivity extends AppCompatActivity implements PostCallback, GetCallback {

    private List<Pet> productList;
    private Spinner petNameSpinner;
    private TextInputLayout tfDescription;
    private Button btnAddCase;
    private Case dogCase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_case);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        petNameSpinner = findViewById(R.id.spinner);
        tfDescription = findViewById(R.id.tf_case);
        btnAddCase = findViewById(R.id.btn_dad_pet);

        String errorMessage = "Login failed. Please check your credentials.";
        new GetTask(this).execute(errorMessage, "get-pet-list.php");
        if(getIntent().hasExtra("case")){
            dogCase = getIntent().getParcelableExtra("case");
            TextView tvCase = findViewById(R.id.tv_case);
            tvCase.setText("Edit Case");
            tfDescription.getEditText().setText(dogCase.getDescription());
            btnAddCase.setOnClickListener(this::updateCaseAction);
            btnAddCase.setText("Edit Case");
            return;
        }
        btnAddCase.setOnClickListener(this::addCaseAction);


    }

    private void updateCaseAction(View view) {
        String petName = null;
        try {

            if(Validition.validateEmpty(new TextInputLayout[]{ tfDescription})){
                Messenger.showAlertDialog(this, "Error", "Fields is empty", "Yes").show();
                return;
            }
            String description = tfDescription.getEditText().getText().toString();


            petName = petNameSpinner.getSelectedItem().toString();
            int petId = 0;
            for (Pet pet: productList){
                if(pet.getPetName() == petName){
                    petId = pet.getPetId();

                }
                Log.d("petId", petId + " : " + pet.getPetName() + " : " +petName);

            }
            String caseId = URLEncoder.encode(String.valueOf(dogCase.getCaseId()), "UTF-8");

            String descriptioEncode = URLEncoder.encode(description, "UTF-8");

            String postData =
                    "caseId= " + caseId +
                            "&description=" + descriptioEncode;

            String errorMessage = "";


        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCaseAction(View view) {
        String petName = null;
        try {

            if(Validition.validateEmpty(new TextInputLayout[]{ tfDescription})){
                Messenger.showAlertDialog(this, "Error", "Fields is empty", "Yes").show();
                return;
            }
            String description = tfDescription.getEditText().getText().toString();


            petName = petNameSpinner.getSelectedItem().toString();
            int petId = 0;
            for (Pet pet: productList){
                if(pet.getPetName() == petName){
                    petId = pet.getPetId();

                }
                Log.d("petId", petId + " : " + pet.getPetName() + " : " +petName);

            }
            String userId = URLEncoder.encode(String.valueOf(SessionManager.getInstance(this).getSession().getPetOwnerId()), "UTF-8");
            String petIdEncode = URLEncoder.encode(String.valueOf(petId), "UTF-8");
            String descriptioEncode = URLEncoder.encode(description, "UTF-8");

            String postData =
                    "userId= " + userId +
                            "&petId=" + petIdEncode +
                            "&description=" + descriptioEncode;

            String errorMessage = "";



        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        if(getIntent().hasExtra("case")){
            Messenger.showAlertDialog(this, "Case", "Case update successfully!", "Ok").show();
            return;
        }
        Messenger.showAlertDialog(this, "Case", "Case created successfully!", "Ok").show();
        tfDescription.getEditText().setText("");

    }

    @Override
    public void onPostError(String errorMessage) {
        Messenger.showAlertDialog(this, "Case", errorMessage, "Ok").show();
    }

    @Override
    public void onGetSuccess(String responseData) {
        Log.d("data", responseData);
        Type productListType = new TypeToken<List<Pet>>() {}.getType();
        Gson gson = new Gson();
        productList = gson.fromJson(responseData, productListType);

        int userId = SessionManager.getInstance(this).getSession().getPetOwnerId();

        List<String> petNames = new ArrayList<>();
        for (Pet pet : productList) {
            if (pet.getOwnerId() == userId){
                petNames.add(pet.getPetName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                petNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petNameSpinner.setAdapter(adapter);

        if(getIntent().hasExtra("case")){{
            setSelectedItem(dogCase.getDogName());

        }}

    }

    private void setSelectedItem(String dogName) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) petNameSpinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(dogName)) {
                petNameSpinner.setSelection(i);
                break;
            }
        }
    }



    @Override
    public void onGetError(String errorMessage) {

    }

}