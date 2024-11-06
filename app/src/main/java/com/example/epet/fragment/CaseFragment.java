package com.example.epet.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.epet.Model.Case;
import com.example.epet.Model.Pet;
import com.example.epet.Model.User;
import com.example.epet.R;
import com.example.epet.adapter.CaseAdapter;
import com.example.epet.adapter.PetAdapter;
import com.example.epet.api.PostTask;
import com.example.epet.calback.PostCallback;
import com.example.epet.util.SessionManager;
import com.example.epet.view.AddCaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CaseFragment extends Fragment implements PostCallback {

    private RecyclerView rvCase;
    private List<Case> caseList;
    private FloatingActionButton btnCase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_case, container, false);
        rvCase = view.findViewById(R.id.rv_case);

        btnCase = view.findViewById(R.id.btn_case);
        btnCase.setOnClickListener(this::createCaseAction);

        return view;
    }

    private void createCaseAction(View view) {
        getActivity().startActivity(new Intent(getContext(), AddCaseActivity.class));
    }

    @Override
    public void onPostSuccess(String responseData) {
        Log.d("data", responseData);
        Type productListType = new TypeToken<List<Case>>() {}.getType();
        Gson gson =new Gson();
        caseList = gson.fromJson(responseData, productListType);
        User user = SessionManager.getInstance(getContext()).getSession();
        List<Case> caseLists = new ArrayList<>();
        for (Case cases: caseLists){
            if(cases.getUserId() ==  user.getPetOwnerId()){
                caseLists.add(cases);
            }

        }
        CaseAdapter caseAdapter = new CaseAdapter(caseLists, getContext());
        rvCase.setAdapter(caseAdapter);
        rvCase.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPostError(String errorMessage) {

    }
}