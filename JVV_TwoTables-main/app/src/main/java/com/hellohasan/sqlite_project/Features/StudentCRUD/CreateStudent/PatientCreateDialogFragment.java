package com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Util.Config;
import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.R;


public class PatientCreateDialogFragment extends DialogFragment {

    private static PatientCreateListener patientCreateListener;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText diseaseEditText;
    private EditText villageEditText;
    private EditText  contactEditText;
    private EditText tabletsEditText;
    private EditText genderEditText;

    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private long registrationNumber = -1;
    private String diseaseString = "";
    private String villageString = "";
    private int contactString = 0; //Integer.parseInt("");
    private String tabletsString = "";
    private String genderString = "";

    public PatientCreateDialogFragment() {
        // Required empty public constructor
    }

    public static PatientCreateDialogFragment newInstance(String title, PatientCreateListener listener){
        patientCreateListener = listener;
        PatientCreateDialogFragment patientCreateDialogFragment = new PatientCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        patientCreateDialogFragment.setArguments(args);

        patientCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return patientCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.patientNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        diseaseEditText = view.findViewById(R.id.diseaseEditText);
        villageEditText = view.findViewById(R.id.villageEditText);
        contactEditText = view.findViewById(R.id.contactEditText);
        tabletsEditText = view.findViewById(R.id.tabletsEditText);
        genderEditText = view.findViewById(R.id.genderEditText);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                diseaseString = diseaseEditText.getText().toString();
                villageString = villageEditText.getText().toString();
              contactString = Integer.parseInt(contactEditText.getText().toString());
                tabletsString = tabletsEditText.getText().toString();
                genderString = genderEditText.getText().toString();

                Patient patient = new Patient(nameString, registrationNumber, diseaseString, villageString,contactString,tabletsString,genderString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertPatient(patient);

                if(id>0){
                    patient.setId(id);
                    patientCreateListener.onStudentCreated(patient);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
