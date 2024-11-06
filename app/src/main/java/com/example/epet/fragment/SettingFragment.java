package com.example.epet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.epet.R;
import com.example.epet.util.DarkModeHelper;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingFragment extends Fragment {

    private SwitchMaterial toggleDarkMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        toggleDarkMode = view.findViewById(R.id.toggle);
        toggleDarkMode.setChecked(DarkModeHelper.isDarkModeEnabled(requireContext()));

        toggleDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DarkModeHelper.setDarkModeEnabled(requireContext(), isChecked);
        });
        return view;
    }
}