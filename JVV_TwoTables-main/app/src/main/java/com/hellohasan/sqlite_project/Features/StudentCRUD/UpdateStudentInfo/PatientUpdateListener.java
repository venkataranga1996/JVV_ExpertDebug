package com.hellohasan.sqlite_project.Features.StudentCRUD.UpdateStudentInfo;

import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.Patient;

public interface PatientUpdateListener {
    void onStudentInfoUpdated(Patient patient, int position);
}
