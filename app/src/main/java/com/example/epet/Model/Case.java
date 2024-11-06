package com.example.epet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Case implements Parcelable {

    @SerializedName("cae_id")
    private int caseId;
    @SerializedName("case_number")
    private String caseNumber;
    @SerializedName("dog_id")
    private int dogId;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private String date;
    @SerializedName("user_id")
    private int userId;

    @SerializedName("petName")
    private String dogName;

    protected Case(Parcel in) {
        caseId = in.readInt();
        caseNumber = in.readString();
        dogId = in.readInt();
        description = in.readString();
        date = in.readString();
        userId = in.readInt();
        dogName = in.readString();
    }

    public static final Creator<Case> CREATOR = new Creator<Case>() {
        @Override
        public Case createFromParcel(Parcel in) {
            return new Case(in);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[size];
        }
    };

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(caseId);
        dest.writeString(caseNumber);
        dest.writeInt(dogId);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeInt(userId);
        dest.writeString(dogName);
    }
}
