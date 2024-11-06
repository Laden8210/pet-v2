package com.example.epet.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epet.Model.Pet;
import com.example.epet.R;
import com.example.epet.api.ApiAddress;
import com.example.epet.view.PetInformationActivity;
import com.example.epet.view.ReportMissingActivity;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {

    private final Context context;
    private List<Pet> petList;

    public PetAdapter(List<Pet> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pet_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pet pet = petList.get(position);


        holder.tvPetName.setText(pet.getPetName());

        Glide.with(context).load(ApiAddress.urlImage + pet.getImagePath()).into(holder.ivPet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, new ArrayList<>(List.of("De", "Missing")));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.btnMenu);
            popup.getMenuInflater().inflate(R.menu.menu_pet_options, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                   if (item.getItemId() == R.id.menu_deceased) {
                       Intent intent = new Intent(context, PetInformationActivity.class);
                       intent.putExtra("pet", pet);
                       context.startActivity(intent);
                   }else if (item.getItemId() == R.id.menu_missing) {
                       Intent intent = new Intent(context, ReportMissingActivity.class);
                       intent.putExtra("pet", pet);
                       context.startActivity(intent);
                   }else if (item.getItemId() == R.id.menu_view) {
                       Intent intent = new Intent(context, PetInformationActivity.class);
                       intent.putExtra("pet", pet);
                       context.startActivity(intent);
                     }
                     return false;
                }
            });

            popup.show();
        });


    }

    @Override
    public int getItemCount() {

        return petList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // Define views
        private ImageView ivPet;
        private TextView tvPetName;
        private CardView cardView;
        Button btnMenu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            ivPet = itemView.findViewById(R.id.iv_pet_image);
            tvPetName = itemView.findViewById(R.id.tv_pet_name);
            btnMenu = itemView.findViewById(R.id.btn_menu);
        }
    }
}
