package com.example.epet.Model;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("image")
    private String image;

    public ImageResponse() {
    }

    public ImageResponse(String success, String image) {
        this.success = success;
        this.image = image;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
