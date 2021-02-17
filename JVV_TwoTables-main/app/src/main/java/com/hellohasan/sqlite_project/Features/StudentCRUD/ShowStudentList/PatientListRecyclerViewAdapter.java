package com.hellohasan.sqlite_project.Features.StudentCRUD.ShowStudentList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.Patient;
import com.hellohasan.sqlite_project.Features.StudentCRUD.UpdateStudentInfo.PatientUpdateDialogFragment;
import com.hellohasan.sqlite_project.Features.StudentCRUD.UpdateStudentInfo.PatientUpdateListener;

import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class PatientListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Patient> patientList;
    private DatabaseQueryClass databaseQueryClass;

    public PatientListRecyclerViewAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Patient patient = patientList.get(position);

        holder.nameTextView.setText(patient.getName());
        holder.registrationNumTextView.setText(String.valueOf(patient.getRegistrationNumber()));
        holder.diseaseTextView.setText(patient.getDisease());
        holder.villageTextView.setText(patient.getVillage());
        holder.contactTextView.setText(""+patient.getContact());
        holder.tabletsTextView.setText(patient.getTablets());
        holder.genderTextView.setText(patient.getGender());



        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this student?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deleteStudent(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientUpdateDialogFragment patientUpdateDialogFragment = PatientUpdateDialogFragment.newInstance(patient.getRegistrationNumber(), itemPosition, new PatientUpdateListener() {
                    @Override
                    public void onStudentInfoUpdated(Patient student, int position) {
                        patientList.set(position, student);
                        notifyDataSetChanged();
                    }
                });
                patientUpdateDialogFragment.show(((PatientListActivity) context).getSupportFragmentManager(), Config.UPDATE_PATIENT);
            }
        });


        // * if problem prestists delete this

    }

    private void deleteStudent(int position) {
        Patient patient = patientList.get(position);
        long count = databaseQueryClass.deleteStudentByRegNum(patient.getRegistrationNumber());

        if(count>0){
            patientList.remove(position);
            notifyDataSetChanged();
            ((PatientListActivity) context).viewVisibility();
            Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Student not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}
