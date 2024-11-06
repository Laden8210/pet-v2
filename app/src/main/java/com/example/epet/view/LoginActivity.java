package com.example.epet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.epet.Model.User;
import com.example.epet.R;
import com.example.epet.api.PostTask;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity implements PostCallback {

    private TextInputLayout usernameEmail;
    private TextInputLayout password;

    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        if(SessionManager.getInstance(this).getSession() != null){
            startActivity(new Intent(this, HeroActivity.class));
            finish();
        }
        register = findViewById(R.id.btn_register);

        register.setOnClickListener(this::registerAction);
        usernameEmail = findViewById(R.id.til_login_username_email);
        password = findViewById(R.id.til_login_password);

        login =findViewById(R.id.btn_login);

        login.setOnClickListener(this::loginAction);

    }

    private void loginAction(View view) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("USER_IDENTIFIER", usernameEmail.getEditText().getText().toString());
            jsonObject.put("PASSWORD", password.getEditText().getText().toString());

            new PostTask(this, this, "error", "login.php").execute(jsonObject);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void registerAction(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostSuccess(String responseData) {

        Gson gson = new Gson();

        Type userType = new TypeToken<User>() {}.getType();
        User user = gson.fromJson(responseData, userType);
        SessionManager.getInstance(this).clearSession();
        SessionManager.getInstance(this).createSession(user);
        startActivity(new Intent(this, HeroActivity.class));
    }

    @Override
    public void onPostError(String errorMessage) {

        Messenger.showAlertDialog(this, "Login Error", errorMessage, "Ok").show();
    }
}