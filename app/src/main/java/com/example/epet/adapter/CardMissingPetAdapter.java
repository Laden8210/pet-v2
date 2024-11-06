package com.example.epet.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epet.Model.MissingPet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.util.Messenger;
import com.example.epet.view.FoundReportActivity;

import java.util.List;

public class CardMissingPetAdapter extends RecyclerView.Adapter<CardMissingPetAdapter.MyViewHolder> {

    private Context context;
    private List<MissingPet> missingPetList;

    public CardMissingPetAdapter(Context context, List<MissingPet> missingPetList) {
        this.context = context;
        this.missingPetList = missingPetList;
    }

    @NonNull
    @Override
    public CardMissingPetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_missing_pet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardMissingPetAdapter.MyViewHolder holder, int position) {

        MissingPet missingPet = missingPetList.get(position);
        holder.tvPetName.setText(missingPet.getPetName());
        holder.tvDate.setText(missingPet.getDate());

        Glide.with(context)
                .load(ApiAddress.urlImage + missingPet.getMissingImagePath())
                .into(        holder.ivMissingPet);

        if (missingPet.getMissingStatus() == 0) {
            // Set background tint to red if missing
            holder.cvMissingPet.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            holder.cardView.setOnClickListener(e-> {
                Intent intent = new Intent(context, FoundReportActivity.class);
                intent.putExtra("missingPet", missingPet);
                context.startActivity(intent);
            });
        } else {

            holder.cvMissingPet.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

            holder.cardView.setOnClickListener(e-> {
                Messenger.showAlertDialog(context, "Pet Found", "Pet has been found", "OK").show();
            });
        }





    }

    @Override
    public int getItemCount() {
        return missingPetList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMissingPet;
        private TextView tvPetName, tvDate;
        private CardView cvMissingPet;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMissingPet = itemView.findViewById(R.id.ivPet);
            tvPetName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            cvMissingPet = itemView.findViewById(R.id.cardView2);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
