package com.example.epet.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.epet.R;
import com.example.epet.api.PostTask;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChangePasswordActivity extends AppCompatActivity implements PostCallback {

    private Button update;
    private TextInputLayout tfNPassword, tfCPassword, tfOPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        tfCPassword = findViewById(R.id.til_reg_cpassword);
        tfNPassword = findViewById(R.id.til_reg_npassword);
        tfOPassword = findViewById(R.id.til_reg_opassword);
        update = findViewById(R.id.btn_reg_register);

        update.setOnClickListener(this::updateAction);


    }

    private void updateAction(View view) {
        String postData = null;
        try {
            postData = "cpassword=" + URLEncoder.encode(tfCPassword.getEditText().getText().toString(), "UTF-8") +
                    "&npassword=" + URLEncoder.encode(tfNPassword.getEditText().getText().toString(), "UTF-8") +
                    "&opassword=" + URLEncoder.encode(tfOPassword.getEditText().getText().toString(), "UTF-8") +
                    "&user_id=" + URLEncoder.encode(String.valueOf(SessionManager.getInstance(this).getSession().getPetOwnerId()), "UTF-8");

            String errorMessage = "";



        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Messenger.showAlertDialog(this, "Update Error ", "Password Update Successfully", "OK").show();
        tfCPassword.getEditText().setText("");
        tfOPassword.getEditText().setText("");
        tfNPassword.getEditText().setText("");
    }

    @Override
    public void onPostError(String errorMessage) {
        Messenger.showAlertDialog(this, "Update Error ", errorMessage, "OK").show();
    }
}