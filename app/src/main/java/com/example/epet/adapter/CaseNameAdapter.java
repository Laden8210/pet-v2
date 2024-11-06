package com.example.epet.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epet.Model.Case;
import com.example.epet.R;
import com.example.epet.util.Messenger;
import com.example.epet.view.AddCaseActivity;

import java.util.List;

public class CaseNameAdapter extends RecyclerView.Adapter<CaseNameAdapter.MyViewHolder> {

    private List<Case> caseList;
    private Context context;


    public CaseNameAdapter(List<Case> caseList, Context context) {
        this.caseList = caseList;
        this.context = context;
    }

    @NonNull
    @Override
    public CaseNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_case, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseNameAdapter.MyViewHolder holder, int position) {
        Case dogCase = caseList.get(position);
        holder.description.setText(dogCase.getDescription());
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);

        }
    }
}
