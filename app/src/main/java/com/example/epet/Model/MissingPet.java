package com.example.epet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class MissingPet implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("pet_id")
    private int petId;

    @SerializedName("location")
    private String location;

    @SerializedName("report")
    private String report;

    @SerializedName("found_report")
    private String foundReport;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("missing_image_path")
    private String missingImagePath;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("pet_name")
    private String petName;

    @SerializedName("age")
    private int age;

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

    @SerializedName("pet_owner_id")
    private int petOwnerId;

    @SerializedName("pet_status")
    private String petStatus;

    @SerializedName("missing_status")
    private int missingStatus;

    protected MissingPet(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        petId = in.readInt();
        location = in.readString();
        report = in.readString();
        foundReport = in.readString();
        date = in.readString();
        time = in.readString();
        missingImagePath = in.readString();
        createdAt = in.readString();
        petName = in.readString();
        age = in.readInt();
        type = in.readString();
        breed = in.readString();
        color = in.readString();
        description = in.readString();
        birthdate = in.readString();
        weight = in.readString();
        height = in.readString();
        gender = in.readString();
        imagePath = in.readString();
        petOwnerId = in.readInt();
        petStatus = in.readString();
        missingStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(petId);
        dest.writeString(location);
        dest.writeString(report);
        dest.writeString(foundReport);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(missingImagePath);
        dest.writeString(createdAt);
        dest.writeString(petName);
        dest.writeInt(age);
        dest.writeString(type);
        dest.writeString(breed);
        dest.writeString(color);
        dest.writeString(description);
        dest.writeString(birthdate);
        dest.writeString(weight);
        dest.writeString(height);
        dest.writeString(gender);
        dest.writeString(imagePath);
        dest.writeInt(petOwnerId);
        dest.writeString(petStatus);
        dest.writeInt(missingStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MissingPet> CREATOR = new Creator<MissingPet>() {
        @Override
        public MissingPet createFromParcel(Parcel in) {
            return new MissingPet(in);
        }

        @Override
        public MissingPet[] newArray(int size) {
            return new MissingPet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getFoundReport() {
        return foundReport;
    }

    public void setFoundReport(String foundReport) {
        this.foundReport = foundReport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMissingImagePath() {
        return missingImagePath;
    }

    public void setMissingImagePath(String missingImagePath) {
        this.missingImagePath = missingImagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(int petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    public int getMissingStatus() {
        return missingStatus;
    }

    public void setMissingStatus(int missingStatus) {
        this.missingStatus = missingStatus;
    }
}
