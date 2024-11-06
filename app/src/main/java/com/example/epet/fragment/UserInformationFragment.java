package com.example.epet.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.epet.Model.User;
import com.example.epet.R;
import com.example.epet.util.SessionManager;
import com.example.epet.view.ChangePasswordActivity;
import com.example.epet.view.RegisterActivity;

public class UserInformationFragment extends Fragment {


    private TextView fullname, birthdate, gender, address, username, email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_information, container, false);
        fullname = view.findViewById(R.id.fullname);
        birthdate = view.findViewById(R.id.birthdate);
        gender = view.findViewById(R.id.gender);
        address = view.findViewById(R.id.address);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        gender = view.findViewById(R.id.gender);

//
//        User user = SessionManager.getInstance(getContext()).getSession();
//
//         fullname.setText(user.getFirstName() + " " + user.getMiddleName().charAt(0) + ". "  + user.getLastName());
//         birthdate.setText(user.getBirthdate());
//         address.setText(user.getAddress());
//         username.setText(user.getUserName());
//         email.setText(user.getEmail());
//         gender.setText(user.getGender());

        return view;
    }

    private void editAction(View view) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        intent.putExtra("user", SessionManager.getInstance(getContext()).getSession());
        startActivity(intent);
    }
}