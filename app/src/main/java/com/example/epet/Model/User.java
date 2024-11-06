package com.example.epet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable {

    @SerializedName("pet_owner_id")
    private int petOwnerId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;

    @SerializedName("phone")
    private String phone;

    @SerializedName("tel_number")
    private String telNumber;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("zip_code")
    private String zipCode;

    @SerializedName("street")
    private String street;

    @SerializedName("city")
    private String city;

    @SerializedName("province")
    private String province;

    @SerializedName("brgy")
    private String brgy;

    @SerializedName("image")
    private String image;

    @SerializedName("is_activate")
    private int isActivate;

    @SerializedName("password")
    private String password;

    public User(int petOwnerId, String firstName, String lastName, String middleName, String age,
                String birthdate, String email, String phone, String telNumber, String houseNumber,
                String zipCode, String street, String city, String province, String brgy,
                String userName, String gender, String image, int isActivate) {
        this.petOwnerId = petOwnerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.telNumber = telNumber;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.province = province;
        this.brgy = brgy;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.isActivate = isActivate;
    }

    public User(String firstName, String lastName, String middleName, String age, String birthdate,
                String email, String phone, String telNumber, String houseNumber, String zipCode,
                String street, String city, String province, String brgy, String userName,
                String password, String gender, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.telNumber = telNumber;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.province = province;
        this.brgy = brgy;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.image = image;
    }
    protected User(Parcel in) {
        petOwnerId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        birthdate = in.readString();
        userName = in.readString();
        email = in.readString();
        gender = in.readString();
        age = in.readString();
        phone = in.readString();
        telNumber = in.readString();
        houseNumber = in.readString();
        zipCode = in.readString();
        street = in.readString();
        city = in.readString();
        province = in.readString();
        brgy = in.readString();
        image = in.readString();
        isActivate = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(petOwnerId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(middleName);
        dest.writeString(birthdate);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(age);
        dest.writeString(phone);
        dest.writeString(telNumber);
        dest.writeString(houseNumber);
        dest.writeString(zipCode);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(brgy);
        dest.writeString(image);
        dest.writeInt(isActivate);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters and setters
    public int getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(int petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBrgy() {
        return brgy;
    }

    public void setBrgy(String brgy) {
        this.brgy = brgy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(int isActivate) {
        this.isActivate = isActivate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJson() {
        try {
            String jsonString = new Gson().toJson(this);
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
