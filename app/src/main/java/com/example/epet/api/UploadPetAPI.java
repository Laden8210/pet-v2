package com.example.epet.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.Call;

public interface UploadPetAPI {

    @Multipart
    @POST("upload-pet.php")
    Call<ResponseBody> uploadPet(@Part MultipartBody.Part image,
                                   @Part("pet_name") RequestBody petName,
                                   @Part("pet_age") RequestBody petAge,
                                   @Part("pet_birthdate") RequestBody petBirthdate,
                                   @Part("pet_color") RequestBody petColor,
                                   @Part("pet_type") RequestBody petType,
                                   @Part("gender") RequestBody petGender,
                                   @Part("pet_breed") RequestBody petBreed,
                                   @Part("pet_weight") RequestBody petWeight,
                                   @Part("pet_height") RequestBody petHeight,
                                   @Part("pet_description") RequestBody petDescription,
                                   @Part("user_id") RequestBody userId);



    @Multipart
    @POST("submit-report.php")
    Call<ResponseBody> submitReport(@Part MultipartBody.Part image,
                                    @Part("location") RequestBody location,
                                    @Part("report") RequestBody report,
                                    @Part("date") RequestBody date,
                                    @Part("time") RequestBody time,
                                    @Part("user_id") RequestBody userId,
                                    @Part("pet_id") RequestBody petId);
}
