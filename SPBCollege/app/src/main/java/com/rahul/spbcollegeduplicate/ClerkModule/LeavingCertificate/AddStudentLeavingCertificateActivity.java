package com.rahul.spbcollegeduplicate.ClerkModule.LeavingCertificate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

import java.util.Calendar;

public class AddStudentLeavingCertificateActivity extends AppCompatActivity {

    EditText et_stud_gen_reg_no,et_stud_UID_no,et_stud_full_name,et_stud_mother_name,et_stud_nationality,et_stud_religion,
    et_stud_caste,et_stud_subcaste,et_stud_village,et_stud_taluqa,et_stud_dist,et_stud_state,et_stud_DOB,et_stud_DOB_in_letter,
    et_stud_last_school_name,et_stud_last_STD,et_stud_add_date,et_stud_add_STD,et_stud_progress_in_Study,et_stud_behavior,et_stud_leave_date,
    et_stud_current_STD,et_stud_long_study,et_stud_leave_reason,et_stud_grade;
    Button btn_submit;
    String spinner_mother_tung;
    ImageButton ibtn_fetch_from_data;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_leaving_certificate);
        setTitle("Issue Leaving Certificate");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Leaving_Certificate_Record");


        et_stud_gen_reg_no = findViewById(R.id.et__aS_leaving_GenatalRegisterNO);
        et_stud_UID_no = findViewById(R.id.et__aS_leaving_adharNO);
        et_stud_full_name = findViewById(R.id.et__aS_leaving_Sname);
        et_stud_mother_name = findViewById(R.id.et__aS_leaving_Mname);
        et_stud_nationality = findViewById(R.id.et__aS_leaving_Natioality);
        et_stud_caste = findViewById(R.id.et__aS_leaving_cast);
        et_stud_subcaste = findViewById(R.id.et__aS_leaving_subcast);
        et_stud_religion = findViewById(R.id.et__aS_leaving_religion);
        et_stud_village = findViewById(R.id.et__aS_leaving_birthvillage);
        et_stud_taluqa = findViewById(R.id.et__aS_leaving_birthtaluqa);
        et_stud_dist = findViewById(R.id.et__aS_leaving_birthdist);
        et_stud_state = findViewById(R.id.et__aS_leaving_birthstate);
        et_stud_DOB = findViewById(R.id.et__aS_leaving_birthinno);
        et_stud_DOB_in_letter = findViewById(R.id.et__aS_leaving_birthinletter);
        et_stud_last_school_name = findViewById(R.id.et__aS_leaving_lastschool);
        et_stud_last_STD = findViewById(R.id.et__aS_leaving_laststd);
        et_stud_add_date = findViewById(R.id.et__aS_leaving_admissiondate);
        et_stud_add_STD = findViewById(R.id.et__aS_leaving_whichstd);
        et_stud_progress_in_Study = findViewById(R.id.et__aS_leaving_progress_in_study);
        et_stud_behavior = findViewById(R.id.et__aS_leaving_behaviour);
        et_stud_leave_date = findViewById(R.id.et__aS_leaving_progress_leavedate);
        et_stud_current_STD = findViewById(R.id.et__aS_leaving_currentstd);
        et_stud_long_study = findViewById(R.id.et__aS_leaving_longclass);
        et_stud_leave_reason = findViewById(R.id.et__aS_leaving_leaveReason);
        et_stud_grade = findViewById(R.id.et__aS_leaving_Grade);
        btn_submit = findViewById(R.id.btn__aS_leave_submit);
        ibtn_fetch_from_data = findViewById(R.id.btn_fetch_from_gen_reg);
        Spinner spinner = findViewById(R.id.sp__aS_leaving_mothertung);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_mother_tung = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        et_stud_leave_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_stud_leave_date);
                }
            }
        });

        et_stud_DOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_stud_DOB);
                }
            }
        });

        et_stud_add_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_stud_add_date);
                }
            }
        });




        ibtn_fetch_from_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String GenRegNo = et_stud_gen_reg_no.getText().toString();

                DatabaseReference studReference = FirebaseDatabase.getInstance().getReference("GeneralRegister").child(GenRegNo);
                progressDialog.setMessage("Fetching Data...");
                progressDialog.show();
                    studReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if (snapshot.exists()) {
                                String studGenRegNo = snapshot.child("student_general_register_no").getValue(String.class);
                                String studUID = snapshot.child("studentUID").getValue(String.class);
                                String studSurname = snapshot.child("student_surname").getValue(String.class);
                                String studName = snapshot.child("student_name").getValue(String.class);
                                String studFatherName = snapshot.child("student_father_name").getValue(String.class);
                                String StudMotherName = snapshot.child("student_mother_name").getValue(String.class);
                                String StudNationality = snapshot.child("student_nationality").getValue(String.class);
                                String StudReligion = snapshot.child("student_religion").getValue(String.class);
                                String StudCaste = snapshot.child("student_caste").getValue(String.class);
                                String StudSubCaste = snapshot.child("student_sub_caste").getValue(String.class);
                                String StudVillage = snapshot.child("student_village").getValue(String.class);
                                String StudTaluqa = snapshot.child("student_taluqa").getValue(String.class);
                                String StudDist = snapshot.child("student_district").getValue(String.class);
                                String StudState = snapshot.child("student_state").getValue(String.class);
                                String StudDOBInNO = snapshot.child("studentDOB").getValue(String.class);
                                String StudDOBInLatter = snapshot.child("student_DOB_in_latter").getValue(String.class);
                                String StudLastSchool = snapshot.child("student_last_school").getValue(String.class);
                                String StudLastSTD = snapshot.child("student_last_std").getValue(String.class);
                                String StudCurrentSTD = snapshot.child("student_new_STD").getValue(String.class);
                                String StudCurrentAddDate = snapshot.child("student_admission_date").getValue(String.class);

                                et_stud_gen_reg_no.setText(studGenRegNo);
                                et_stud_UID_no.setText(studUID);
                                et_stud_full_name.setText(studSurname+" "+studName+" "+studFatherName);
                                et_stud_mother_name.setText(StudMotherName+" "+studFatherName+" "+studSurname);
                                et_stud_nationality.setText(StudNationality);
                                et_stud_religion.setText(StudReligion);
                                et_stud_caste.setText(StudCaste);
                                et_stud_subcaste.setText(StudSubCaste);
                                et_stud_village.setText(StudVillage);
                                et_stud_taluqa.setText(StudTaluqa);
                                et_stud_dist.setText(StudDist);
                                et_stud_state.setText(StudState);
                                et_stud_DOB.setText(StudDOBInNO);
                                et_stud_DOB_in_letter.setText(StudDOBInLatter);
                                et_stud_last_school_name.setText(StudLastSchool);
                                et_stud_last_STD.setText(StudLastSTD);
                                et_stud_add_date.setText(StudCurrentAddDate);
                                et_stud_add_STD.setText(StudCurrentSTD);
                            } else {
                                Toast.makeText(AddStudentLeavingCertificateActivity.this, "Student Data Not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddStudentLeavingCertificateActivity.this, "Error While Fetching data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        });




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_stud_gen_reg_no.getText().toString().isEmpty()){
                    et_stud_gen_reg_no.setError("Enter General Register No");
                } else if (et_stud_UID_no.getText().toString().isEmpty() && et_stud_UID_no.getText().toString().length()<12) {
                    et_stud_UID_no.setError("Enter Proper UID");
                } else if (et_stud_full_name.getText().toString().isEmpty()) {
                    et_stud_full_name.setError("Enter Student Full Name");
                } else if (et_stud_mother_name.getText().toString().isEmpty()) {
                    et_stud_mother_name.setError("Enter Student's Mother Name");
                } else if (et_stud_nationality.getText().toString().isEmpty()) {
                    et_stud_nationality.setError("Enter Nationality");
                } else if (et_stud_caste.getText().toString().isEmpty()) {
                    et_stud_caste.setError("Enter Caste");
                } else if (et_stud_subcaste.getText().toString().isEmpty()) {
                    et_stud_subcaste.setError("Enter SubCaste");
                }else if (et_stud_religion.getText().toString().isEmpty()) {
                    et_stud_religion.setError("Enter Religion");
                } else if (et_stud_village.getText().toString().isEmpty()) {
                    et_stud_village.setError("Enter Village Name");
                } else if (et_stud_taluqa.getText().toString().isEmpty()) {
                    et_stud_taluqa.setError("Enter Taluqa");
                } else if (et_stud_dist.getText().toString().isEmpty()) {
                    et_stud_dist.setError("Enter District");
                } else if (et_stud_state.getText().toString().isEmpty()) {
                    et_stud_state.setError("Enter State");
                } else if (et_stud_DOB.getText().toString().isEmpty()) {
                    et_stud_DOB.setError("Enter DOB");
                } else if (et_stud_DOB_in_letter.getText().toString().isEmpty()) {
                    et_stud_DOB_in_letter.setError("Enter DOB In Latter");
                } else if (et_stud_last_school_name.getText().toString().isEmpty()) {
                    et_stud_last_school_name.setError("Enter Last School Name");
                } else if (et_stud_last_STD.getText().toString().isEmpty()) {
                    et_stud_last_STD.setError("Enter Last STD");
                } else if (et_stud_add_date.getText().toString().isEmpty()) {
                    et_stud_add_date.setError("Enter Admission Date");
                } else if (et_stud_add_STD.getText().toString().isEmpty()) {
                    et_stud_add_STD.setError("Enter Admitted STD");
                } else if (et_stud_progress_in_Study.getText().toString().isEmpty()) {
                    et_stud_progress_in_Study.setError("Enter Study Progress");
                } else if (et_stud_behavior.getText().toString().isEmpty()) {
                    et_stud_behavior.setError("Enter Student Behavior");
                } else if (et_stud_leave_date.getText().toString().isEmpty()) {
                    et_stud_leave_date.setError("Enter Leave Date");
                }else if (et_stud_current_STD.getText().toString().isEmpty()) {
                    et_stud_current_STD.setError("Enter Current STD");
                } else if (et_stud_long_study.getText().toString().isEmpty()) {
                    et_stud_long_study.setError("Enter Studying From Year");
                } else if (et_stud_leave_reason.getText().toString().isEmpty()) {
                    et_stud_leave_reason.setError("Enter Reason Of Leaving");
                } else if (et_stud_grade.getText().toString().isEmpty()) {
                    et_stud_grade.setError("Enter Grade");
                } else {
                    showConfirmationDialog();
                }
            }
        });
    }

    private void showDatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudentLeavingCertificateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to submit Leaving Certificate?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkAndIssueLeavingCertificate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void checkAndIssueLeavingCertificate() {
        final String generalRegisterNo = et_stud_gen_reg_no.getText().toString().trim();

        // Check if the general register number already exists
        databaseReference.child(generalRegisterNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(AddStudentLeavingCertificateActivity.this, "Leaving Certificate Is Already Issued ", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Issuing Leaving Certificate...");
                    progressDialog.show();
                    issueLeavingCertificate(generalRegisterNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(AddStudentLeavingCertificateActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void issueLeavingCertificate(String generalRegisterNo) {
            DatabaseReference studentRef = databaseReference.child(generalRegisterNo);
            studentRef.child("student_general_register_no").setValue(generalRegisterNo);
            studentRef.child("studentUID").setValue(et_stud_UID_no.getText().toString().trim());
            studentRef.child("student_full_name").setValue(et_stud_full_name.getText().toString());
            studentRef.child("student_mother_full_name").setValue(et_stud_mother_name.getText().toString().trim());
            studentRef.child("student_nationality").setValue(et_stud_nationality.getText().toString().trim());
            studentRef.child("student_mother_tung").setValue(spinner_mother_tung);
            studentRef.child("student_caste").setValue(et_stud_caste.getText().toString().trim());
            studentRef.child("student_sub_caste").setValue(et_stud_subcaste.getText().toString().trim());
            studentRef.child("student_religion").setValue(et_stud_religion.getText().toString().trim());
            studentRef.child("student_village").setValue(et_stud_village.getText().toString().trim());
            studentRef.child("student_taluqa").setValue(et_stud_taluqa.getText().toString().trim());
            studentRef.child("student_district").setValue(et_stud_dist.getText().toString().trim());
            studentRef.child("student_state").setValue(et_stud_state.getText().toString().trim());
            studentRef.child("studentDOB").setValue(et_stud_DOB.getText().toString().trim());
            studentRef.child("student_DOB_in_latter").setValue(et_stud_DOB_in_letter.getText().toString());
            studentRef.child("student_last_school").setValue(et_stud_last_school_name.getText().toString());
            studentRef.child("student_last_std").setValue(et_stud_last_STD.getText().toString().trim());
            studentRef.child("student_admission_date").setValue(et_stud_add_date.getText().toString().trim());
            studentRef.child("student_add_STD").setValue(et_stud_add_STD.getText().toString().trim());
            studentRef.child("student_progress_Study").setValue(et_stud_progress_in_Study.getText().toString().trim());
            studentRef.child("student_behavior").setValue(et_stud_behavior.getText().toString().trim());
            studentRef.child("student_leave_date").setValue(et_stud_leave_date.getText().toString().trim());
            studentRef.child("student_current_std").setValue(et_stud_current_STD.getText().toString().trim());
            studentRef.child("student_studying_long_year").setValue(et_stud_long_study.getText().toString().trim());
            studentRef.child("student_leave_reason").setValue(et_stud_leave_reason.getText().toString().trim());
            studentRef.child("student_grade").setValue(et_stud_grade.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(AddStudentLeavingCertificateActivity.this, "Leaving Certificate Issued successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddStudentLeavingCertificateActivity.this, LeavingCertificateActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle errors
                                Toast.makeText(AddStudentLeavingCertificateActivity.this, "Failed to Issue Leaving Certificate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}