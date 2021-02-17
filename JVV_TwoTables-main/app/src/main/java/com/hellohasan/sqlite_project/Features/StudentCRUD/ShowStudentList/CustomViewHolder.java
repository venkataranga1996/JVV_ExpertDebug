package com.hellohasan.sqlite_project.Features.StudentCRUD.ShowStudentList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView  registrationNumTextView;
    TextView  diseaseTextView;
    TextView  villageTextView;
    TextView  contactTextView;
    TextView  tabletsTextView;
    TextView genderTextView;


    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        registrationNumTextView = itemView.findViewById(R.id.registrationNumTextView);
        diseaseTextView = itemView.findViewById(R.id.diseaseTextView);
        villageTextView = itemView.findViewById(R.id.villageTextView);
        contactTextView = itemView.findViewById(R.id.contactTextView);
        tabletsTextView = itemView.findViewById(R.id.tabletsTextView);
        genderTextView = itemView.findViewById(R.id.genderTextView);

        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
