package com.example.epet.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epet.Model.Case;
import com.example.epet.R;
import com.example.epet.util.Messenger;
import com.example.epet.view.AddCaseActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.MyViewHolder> {

    private List<Case> caseList;
    private Context context;


    public CaseAdapter(List<Case> caseList, Context context) {
        this.caseList = caseList;
        this.context = context;
    }

    @NonNull
    @Override
    public CaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_case_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseAdapter.MyViewHolder holder, int position) {
        Case dogCase = caseList.get(position);
        holder.caseNumber.setText(dogCase.getCaseNumber());
        holder.dogName.setText(dogCase.getDogName());
        holder.description.setText(dogCase.getDescription());
        holder.date.setText(dogCase.getDate());

        holder.cvCase.setOnClickListener(e ->{
            Messenger.showAlertDialog(context, "Edit Case", "Do you want to edit this case?", "Yes", "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, AddCaseActivity.class);
                    intent.putExtra("case", dogCase);
                    context.startActivity(intent);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        });

    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView caseNumber, dogName, description, date;
        private CardView cvCase;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            caseNumber = itemView.findViewById(R.id.textView7);
            dogName = itemView.findViewById(R.id.dog_name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.textView11);
            cvCase = itemView.findViewById(R.id.cv_case);
        }
    }
}
