package com.example.epet.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.view.PetInformationActivity;

import java.util.List;

public class PetNameAdapter extends RecyclerView.Adapter<PetNameAdapter.MyViewHolder> {

    private final Context context;
    private List<Pet> petList;

    public PetNameAdapter(List<Pet> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pet pet = petList.get(position);
        holder.tvPetName.setText(pet.getPetName());
        holder.cardView.setOnClickListener(e ->{
            Intent intent = new Intent(context, PetInformationActivity.class);
            intent.putExtra("pet", pet);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return petList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPetName;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvPetName = itemView.findViewById(R.id.tv_name);
        }
    }
}
