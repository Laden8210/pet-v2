package com.example.epet.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.api.UploadPetAPI;
import com.example.epet.util.Loader;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportMissingActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    private TextView tvPetName;
    private TextView tvWeight;
    private TextView tvBreed;
    private TextView tvDob;
    private TextView tvGender;

    private Button btnSubmit;
    private Button btnUploadPhoto;

    private TextInputLayout tilLocation, tilReport, tilDate, tilTime;
    private ImageView ivPet;
    private Loader loader = new Loader();
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_missing);

        tvPetName = findViewById(R.id.tv_pet_name);
        tvWeight = findViewById(R.id.tv_weight);
        tvBreed = findViewById(R.id.tv_breed);
        tvDob = findViewById(R.id.tv_dob);
        tvGender = findViewById(R.id.tv_gender);

        tilLocation = findViewById(R.id.til_location);
        tilReport = findViewById(R.id.til_report);
        tilDate = findViewById(R.id.til_date);
        tilTime = findViewById(R.id.til_time);
        ivPet = findViewById(R.id.iv_pet);


        btnSubmit = findViewById(R.id.btn_submit);
        btnUploadPhoto = findViewById(R.id.btn_menu);

        if (getIntent().hasExtra("pet")) {
            pet = getIntent().getParcelableExtra("pet");

        }

        setupDefaultValues();

        btnSubmit.setOnClickListener(this::submitReportAction);
        btnUploadPhoto.setOnClickListener(this::uploadPhotoAction);
    }

    private void uploadPhotoAction(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void submitReportAction(View view) {
        String location = tilLocation.getEditText().getText().toString();
        String report = tilReport.getEditText().getText().toString();
        String date = tilDate.getEditText().getText().toString();
        String time = tilTime.getEditText().getText().toString();

        byte[] image = imageToBase64(((BitmapDrawable) ivPet.getDrawable()).getBitmap());

        submitReport(location, report, date, time, image);
    }

    private void submitReport(String location, String report, String date, String time, byte[] image) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiAddress.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UploadPetAPI uploadPetAPI = retrofit.create(UploadPetAPI.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image);
        String filename = System.currentTimeMillis() + ".jpg";
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", filename, requestFile);

        RequestBody locationBody = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody reportBody = RequestBody.create(MediaType.parse("text/plain"), report);
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"),String.valueOf( SessionManager.getInstance(this).getSession().getPetOwnerId()));
        RequestBody petIdBody = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(pet.getPetId()));


        Call<ResponseBody> call = uploadPetAPI.submitReport(body, locationBody, reportBody, dateBody, timeBody, userIdBody, petIdBody);
        loader.showLoader(this);
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

                                Messenger.showAlertDialog(ReportMissingActivity.this, "Success", message, "Ok").show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Messenger.showAlertDialog(ReportMissingActivity.this, "Error", "Failed to upload image", "Ok").show();
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
                        Messenger.showAlertDialog(ReportMissingActivity.this, "Error", t.getMessage(), "Ok").show();
                    }
                }, 1000);
            }
        });

        Log.d("Upload", image.toString());

    }

    private void setupDefaultValues() {
        if (pet != null) {
            tvPetName.setText(pet.getPetName());
            tvWeight.setText(pet.getWeight());
            tvBreed.setText(pet.getBreed());
            tvDob.setText(pet.getBirthdate());
            tvGender.setText(pet.getGender());

            Glide.with(this).load(ApiAddress.urlImage + pet.getImagePath()).into(ivPet);
        }
    }

    private byte[] imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(ivPet);
            }
        }
    }


}
