package com.hellohasan.sqlite_project.Features.StudentCRUD.UpdateStudentInfo;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.Patient;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;


public class PatientUpdateDialogFragment extends DialogFragment {

    private static long patientRegNo;
    private static int patientItemPosition;
    private static PatientUpdateListener patientUpdateListener;

    private Patient mPatient;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText diseaseEditText;
    private EditText villageEditText;
    private EditText contactEditText;
    private EditText tabletsEditText;
    private EditText genderEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private long registrationNumber = -1;
    private String diseaseString = "";
    private String villageString = "";
    private int   contactNumber = Integer.parseInt("");
    private String tabletsString ="";
    private String genderString ="";


    private DatabaseQueryClass databaseQueryClass;

    public PatientUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static PatientUpdateDialogFragment newInstance(long registrationNumber, int position, PatientUpdateListener listener){
        patientRegNo = registrationNumber;
        patientItemPosition = position;
        patientUpdateListener = listener;
        PatientUpdateDialogFragment patientUpdateDialogFragment = new PatientUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update student information");
        patientUpdateDialogFragment.setArguments(args);

        patientUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return patientUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.patientNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        diseaseEditText = view.findViewById(R.id.diseaseEditText);
        villageEditText = view.findViewById(R.id.villageEditText);
        contactEditText = view.findViewById(R.id.contactEditText);
        tabletsEditText = view.findViewById(R.id.tabletsEditText);
        genderEditText = view.findViewById(R.id.genderEditText);
        updateButton = view.findViewById(R.id.updateStudentInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mPatient = databaseQueryClass.getStudentByRegNum(patientRegNo);

        if(mPatient !=null){
            nameEditText.setText(mPatient.getName());
            registrationEditText.setText(String.valueOf(mPatient.getRegistrationNumber()));
            diseaseEditText.setText(mPatient.getDisease());
            villageEditText.setText(mPatient.getVillage());
            contactEditText.setText(String.valueOf(mPatient.getContact()));
            tabletsEditText.setText(mPatient.getTablets());
            genderEditText.setText(mPatient.getGender());




            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                    diseaseString = diseaseEditText.getText().toString();
                    villageString = villageEditText.getText().toString();
                    contactNumber = Integer.parseInt(contactEditText.getText().toString());
                    tabletsString = tabletsEditText.getText().toString();
                   genderString = genderEditText.getText().toString();


                    mPatient.setName(nameString);
                    mPatient.setRegistrationNumber(registrationNumber);
                    mPatient.setDisease(diseaseString);
                    mPatient.setVillage(villageString);
                    mPatient.setContact(contactNumber);
                    mPatient.setTablets(tabletsString);
                    mPatient.setGender(genderString);



                    long id = databaseQueryClass.updatePatientInfo(mPatient);

                    if(id>0){
                        patientUpdateListener.onStudentInfoUpdated(mPatient, patientItemPosition);
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

        }

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
