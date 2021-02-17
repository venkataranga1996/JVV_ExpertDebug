package com.hellohasan.sqlite_project.Features.StudentCRUD.ShowStudentList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.Patient;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.PatientCreateDialogFragment;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.PatientCreateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity implements PatientCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Patient> patientList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView studentListEmptyTextView;
    private RecyclerView recyclerView;
    private PatientListRecyclerViewAdapter patientListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = findViewById(R.id.recyclerView);
        summaryTextView = findViewById(R.id.summaryTextView);
        studentListEmptyTextView = findViewById(R.id.emptyListTextView);

        patientList.addAll(databaseQueryClass.getAllStudent());

        patientListRecyclerViewAdapter = new PatientListRecyclerViewAdapter(this, patientList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(patientListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentCreateDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        printSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all students?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllStudents();
                            if(isAllDeleted){
                                patientList.clear();
                                patientListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(patientList.isEmpty())
            studentListEmptyTextView.setVisibility(View.VISIBLE);
        else
            studentListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    private void openStudentCreateDialog() {
        PatientCreateDialogFragment patientCreateDialogFragment = PatientCreateDialogFragment.newInstance("Create Student", this);
        patientCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PATIENT);
    }

    @SuppressLint("StringFormatMatches")
    private void printSummary() {
        long studentNum = databaseQueryClass.getNumberOfPatient();


        summaryTextView.setText(getResources().getString(R.string.database_summary, studentNum));
    }

    @Override
    public void onStudentCreated(Patient patient) {
        patientList.add(patient);
        patientListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(patient.getName());
    }

}
