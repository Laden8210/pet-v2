package com.example.epet.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.api.PostTask;
import com.example.epet.api.UploadPetAPI;
import com.example.epet.calback.ImageUploader;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Loader;
import com.example.epet.util.Messenger;

import com.example.epet.util.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPetActivity extends AppCompatActivity implements ImageUploader.UploadCallback, PostCallback {


    private static final int PICK_IMAGE_REQUEST = 2;
    private TextInputLayout etPetName;
    private TextInputLayout etAge;
    private TextInputLayout etBirthdate;
    private TextInputLayout etColor;
    private TextInputLayout etType;
    private Spinner spGender;
    private TextInputLayout etBreed;
    private TextInputLayout etWeight;
    private TextInputLayout etHeight;
    private TextInputLayout etDescription;
    private Button btnAddPet;
    private ImageView ivPetImage;
    private Button btnUploadImage;

    private Loader loader = new Loader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Initialize components
        etPetName = findViewById(R.id.petname);
        etAge = findViewById(R.id.age);
        etType = findViewById(R.id.type);
        etBreed = findViewById(R.id.breed);
        etColor = findViewById(R.id.color);
        etDescription = findViewById(R.id.description);
        spGender = findViewById(R.id.sp_gender);

        btnAddPet = findViewById(R.id.addPet);
        etBirthdate = findViewById(R.id.birthdate);
        etWeight = findViewById(R.id.weight);
        etHeight = findViewById(R.id.height);
        ivPetImage = findViewById(R.id.image);
        btnUploadImage = findViewById(R.id.uploadImage);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUploadImage.setOnClickListener(this::uploadImageAction);

        btnAddPet.setOnClickListener(this::addPet);


    }


    private void uploadImageAction(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(ivPetImage);
            }
        }
    }


    private void addPet(View view) {

        String petName = etPetName.getEditText().getText().toString().trim();
        String age = etAge.getEditText().getText().toString().trim();
        String type = etType.getEditText().getText().toString().trim();
        String breed = etBreed.getEditText().getText().toString().trim();
        String color = etColor.getEditText().getText().toString().trim();
        String description = etDescription.getEditText().getText().toString().trim();
        String gender = spGender.getSelectedItem().toString();
        String birthdate = etBirthdate.getEditText().getText().toString().trim();
        String weight = etWeight.getEditText().getText().toString().trim();
        String height = etHeight.getEditText().getText().toString().trim();

        byte[] image = imageToBase64(((BitmapDrawable) ivPetImage.getDrawable()).getBitmap());

        uploadPet(petName, age, type, breed, color, description, birthdate, weight, height, gender, image);


    }


    private void uploadPet(String petName, String age, String type, String breed, String color, String description, String birthdate, String weight, String height, String gender, byte[] image) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiAddress.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UploadPetAPI uploadPetAPI = retrofit.create(UploadPetAPI.class);

        Log.d("Upload", image.toString());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image);
        String filename = System.currentTimeMillis() + ".jpg";
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", filename, requestFile);

        RequestBody petNameBody = RequestBody.create(MediaType.parse("text/plain"), petName);
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), age);
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody breedBody = RequestBody.create(MediaType.parse("text/plain"), breed);
        RequestBody colorBody = RequestBody.create(MediaType.parse("text/plain"), color);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody birthdateBody = RequestBody.create(MediaType.parse("text/plain"), birthdate);
        RequestBody weightBody = RequestBody.create(MediaType.parse("text/plain"), weight);
        RequestBody heightBody = RequestBody.create(MediaType.parse("text/plain"), height);
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SessionManager.getInstance(this).getSession().getPetOwnerId()));

        Call<ResponseBody> call = uploadPetAPI.uploadPet(body, petNameBody, ageBody, birthdateBody, colorBody, typeBody, genderBody, breedBody, weightBody, heightBody, descriptionBody, userIdBody);
        loader.showLoader(this);

        Log.d("URL" , call.request().url().toString());
        Log.d("View Body", call.request().body().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loader.dismissLoader();
                        if (response.isSuccessful()) {

                            try {
                                String jsonResponse = response.body().string();

                                Log.d("Upload", jsonResponse);
                                JSONObject jsonObject = new JSONObject(jsonResponse);
                                String message = jsonObject.getString("message");

                                Messenger.showAlertDialog(AddPetActivity.this, "Success", message, "Ok").show();



                                etPetName.getEditText().setText("");
                                etAge.getEditText().setText("");
                                etType.getEditText().setText("");
                                etBreed.getEditText().setText("");
                                etColor.getEditText().setText("");
                                etDescription.getEditText().setText("");

                                etBirthdate.getEditText().setText("");
                                etWeight.getEditText().setText("");
                                etHeight.getEditText().setText("");
                                spGender.setSelection(0);
                                ivPetImage.setImageResource(0);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Messenger.showAlertDialog(AddPetActivity.this, "Error", "Failed to upload image", "Ok").show();
                        }
                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loader.dismissLoader(); // Dismiss the loader after a delay
                        Log.d("Upload", "Error: " + t.getMessage());
                        Messenger.showAlertDialog(AddPetActivity.this, "Error", t.getMessage(), "Ok").show();
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void onUploadComplete(String response) {

    }

    @Override
    public void onPostSuccess(String responseData) {
        if (getIntent().hasExtra("pet")) {
            Messenger.showAlertDialog(this, "Pet Update", "Pet updated successfully", "Ok").show();
            return;
        }

        Messenger.showAlertDialog(this, "Pet Added", "Pet added successfully", "Ok", "Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AddPetActivity.this, HeroActivity.class));
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onPostError(String errorMessage) {
        Messenger.showAlertDialog(this, "Data Error", errorMessage, "Ok").show();
    }

    private byte[] imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
