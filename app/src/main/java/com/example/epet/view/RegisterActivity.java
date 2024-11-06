package com.example.epet.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.epet.Model.ImageResponse;
import com.example.epet.Model.User;
import com.example.epet.R;
import com.example.epet.adapter.GenderAdapter;
import com.example.epet.api.ApiAddress;
import com.example.epet.api.PostTask;
import com.example.epet.calback.ImageUploader;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements PostCallback, ImageUploader.UploadCallback {

    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout middleName;
    private TextInputLayout age;
    private TextInputLayout birthdate;
    private TextInputLayout email;
    private TextInputLayout phone;
    private TextInputLayout telNumber;
    private TextInputLayout houseNumber;
    private TextInputLayout zipCode;
    private TextInputLayout street;
    private TextInputLayout city;
    private TextInputLayout province;
    private TextInputLayout brgy;
    private TextInputLayout username;
    private TextInputLayout password;
    private RadioGroup rgGender;

    private ImageView ivPet;

    private Button btnRegister;
    private Button btnUploadImage;
    private FloatingActionButton fabDatePick;
    private String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        middleName = findViewById(R.id.middleName);
        age = findViewById(R.id.age);
        birthdate = findViewById(R.id.birthdate);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        telNumber = findViewById(R.id.tel);
        houseNumber = findViewById(R.id.houseNumber);
        zipCode = findViewById(R.id.zipCode);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        brgy = findViewById(R.id.brgy);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rgGender = findViewById(R.id.rg_gender);

        btnUploadImage = findViewById(R.id.brgyId);
        fabDatePick = findViewById(R.id.btn_birthdate);
        btnRegister = findViewById(R.id.btn_register);

        ivPet = findViewById(R.id.img_profile);
        
        btnUploadImage.setOnClickListener(action -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            uploadProof.launch(intent);
        });
        fabDatePick.setOnClickListener(this::pickDateAction);
        btnRegister.setOnClickListener(this::registerAction);
    }

    private void pickDateAction(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {


                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;

                birthdate.getEditText().setText(selectedDate);

                // get the age

                Calendar dob = Calendar.getInstance();
                dob.set(selectedYear, selectedMonth, selectedDay);
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                String ageS = Integer.toString(age);
                RegisterActivity.this.age.getEditText().setText(ageS);

            }
        }, year, month, day);
        datePickerDialog.show();
    }


    private void registerAction(View view) {
        String firstNameText = firstName.getEditText().getText().toString().trim();
        String lastNameText = lastName.getEditText().getText().toString().trim();
        String middleNameText = middleName.getEditText().getText().toString().trim();
        String ageText = age.getEditText().getText().toString().trim();
        String birthdateText = birthdate.getEditText().getText().toString().trim();
        String emailText = email.getEditText().getText().toString().trim();
        String phoneText = phone.getEditText().getText().toString().trim();
        String telNumberText = telNumber.getEditText().getText().toString().trim();
        String houseNumberText = houseNumber.getEditText().getText().toString().trim();
        String zipCodeText = zipCode.getEditText().getText().toString().trim();
        String streetText = street.getEditText().getText().toString().trim();
        String cityText = city.getEditText().getText().toString().trim();
        String provinceText = province.getEditText().getText().toString().trim();
        String brgyText = brgy.getEditText().getText().toString().trim();
        String usernameText = username.getEditText().getText().toString().trim();
        String passwordText = password.getEditText().getText().toString().trim();


        if (firstNameText.isEmpty() || lastNameText.isEmpty()  || ageText.isEmpty() || birthdateText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || telNumberText.isEmpty() || houseNumberText.isEmpty() || zipCodeText.isEmpty() || streetText.isEmpty() || cityText.isEmpty() || provinceText.isEmpty() || brgyText.isEmpty() || usernameText.isEmpty() || passwordText.isEmpty()) {
            Messenger.showAlertDialog(this, "Register Error", "Please fill up all fields.", "Ok").show();
            return;
        }

        if (image == null) {
            Messenger.showAlertDialog(this, "Register Error", "Please upload an image.", "Ok").show();
            return;
        }

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String genderText = selectedGenderId == R.id.rb_male ? "Male" : "Female";

        User newUser = new User(firstNameText, lastNameText, middleNameText, ageText, birthdateText,
                emailText, phoneText, telNumberText, houseNumberText, zipCodeText,
                streetText, cityText, provinceText, brgyText, usernameText, passwordText, genderText, image);


        Log.d("User", newUser.toJson().toString());

        try {

            new PostTask(this, this, "error", "register-user.php").execute(newUser.toJson());
        } catch (Exception e) {
            e.printStackTrace();
            Messenger.showAlertDialog(this, "Registration Error", "An error occurred during registration.", "Ok").show();
        }
    }



    ActivityResultLauncher<Intent> uploadProof = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Uri uri = result.getData().getData();

                    String filePath = getFilePathFromUri(uri);
                    ivPet.setImageURI(uri);

                    Bitmap bitmap = ((BitmapDrawable) ivPet.getDrawable()).getBitmap();

                    ImageUploader uploader = new ImageUploader(this, bitmap,this);
                    uploader.execute(ApiAddress.url+"upload-profile.php");

                    Log.d(filePath, filePath);

                }
            }
    );

    private String getFilePathFromUri(Uri uri) {
        String filePath = "";
        if (uri.getScheme().equals("file")) {
            filePath = uri.getPath();
        } else if (uri.getScheme().equals("content")) {

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
                cursor.close();
            }
        }
        return filePath;
    }



    @Override
    public void onPostSuccess(String responseData) {

        Gson gson = new Gson();

        // Parse the JSON response directly
        JsonObject jsonResponse = gson.fromJson(responseData, JsonObject.class);

        if (jsonResponse.has("success")) {
            String successMessage = jsonResponse.get("success").getAsString();
            Messenger.showAlertDialog(this, "Register Success", successMessage, "Ok", "Back to Login"
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        } else if (jsonResponse.has("error")) {
            String errorMessage = jsonResponse.get("error").getAsString();
            System.out.println("Error: " + errorMessage);
            // Show an error message to the user (e.g., using a Toast)
        }



    }

    @Override
    public void onPostError(String errorMessage) {
        Messenger.showAlertDialog(this, "Register Error", errorMessage, "Ok").show();
    }

    @Override
    public void onUploadComplete(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ImageResponse>(){}.getType();
        ImageResponse imageResponse = gson.fromJson(response, type);
        image = imageResponse.getImage();
        Messenger.showAlertDialog(this, "Upload Success", imageResponse.getSuccess(), "Ok").show();
    }
}