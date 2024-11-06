package com.example.epet.calback;

public interface GetCallback {

    void onGetSuccess(String responseData);

    void onGetError(String errorMessage);
}
