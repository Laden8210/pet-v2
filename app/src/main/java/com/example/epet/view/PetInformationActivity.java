package com.example.epet.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PetInformationActivity extends AppCompatActivity {

    private TextView tvPetName, tvWeight, tvHeight, tvBreed, tvDob, tvGender;
    private ImageView ivPet, ivQrCode;
    private Button btnEdit, btnQrCodeDownload;
    private Pet pet;

    private static final int PERMISSION_REQUEST_CODE = 100;
    private String petName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_information);

        // Initialize views
        tvPetName = findViewById(R.id.tv_pet_name);
        tvWeight = findViewById(R.id.tv_weight);
        tvHeight = findViewById(R.id.tv_height); // Ensure this is initialized
        tvBreed = findViewById(R.id.tv_breed);
        tvDob = findViewById(R.id.tv_dob);
        tvGender = findViewById(R.id.tv_gender);
        ivPet = findViewById(R.id.iv_pet);
        ivQrCode = findViewById(R.id.iv_pet_qr);
        btnEdit = findViewById(R.id.btn_edit);
        btnQrCodeDownload = findViewById(R.id.btn_qr);

        btnQrCodeDownload.setOnClickListener(this::downloadQrAction);
        btnEdit.setOnClickListener(this::editAction);

        pet = getIntent().getParcelableExtra("pet");


        if (pet != null) {
            if (pet.getImagePath() != null) {
                String url = (ApiAddress.urlImage + pet.getImagePath()).replace(" ", "");
                Glide.with(ivPet.getContext()).load(url).into(ivPet);
            } else {
                ivPet.setImageResource(R.drawable.logo);
            }


            tvPetName.setText(pet.getPetName());
            tvWeight.setText(String.valueOf(pet.getWeight()));
            tvHeight.setText(String.valueOf(pet.getHeight()));
            tvBreed.setText(pet.getBreed());
            tvDob.setText(pet.getBirthdate());
            tvGender.setText(pet.getGender());
            petName = pet.getPetName();

            generateQrCode(pet);
        }
    }

    private void editAction(View view) {
        Intent intent = new Intent(this, AddPetActivity.class);
        intent.putExtra("pet", pet);
        startActivity(intent);
    }

    private void downloadQrAction(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            saveQrCodeToGallery();
        }
    }

    private void generateQrCode(Pet pet) {
        Gson gson = new Gson();
        String petJson = gson.toJson(pet);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(petJson, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ivQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveQrCodeToGallery() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivQrCode.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Pet QR Code");
        values.put(MediaStore.Images.Media.DESCRIPTION, "QR Code for Pet Information");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");


            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Pet");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            values.put(MediaStore.Images.Media.DATA, directory.getPath() + "/"+petName+".png");


        OutputStream outStream;
        try {
            outStream = getContentResolver().openOutputStream(
                    getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            if (outStream != null) {
                outStream.flush();
                outStream.close();
                Toast.makeText(this, "QR Code saved to Documents", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
                saveQrCodeToGallery();

        }
    }
}
