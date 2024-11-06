package com.example.epet.util;

import com.google.android.material.textfield.TextInputLayout;

public class Validition {

    public static boolean validateEmpty(TextInputLayout ... fields){
        for (TextInputLayout field: fields) {
           if(field.getEditText().getText().toString().isEmpty()){
               return true;
           }
        }
        return false;
    }
}
