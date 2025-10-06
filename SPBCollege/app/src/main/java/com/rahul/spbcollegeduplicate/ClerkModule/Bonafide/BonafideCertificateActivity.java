package com.rahul.spbcollegeduplicate.ClerkModule.Bonafide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BonafideCertificateActivity extends AppCompatActivity {
    ImageView bonafideCertificateStudImg;
    EditText et_bonafide_certificate_stud_name,et_bonafide_certificate_stud_dob,et_bonafide_certificate_stud_dob_letter,
            et_bonafide_certificate_stud_learning_year,et_bonafide_certificate_stud_learning_standard,et_bonafide_certificate_stud_cast,et_bonafide_certificate_stud_birth_place;
    Button btn_submit_bonafide;
    DatabaseReference reference;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_bonafide_certificate);

        setTitle("Generate Bonafide Certificate");

        Calendar calendar=Calendar.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final  int month=calendar.get(Calendar.MONTH);
        final  int day=calendar.get(Calendar.DAY_OF_MONTH);

       // bonafideCertificateStudImg = findViewById(R.id.add_bonafide_certificate_stud_image);
        et_bonafide_certificate_stud_name = findViewById(R.id.et_add_bonafide_certificate_stud_name);
        et_bonafide_certificate_stud_dob = findViewById(R.id.et_add_bonafide_certificate_stud_dob);
        et_bonafide_certificate_stud_dob_letter = findViewById(R.id.et_add_bonafide_certificate_stud_dob_letter);
        et_bonafide_certificate_stud_learning_year = findViewById(R.id.et_add_bonafide_certificate_stud_current_study_year);
        et_bonafide_certificate_stud_learning_standard = findViewById(R.id.et_add_bonafide_certificate_stud_current_standard);
        et_bonafide_certificate_stud_cast = findViewById(R.id.et_add_bonafide_certificate_stud_cast);
        et_bonafide_certificate_stud_birth_place = findViewById(R.id.et_add_bonafide_certificate_stud_birth_place);
        btn_submit_bonafide = findViewById(R.id.btn_submit_bonafide);


        et_bonafide_certificate_stud_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(BonafideCertificateActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            et_bonafide_certificate_stud_dob.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

        btn_submit_bonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_bonafide_certificate_stud_name.getText().toString().isEmpty()){
                    et_bonafide_certificate_stud_name.setError("Please Enter Student Full Name");
                } else if (et_bonafide_certificate_stud_dob.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_dob.setError("Please Enter Student DOB");
                } else if (et_bonafide_certificate_stud_dob_letter.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_dob_letter.setError("Please Enter Student DOB In Latter");
                } else if (et_bonafide_certificate_stud_learning_year.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_learning_year.setError("Please Enter Student Educational Year");
                } else if (et_bonafide_certificate_stud_learning_standard.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_learning_standard.setError("Please Enter Student Standard");
                } else if (et_bonafide_certificate_stud_cast.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_cast.setError("Please Enter Student Caste");
                } else if (et_bonafide_certificate_stud_birth_place.getText().toString().isEmpty()) {
                    et_bonafide_certificate_stud_birth_place.setError("Please Enter Student Birth Place");
                } else {

                    showConfirmationDialog();

                }
            }
        });

    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BonafideCertificateActivity.this, "Submission confirmed", Toast.LENGTH_SHORT).show();
                addDataToBonafideRecord();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BonafideCertificateActivity.this, "Submission cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void addDataToBonafideRecord() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Student's_Bonafide_Record");

        final String studentNameForBonafide = et_bonafide_certificate_stud_name.getText().toString();
        final String studentLearningYearForBonafide = et_bonafide_certificate_stud_learning_year.getText().toString();

        // Check if a student with the same name and learning year already exists in the database
        reference.orderByChild("studentName").equalTo(studentNameForBonafide).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean bonafideExists = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Check if a student with the same name and learning year exists
                    String existingLearningYear = snapshot.child("studentLearningYear").getValue(String.class);

                    if (existingLearningYear != null && existingLearningYear.equals(studentLearningYearForBonafide)) {
                        bonafideExists = true;
                        break;
                    }
                }

                if (bonafideExists) {
                    // Student with the same name and learning year already exists, show a toast
                    Toast.makeText(BonafideCertificateActivity.this, "Bonafide for this student with the same learning year already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Student with the same name and learning year doesn't exist, proceed to add the record
                    String studentDOBForBonafide = et_bonafide_certificate_stud_dob.getText().toString();
                    String studentDOBInLetterForBonafide = et_bonafide_certificate_stud_dob_letter.getText().toString();
                    String studentLearningStandardForBonafide = et_bonafide_certificate_stud_learning_standard.getText().toString();
                    String studentCasteForBonafide = et_bonafide_certificate_stud_cast.getText().toString();
                    String studentBirthPlaceForBonafide = et_bonafide_certificate_stud_birth_place.getText().toString();

                    Map<String, Object> bonafideData = new HashMap<>();
                    bonafideData.put("studentName", studentNameForBonafide);
                    bonafideData.put("studentDOB", studentDOBForBonafide);
                    bonafideData.put("studentDOBInLetter", studentDOBInLetterForBonafide);
                    bonafideData.put("studentLearningYear", studentLearningYearForBonafide);
                    bonafideData.put("studentLearningStandard", studentLearningStandardForBonafide);
                    bonafideData.put("studentCaste", studentCasteForBonafide);
                    bonafideData.put("studentBirthPlace", studentBirthPlaceForBonafide);

                    reference.child(studentNameForBonafide + "_" + studentLearningYearForBonafide).setValue(bonafideData);

                    Toast.makeText(BonafideCertificateActivity.this, "Record Stored Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BonafideCertificateActivity.this, RecentlyGeneratedBonafideActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if any
                Toast.makeText(BonafideCertificateActivity.this, "Error checking for existing student: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}