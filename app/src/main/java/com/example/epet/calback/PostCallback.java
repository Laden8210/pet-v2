package com.example.epet.calback;

public interface PostCallback {
    void onPostSuccess(String responseData);

    void onPostError(String errorMessage);
}
