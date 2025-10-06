package com.rahul.spbcollegeduplicate.ClerkModule.GernalRegister;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class AddStudentGenralRegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    EditText et_general_register_no, et_student_id, et_studentUID, et_student_surname, et_student_name, et_student_father_name,
            et_student_mother_name, et_student_nationality, et_student_caste, et_student_sub_caste,et_student_religion, et_student_village,
            et_student_taluqa, et_student_district, et_student_state, et_student_country, et_studentDOB, et_student_DOB_in_latter,
            et_student_last_school, et_student_last_std, et_student_admission_date, et_student_new_STD, et_student_last_addd_date,
            et_student_progress_Study, et_student_progress;
    String spinner_mother_tung;
    ProgressDialog progressDialog;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_genral_register);

        setTitle("Add General Register");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding student data...");
        progressDialog.setCancelable(false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("GeneralRegister");

        et_general_register_no = findViewById(R.id.et__agr_admission_No);
        et_student_id = findViewById(R.id.et__agr_stdid);
        et_studentUID = findViewById(R.id.et__agr_stduid);
        et_student_surname = findViewById(R.id.et__agr_stdsername);
        et_student_name = findViewById(R.id.et__agr_stdname);
        et_student_father_name = findViewById(R.id.et__agr_stdparname);
        et_student_mother_name = findViewById(R.id.et__agr_stdmothname);
        et_student_nationality = findViewById(R.id.et__agr_stdnationality);
        et_student_caste = findViewById(R.id.et__agr_stdcast);
        et_student_sub_caste = findViewById(R.id.et__agr_stdsubcast);
        et_student_religion = findViewById(R.id.et__agr_stdreligion);
        et_student_village = findViewById(R.id.et__agr_stdvillage);
        et_student_taluqa = findViewById(R.id.et__agr_stdtaluqa);
        et_student_district = findViewById(R.id.et__agr_stddist);
        et_student_state = findViewById(R.id.et__agr_stdsate);
        et_student_country = findViewById(R.id.et__agr_stdcountry);
        et_studentDOB = findViewById(R.id.et__agr_stdbirhinno);
        et_student_DOB_in_latter = findViewById(R.id.et__agr_stdbirhinletter);
        et_student_new_STD = findViewById(R.id.et__agr_stdschoolstdn);
        et_student_admission_date = findViewById(R.id.et__agr_stdadmissiondate);
        et_student_last_std = findViewById(R.id.et__agr_stdsincestandard);
        et_student_last_addd_date = findViewById(R.id.et__agr_stdsincedate);
        et_student_progress_Study = findViewById(R.id.et__agr_stdprogress);
        et_student_progress = findViewById(R.id.et__agr_stdstudprogress);
        et_student_last_school = findViewById(R.id.et__agr_stdlastschoolname);

        submitButton = findViewById(R.id.btn__agr_stdsubmit);

        Spinner spinner = findViewById(R.id.sp__agr_mother_tung);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_mother_tung = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        et_student_admission_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_student_admission_date);
                }
            }
        });

        et_studentDOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_studentDOB);
                }
            }
        });

        et_student_last_addd_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_student_last_addd_date);
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_general_register_no.getText().toString().isEmpty()){
                    et_general_register_no.setError("Enter Admission No");
                } else if (et_student_id.getText().toString().isEmpty()) {
                    et_student_id.setError("Enter Student Id");
                } else if (et_studentUID.getText().toString().isEmpty() && et_studentUID.getText().toString().length()<12) {
                    et_studentUID.setError("Enter Proper UID");
                } else if (et_student_surname.getText().toString().isEmpty()) {
                    et_student_surname.setError("Enter Surname");
                } else if (et_student_name.getText().toString().isEmpty()) {
                    et_student_name.setError("Enter Name");
                } else if (et_student_father_name.getText().toString().isEmpty()) {
                    et_student_father_name.setError("Enter Father Name");
                } else if (et_student_mother_name.getText().toString().isEmpty()) {
                    et_student_mother_name.setError("Enter Mother Name");
                } else if (et_student_nationality.getText().toString().isEmpty()) {
                    et_student_nationality.setError("Enter Nationality");
                } else if (et_student_caste.getText().toString().isEmpty()) {
                    et_student_caste.setError("Enter Caste");
                } else if (et_student_sub_caste.getText().toString().isEmpty()) {
                    et_student_sub_caste.setError("Enter SubCaste");
                }else if (et_student_religion.getText().toString().isEmpty()) {
                    et_student_religion.setError("Enter Religion");
                } else if (et_student_village.getText().toString().isEmpty()) {
                    et_student_village.setError("Enter Village Name");
                } else if (et_student_taluqa.getText().toString().isEmpty()) {
                    et_student_taluqa.setError("Enter Taluqa");
                } else if (et_student_district.getText().toString().isEmpty()) {
                    et_student_district.setError("Enter District");
                } else if (et_student_state.getText().toString().isEmpty()) {
                    et_student_state.setError("Enter State");
                } else if (et_student_country.getText().toString().isEmpty()) {
                    et_student_country.setError("Enter Country");
                } else if (et_studentDOB.getText().toString().isEmpty()) {
                    et_studentDOB.setError("Enter DOB");
                } else if (et_student_DOB_in_latter.getText().toString().isEmpty()) {
                    et_student_DOB_in_latter.setError("Enter DOB In Latter");
                } else if (et_student_new_STD.getText().toString().isEmpty()) {
                    et_student_new_STD.setError("Enter STD");
                } else if (et_student_admission_date.getText().toString().isEmpty()) {
                    et_student_admission_date.setError("Enter Admission Date");
                } else if (et_student_last_std.getText().toString().isEmpty()) {
                    et_student_last_std.setError("Enter Last STD");
                } else if (et_student_last_addd_date.getText().toString().isEmpty()) {
                    et_student_last_addd_date.setError("Enter Last Admission Date");
                } else if (et_student_progress_Study.getText().toString().isEmpty()) {
                    et_student_progress_Study.setError("Enter Study Progress");
                } else if (et_student_progress.getText().toString().isEmpty()) {
                    et_student_progress.setError("Enter Student Progress");
                } else if (et_student_last_school.getText().toString().isEmpty()) {
                    et_student_last_school.setError("Enter Last School Name");
                }else {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudentGenralRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        builder.setMessage("Do you want to Add Data General Register?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkAndAddStudent();
                progressDialog.show();
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

    private void checkAndAddStudent() {
        final String generalRegisterNo = et_general_register_no.getText().toString().trim();
        final String uid = et_studentUID.getText().toString().trim();
        final String studentID = et_student_id.getText().toString().trim();

        // Check if the general register number already exists
        databaseReference.child(generalRegisterNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(AddStudentGenralRegisterActivity.this, "Duplicate entry: General register number already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the student ID already exists
                    databaseReference.orderByChild("student_id").equalTo(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(AddStudentGenralRegisterActivity.this, "Duplicate entry: Student ID already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Check if the UID already exists
                                databaseReference.orderByChild("studentUID").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(AddStudentGenralRegisterActivity.this, "Duplicate entry: UID already exists!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            addStudentToDatabase(generalRegisterNo, studentID, uid);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addStudentToDatabase(String generalRegisterNo,String student_id, String uid) {
        DatabaseReference studentRef = databaseReference.child(generalRegisterNo);
        studentRef.child("student_id").setValue(student_id);
        studentRef.child("student_general_register_no").setValue(generalRegisterNo);
        studentRef.child("studentUID").setValue(uid);
        studentRef.child("student_surname").setValue(et_student_surname.getText().toString().trim());
        studentRef.child("student_name").setValue(et_student_name.getText().toString().trim());
        studentRef.child("student_father_name").setValue(et_student_father_name.getText().toString().trim());
        studentRef.child("student_mother_name").setValue(et_student_mother_name.getText().toString().trim());
        studentRef.child("student_nationality").setValue(et_student_nationality.getText().toString().trim());
        studentRef.child("student_mother_tung").setValue(spinner_mother_tung);
        studentRef.child("student_caste").setValue(et_student_caste.getText().toString().trim());
        studentRef.child("student_sub_caste").setValue(et_student_sub_caste.getText().toString().trim());
        studentRef.child("student_religion").setValue(et_student_religion.getText().toString().trim());
        studentRef.child("student_village").setValue(et_student_village.getText().toString().trim());
        studentRef.child("student_taluqa").setValue(et_student_taluqa.getText().toString().trim());
        studentRef.child("student_district").setValue(et_student_district.getText().toString().trim());
        studentRef.child("student_state").setValue(et_student_state.getText().toString().trim());
        studentRef.child("student_country").setValue(et_student_country.getText().toString().trim());
        studentRef.child("studentDOB").setValue(et_studentDOB.getText().toString().trim());
        studentRef.child("student_DOB_in_latter").setValue(et_student_DOB_in_latter.getText().toString());
        studentRef.child("student_last_school").setValue(et_student_last_school.getText().toString());
        studentRef.child("student_last_std").setValue(et_student_last_std.getText().toString().trim());
        studentRef.child("student_admission_date").setValue(et_student_admission_date.getText().toString().trim());
        studentRef.child("student_new_STD").setValue(et_student_new_STD.getText().toString().trim());
        studentRef.child("student_last_addd_date").setValue(et_student_last_addd_date.getText().toString().trim());
        studentRef.child("student_progress_Study").setValue(et_student_progress_Study.getText().toString().trim());
        studentRef.child("student_progress").setValue(et_student_progress.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddStudentGenralRegisterActivity.this, "Student data added successfully!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        } else {
                            // Handle errors
                            Toast.makeText(AddStudentGenralRegisterActivity.this, "Failed to add student data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
