package com.rahul.spbcollegeduplicate.ClerkModule.GernalRegister;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class ViewStudentGeneralRegActivity extends AppCompatActivity {
    EditText et_general_register_no, et_student_id, et_studentUID, et_student_surname, et_student_name, et_student_father_name,
            et_student_mother_name, et_student_nationality,et_student_mother_tung, et_student_caste,et_student_religion, et_student_sub_caste, et_student_village,
            et_student_taluqa, et_student_district, et_student_state, et_student_country, et_studentDOB, et_student_DOB_in_latter,
            et_student_last_school, et_student_last_std, et_student_admission_date, et_student_new_STD, et_student_last_addd_date,
            et_student_progress_Study, et_student_progress;
    DatePickerDialog.OnDateSetListener setListener;
    Button updateButton,deleteButton;
    String selectedGeneralRegisterNo;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_general_reg);

        progressDialog = new ProgressDialog(this);

        setTitle("View General Register");

        et_general_register_no = findViewById(R.id.et__acvsgr_admissionno);
        et_student_id = findViewById(R.id.et__acvsgr_studentid);
        et_studentUID = findViewById(R.id.et__acvsgr_studentuid);
        et_student_surname = findViewById(R.id.et__acvsgr_studentsurname);
        et_student_name = findViewById(R.id.et__acvsgr_studentname);
        et_student_father_name = findViewById(R.id.et__acvsgr_studentparentname);
        et_student_mother_name = findViewById(R.id.et__acvsgr_studentmothername);
        et_student_nationality = findViewById(R.id.et__acvsgr_studentnationality);
        et_student_mother_tung = findViewById(R.id.et__acvsgr_studentmothertung);
        et_student_caste = findViewById(R.id.et__acvsgr_studentcast);
        et_student_sub_caste = findViewById(R.id.et__acvsgr_studentsubcast);
        et_student_religion = findViewById(R.id.et__acvsgr_studentreligion);
        et_student_village = findViewById(R.id.et__acvsgr_studentbirthvill);
        et_student_taluqa = findViewById(R.id.et__acvsgr_studentbirthtal);
        et_student_district = findViewById(R.id.et__acvsgr_studentbirthdist);
        et_student_state = findViewById(R.id.et__acvsgr_studentbirthstate);
        et_student_country = findViewById(R.id.et__acvsgr_studentbirthcoun);
        et_studentDOB = findViewById(R.id.et__acvsgr_studentbirthinno);
        et_student_DOB_in_latter = findViewById(R.id.et__acvsgr_studentbirthinletter);
        et_student_new_STD = findViewById(R.id.et__acvsgr_studentcurrentstd);
        et_student_admission_date = findViewById(R.id.et__acvsgr_studentcurrentdate);
        et_student_last_std = findViewById(R.id.et__acvsgr_studentlastschoolstd);
        et_student_last_addd_date = findViewById(R.id.et__acvsgr_studentadmissiondate);
        et_student_progress_Study = findViewById(R.id.et__acvsgr_studentprogressinstudy);
        et_student_progress = findViewById(R.id.et__acvsgr_studentprogress);
        et_student_last_school = findViewById(R.id.et__acvsgr_studentlastschoolname);

        updateButton = findViewById(R.id.view_genral_update_genaral_btn);
        deleteButton = findViewById(R.id.view_genral_delete_genaral_btn);




        et_general_register_no.setEnabled(false);
        et_student_id.setEnabled(false);
        et_studentUID.setEnabled(false);
        et_student_name.setEnabled(false);
        et_student_father_name.setEnabled(false);
        et_student_surname.setEnabled(false);
        et_student_mother_name.setEnabled(false);
        et_student_mother_tung.setEnabled(false);
        et_student_nationality.setEnabled(false);
        et_student_religion.setEnabled(false);
        et_student_caste.setEnabled(false);
        et_student_sub_caste.setEnabled(false);
        et_student_village.setEnabled(false);
        et_student_taluqa.setEnabled(false);
        et_student_district.setEnabled(false);
        et_student_state.setEnabled(false);
        et_student_country.setEnabled(false);
        et_studentDOB.setEnabled(false);
        et_student_DOB_in_latter.setEnabled(false);
        et_student_last_school.setEnabled(false);
        et_student_last_addd_date.setEnabled(false);
        et_student_last_std.setEnabled(false);
        et_student_new_STD.setEnabled(false);
        et_student_admission_date.setEnabled(false);
        et_student_progress.setEnabled(false);
        et_student_progress_Study.setEnabled(false);




        Intent intent = getIntent();
        if (intent != null) {
            selectedGeneralRegisterNo = intent.getStringExtra("GENERAL_REGISTER_NO");
            retrieveGeneralRegisterData(selectedGeneralRegisterNo);
        }

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


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialogUpdate(view);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialogDelete();
            }
        });
    }

    private void showConfirmationDialogUpdate(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Update General Register Data ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateGeneralRegisterData(view);
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
    private void showConfirmationDialogDelete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete General Register Data ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGeneralRegisterData();
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

    private void showDatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ViewStudentGeneralRegActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void retrieveGeneralRegisterData(String studGeneralRegisterNo) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("GeneralRegister").child(studGeneralRegisterNo);

        clerkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String studGenRegNo = snapshot.child("student_general_register_no").getValue(String.class);
                    String studID = snapshot.child("student_id").getValue(String.class);
                    String studUID = snapshot.child("studentUID").getValue(String.class);
                    String studSurname = snapshot.child("student_surname").getValue(String.class);
                    String studName = snapshot.child("student_name").getValue(String.class);
                    String studFatherName = snapshot.child("student_father_name").getValue(String.class);
                    String StudMotherName = snapshot.child("student_mother_name").getValue(String.class);
                    String StudNationality = snapshot.child("student_nationality").getValue(String.class);
                    String StudMotherTung = snapshot.child("student_mother_tung").getValue(String.class);
                    String StudReligion = snapshot.child("student_religion").getValue(String.class);
                    String StudCaste = snapshot.child("student_caste").getValue(String.class);
                    String StudSubCaste = snapshot.child("student_sub_caste").getValue(String.class);
                    String StudVillage = snapshot.child("student_village").getValue(String.class);
                    String StudTaluqa = snapshot.child("student_taluqa").getValue(String.class);
                    String StudDist = snapshot.child("student_district").getValue(String.class);
                    String StudState = snapshot.child("student_state").getValue(String.class);
                    String StudCountry = snapshot.child("student_country").getValue(String.class);
                    String StudDOBInNO = snapshot.child("studentDOB").getValue(String.class);
                    String StudDOBInLatter = snapshot.child("student_DOB_in_latter").getValue(String.class);
                    String StudLastSchool = snapshot.child("student_last_school").getValue(String.class);
                    String StudLastSTD = snapshot.child("student_last_std").getValue(String.class);
                    String StudLastAddDate = snapshot.child("student_last_addd_date").getValue(String.class);
                    String StudCurrentSTD = snapshot.child("student_new_STD").getValue(String.class);
                    String StudCurrentAddDate = snapshot.child("student_admission_date").getValue(String.class);
                    String StudProgressInStudy = snapshot.child("student_progress_Study").getValue(String.class);
                    String StudProgress = snapshot.child("student_progress").getValue(String.class);

                    et_general_register_no.setText(studGenRegNo);
                    et_student_id.setText(studID);
                    et_studentUID.setText(studUID);
                    et_student_surname.setText(studSurname);
                    et_student_name.setText(studName);
                    et_student_father_name.setText(studFatherName);
                    et_student_mother_name.setText(StudMotherName);
                    et_student_nationality.setText(StudNationality);
                    et_student_mother_tung.setText(StudMotherTung);
                    et_student_religion.setText(StudReligion);
                    et_student_caste.setText(StudCaste);
                    et_student_sub_caste.setText(StudSubCaste);
                    et_student_village.setText(StudVillage);
                    et_student_taluqa.setText(StudTaluqa);
                    et_student_district.setText(StudDist);
                    et_student_state.setText(StudState);
                    et_student_country.setText(StudCountry);
                    et_studentDOB.setText(StudDOBInNO);
                    et_student_DOB_in_latter.setText(StudDOBInLatter);
                    et_student_last_school.setText(StudLastSchool);
                    et_student_last_std.setText(StudLastSTD);
                    et_student_last_addd_date.setText(StudLastAddDate);
                    et_student_new_STD.setText(StudCurrentSTD);
                    et_student_admission_date.setText(StudCurrentAddDate);
                    et_student_progress.setText(StudProgress);
                    et_student_progress_Study.setText(StudProgressInStudy);
                } else {
                    Toast.makeText(ViewStudentGeneralRegActivity.this, "Clerk Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewStudentGeneralRegActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateGeneralRegisterData(View view) {
        // Enable EditText fields for editing
        et_general_register_no.setEnabled(true);
        et_student_id.setEnabled(true);
        et_studentUID.setEnabled(true);
        et_student_name.setEnabled(true);
        et_student_father_name.setEnabled(true);
        et_student_surname.setEnabled(true);
        et_student_mother_name.setEnabled(true);
        et_student_mother_tung.setEnabled(true);
        et_student_nationality.setEnabled(true);
        et_student_religion.setEnabled(true);
        et_student_caste.setEnabled(true);
        et_student_sub_caste.setEnabled(true);
        et_student_village.setEnabled(true);
        et_student_taluqa.setEnabled(true);
        et_student_district.setEnabled(true);
        et_student_state.setEnabled(true);
        et_student_country.setEnabled(true);
        et_studentDOB.setEnabled(true);
        et_student_DOB_in_latter.setEnabled(true);
        et_student_last_school.setEnabled(true);
        et_student_last_addd_date.setEnabled(true);
        et_student_last_std.setEnabled(true);
        et_student_new_STD.setEnabled(true);
        et_student_admission_date.setEnabled(true);
        et_student_progress.setEnabled(true);
        et_student_progress_Study.setEnabled(true);

        // Change the button text to "Save Changes"
        updateButton.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        updateButton.setOnClickListener(v -> {
            showConfirmationDialog();

        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to save the changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveChangesToFirebase();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.show();
    }
    private void saveChangesToFirebase() {
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("GeneralRegister").child(selectedGeneralRegisterNo);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating Data");
        progressDialog.show();
        // Retrieve updated data from EditText fields
        String updatedStudGenRegNo = et_general_register_no.getText().toString().trim();
        String updatedStudID = et_student_id.getText().toString().trim();
        String updatedStudUID = et_studentUID.getText().toString().trim();
        String updatedStudSurname = et_student_surname.getText().toString();
        String updatedStudName = et_student_name.getText().toString();
        String updatedStudFatherName = et_student_father_name.getText().toString();
        String updatedStudMotherName =et_student_mother_name.getText().toString();
        String updatedStudNationality =et_student_nationality.getText().toString();
        String updatedStudMotherTung =et_student_mother_tung.getText().toString();
        String updatedStudReligion =et_student_religion.getText().toString();
        String updatedStudCaste =et_student_caste.getText().toString();
        String updatedStudSubCaste =et_student_sub_caste.getText().toString();
        String updatedStudVillage =et_student_village.getText().toString();
        String updatedStudTaluqa =et_student_taluqa.getText().toString();
        String updatedStudDist =et_student_district.getText().toString();
        String updatedStudState =et_student_state.getText().toString();
        String updatedStudCountry =et_student_country.getText().toString();
        String updatedStudDOBInNO =et_studentDOB.getText().toString();
        String updatedStudDOBInLatter =et_student_DOB_in_latter.getText().toString();
        String updatedStudLastSchool =et_student_last_school.getText().toString();
        String updatedStudLastSTD = et_student_last_std.getText().toString();
        String updatedStudLastAddDate =et_student_last_addd_date.getText().toString();
        String updatedStudCurrentSTD =et_student_new_STD.getText().toString();
        String updatedStudCurrentAddDate =et_student_admission_date.getText().toString();
        String updatedStudProgressInStudy =et_student_progress_Study.getText().toString();
        String updatedStudProgress =et_student_progress.getText().toString();



        // Update the clerk data in the Firebase Realtime Database
        // Update the clerk data in the Firebase Realtime Database
        studentRef.child("student_id").setValue(updatedStudID);
        studentRef.child("student_general_register_no").setValue(updatedStudGenRegNo);
        studentRef.child("studentUID").setValue(updatedStudUID);
        studentRef.child("student_surname").setValue(updatedStudSurname);
        studentRef.child("student_name").setValue(updatedStudName); // Add the updated value for student_name
        studentRef.child("student_father_name").setValue(updatedStudFatherName);
        studentRef.child("student_mother_name").setValue(updatedStudMotherName);
        studentRef.child("student_nationality").setValue(updatedStudNationality);
        studentRef.child("student_mother_tung").setValue(updatedStudMotherTung); // Set the selected spinner value here
        studentRef.child("student_caste").setValue(updatedStudCaste);
        studentRef.child("student_sub_caste").setValue(updatedStudSubCaste);
        studentRef.child("student_religion").setValue(updatedStudReligion);
        studentRef.child("student_village").setValue(updatedStudVillage);
        studentRef.child("student_taluqa").setValue(updatedStudTaluqa);
        studentRef.child("student_district").setValue(updatedStudDist);
        studentRef.child("student_state").setValue(updatedStudState);
        studentRef.child("student_country").setValue(updatedStudCountry);
        studentRef.child("studentDOB").setValue(updatedStudDOBInNO);
        studentRef.child("student_DOB_in_latter").setValue(updatedStudDOBInLatter);
        studentRef.child("student_last_school").setValue(updatedStudLastSchool);
        studentRef.child("student_last_std").setValue(updatedStudLastSTD);
        studentRef.child("student_admission_date").setValue(updatedStudCurrentAddDate);
        studentRef.child("student_new_STD").setValue(updatedStudCurrentSTD);
        studentRef.child("student_last_addd_date").setValue(updatedStudLastAddDate);
        studentRef.child("student_progress_Study").setValue(updatedStudProgressInStudy);
        studentRef.child("student_progress").setValue(updatedStudProgress);

        progressDialog.dismiss();
        Toast.makeText(ViewStudentGeneralRegActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

        et_general_register_no.setEnabled(false);
        et_student_id.setEnabled(false);
        et_studentUID.setEnabled(false);
        et_student_name.setEnabled(false);
        et_student_father_name.setEnabled(false);
        et_student_surname.setEnabled(false);
        et_student_mother_name.setEnabled(false);
        et_student_mother_tung.setEnabled(false);
        et_student_nationality.setEnabled(false);
        et_student_religion.setEnabled(false);
        et_student_caste.setEnabled(false);
        et_student_sub_caste.setEnabled(false);
        et_student_village.setEnabled(false);
        et_student_taluqa.setEnabled(false);
        et_student_district.setEnabled(false);
        et_student_state.setEnabled(false);
        et_student_country.setEnabled(false);
        et_studentDOB.setEnabled(false);
        et_student_DOB_in_latter.setEnabled(false);
        et_student_last_school.setEnabled(false);
        et_student_last_addd_date.setEnabled(false);
        et_student_last_std.setEnabled(false);
        et_student_new_STD.setEnabled(false);
        et_student_admission_date.setEnabled(false);
        et_student_progress.setEnabled(false);
        et_student_progress_Study.setEnabled(false);

        updateButton.setText("Update");

        // Set the original click listener for the "Update" button
        updateButton.setOnClickListener(v -> {
            updateGeneralRegisterData(v);
        });
    }

    private void deleteGeneralRegisterData() {

        DatabaseReference studReference = FirebaseDatabase.getInstance().getReference("GeneralRegister").child(selectedGeneralRegisterNo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting Data");
        progressDialog.show();
        studReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(ViewStudentGeneralRegActivity.this,GeneralRegisterActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                    Toast.makeText(ViewStudentGeneralRegActivity.this, "Data Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewStudentGeneralRegActivity.this, "Failed to delete Data", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}
