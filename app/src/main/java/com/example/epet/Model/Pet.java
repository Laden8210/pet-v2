package com.example.epet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Pet implements Parcelable {

    @SerializedName("id")
    private int petId;

    @SerializedName("pet_owner_id")
    private int ownerId;

    @SerializedName("pet_name")
    private String petName;

    @SerializedName("age")
    private int petAge;

    @SerializedName("type")
    private String type;

    @SerializedName("breed")
    private String breed;

    @SerializedName("color")
    private String color;

    @SerializedName("description")
    private String description;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("weight")
    private String weight;

    @SerializedName("height")
    private String height;

    @SerializedName("gender")
    private String gender;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("created_at")

    private String createdAt;

    @SerializedName("pet_status")
    private String petStatus;

    protected Pet(Parcel in) {
        petId = in.readInt();
        ownerId = in.readInt();
        petName = in.readString();
        petAge = in.readInt();
        type = in.readString();
        breed = in.readString();
        color = in.readString();
        description = in.readString();
        birthdate = in.readString();
        weight = in.readString();
        height = in.readString();
        gender = in.readString();
        imagePath = in.readString();
        createdAt = in.readString();
        petStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(petId);
        dest.writeInt(ownerId);
        dest.writeString(petName);
        dest.writeInt(petAge);
        dest.writeString(type);
        dest.writeString(breed);
        dest.writeString(color);
        dest.writeString(description);
        dest.writeString(birthdate);
        dest.writeString(weight);
        dest.writeString(height);
        dest.writeString(gender);
        dest.writeString(imagePath);
        dest.writeString(createdAt);
        dest.writeString(petStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }
}
