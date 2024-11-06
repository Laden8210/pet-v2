package com.example.epet.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.epet.Model.MissingPet;
import com.example.epet.R;
import com.example.epet.api.PostTask;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Messenger;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class FoundReportActivity extends AppCompatActivity implements PostCallback {

    private TextView tvName, tvAge, tvType, tvBreed, tvColor, tvDescription, tvBirthdate;
    private TextView tvWeight, tvHeight, tvGender, tvLocation, tvReport, tvDate, tvTime;
    private Button btnFound;
    private TextInputLayout tilDescription;
    private MissingPet missingPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_report);

        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvType = findViewById(R.id.tv_type);
        tvBreed = findViewById(R.id.tv_breed);
        tvColor = findViewById(R.id.tv_color);
        tvDescription = findViewById(R.id.tv_description);
        tvBirthdate = findViewById(R.id.tv_birthdate);
        tvWeight = findViewById(R.id.tv_weight);
        tvHeight = findViewById(R.id.tv_height);
        tvGender = findViewById(R.id.tv_gender);
        tvLocation = findViewById(R.id.tv_location);
        tvReport = findViewById(R.id.tv_report);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);

        tilDescription = findViewById(R.id.til_description);

        btnFound = findViewById(R.id.btn_submit);
        btnFound.setOnClickListener(this::submitFoundReport);

        if (getIntent().hasExtra("missingPet")){
            missingPet = getIntent().getParcelableExtra("missingPet");

            tvName.setText(missingPet.getPetName());
            tvAge.setText(String.valueOf(missingPet.getAge()));
            tvType.setText(missingPet.getType());
            tvBreed.setText(missingPet.getBreed());
            tvColor.setText(missingPet.getColor());
            tvDescription.setText(missingPet.getDescription());
            tvBirthdate.setText(missingPet.getBirthdate());
            tvWeight.setText(missingPet.getWeight());
            tvHeight.setText(missingPet.getHeight());
            tvGender.setText(missingPet.getGender());
            tvLocation.setText(missingPet.getLocation());
            tvReport.setText(missingPet.getReport());
            tvDate.setText(missingPet.getDate());
            tvTime.setText(missingPet.getTime());

        }

    }

    private void submitFoundReport(View view) {
        String description = tilDescription.getEditText().getText().toString();
        if (description.isEmpty()){
            tilDescription.setError("Description is required");
            return;
        }

        try {
            JSONObject data = new JSONObject();
            data.put("MISSING_PET_ID", missingPet.getId());
            data.put("DESCRIPTION", description);

            new PostTask(this, this, "Error", "found-report.php").execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPostSuccess(String responseData) {
        MaterialAlertDialogBuilder builder = Messenger.showAlertDialog(this, "Success", "Thank you for submitting your found pet report. We have successfully recorded the details, and your found pet is now visible in our system. Kindly bring a lost pet to the San Roque Barangay Hall to assist in reuniting a pet with his/her owner. Thank you for your kindness in helping reunite pets with their families!", "Ok");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            startActivity(new Intent(this, HeroActivity.class));
        });

        builder.show();
    }

    @Override
    public void onPostError(String errorMessage) {
        Messenger.showAlertDialog(this, "Error", errorMessage, "Ok").show();
    }
}