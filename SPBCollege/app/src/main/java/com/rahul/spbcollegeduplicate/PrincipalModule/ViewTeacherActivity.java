package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

public class ViewTeacherActivity extends AppCompatActivity {
    EditText et_teacher_full_name, et_teacher_id, et_teacher_mail, et_teacher_mobile_no, et_teacher_aadhar_no, et_teacher_pan_no, et_teacher_username;
    Button btn_update_teacher_profile, btn_delete_teacher_profile;
    String teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);
        setTitle("View teacher");

        et_teacher_full_name = findViewById(R.id.view_teacher_teacher_name);
        et_teacher_id = findViewById(R.id.view_teacher_teacher_id);
        et_teacher_mail = findViewById(R.id.view_teacher_teacher_email_id);
        et_teacher_mobile_no = findViewById(R.id.view_teacher_teacher_mobile_no);
        et_teacher_aadhar_no = findViewById(R.id.view_teacher_teacher_aadhar_no);
        et_teacher_pan_no = findViewById(R.id.view_teacher_teacher_pan_no);
        et_teacher_username = findViewById(R.id.view_teacher_teacher_username);
        btn_update_teacher_profile = findViewById(R.id.view_teacher_update_teacher_btn);
        btn_delete_teacher_profile = findViewById(R.id.view_teacher_delete_teacher);

        et_teacher_full_name.setEnabled(false);
        et_teacher_id.setEnabled(false);
        et_teacher_mobile_no.setEnabled(false);
        et_teacher_mail.setEnabled(false);
        et_teacher_aadhar_no.setEnabled(false);
        et_teacher_pan_no.setEnabled(false);
        et_teacher_username.setEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            teacherId = intent.getStringExtra("teacherId");
            retrieveteacherData(teacherId);
        }

        btn_update_teacher_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateteacherProfile(view);
            }
        });

        btn_delete_teacher_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTeacherProfile(view);
            }
        });
    }

    private void retrieveteacherData(String teacherId) {
        DatabaseReference teacherReference = FirebaseDatabase.getInstance().getReference("teacher's_Profile").child(teacherId);

        teacherReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String teacherFullName = snapshot.child("teacherFullName").getValue(String.class);
                    String teacherId = snapshot.child("teacherId").getValue(String.class);
                    String teacherEmail = snapshot.child("teacherEMailId").getValue(String.class);
                    String teacherMobileNo = snapshot.child("teacherMobileNo").getValue(String.class);
                    String teacherAadharNo = snapshot.child("teacherAadharNo").getValue(String.class);
                    String teacherPanNo = snapshot.child("teacherPanNo").getValue(String.class);
                    String teacherUsername = snapshot.child("teacherUsername").getValue(String.class);

                    et_teacher_full_name.setText(teacherFullName);
                    et_teacher_id.setText(teacherId);
                    et_teacher_mail.setText(teacherEmail);
                    et_teacher_mobile_no.setText(teacherMobileNo);
                    et_teacher_aadhar_no.setText(teacherAadharNo);
                    et_teacher_pan_no.setText(teacherPanNo);
                    et_teacher_username.setText(teacherUsername);
                } else {
                    Toast.makeText(ViewTeacherActivity.this, "teacher Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewTeacherActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateteacherProfile(View view) {
        // Enable EditText fields for editing
        et_teacher_full_name.setEnabled(true);
        et_teacher_id.setEnabled(true);
        et_teacher_mail.setEnabled(true);
        et_teacher_mobile_no.setEnabled(true);
        et_teacher_aadhar_no.setEnabled(true);
        et_teacher_pan_no.setEnabled(true);
        et_teacher_username.setEnabled(true);

        // Change the button text to "Save Changes"
        btn_update_teacher_profile.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        btn_update_teacher_profile.setOnClickListener(v -> {
            saveChangesToFirebase();
        });
    }

    private void saveChangesToFirebase() {
        DatabaseReference teacherReference = FirebaseDatabase.getInstance().getReference("teacher's_Profile").child(teacherId);

        // Retrieve updated data from EditText fields
        String updatedteacherFullName = et_teacher_full_name.getText().toString();
        String updatedteacherId = et_teacher_id.getText().toString();
        String updatedteacherEmail = et_teacher_mail.getText().toString();
        String updatedteacherMobileNo = et_teacher_mobile_no.getText().toString();
        String updatedteacherAadharNo = et_teacher_aadhar_no.getText().toString();
        String updatedteacherPanNo = et_teacher_pan_no.getText().toString();
        String updatedteacherUsername = et_teacher_username.getText().toString();

        // Update the teacher data in the Firebase Realtime Database
        teacherReference.child("teacherFullName").setValue(updatedteacherFullName);
        teacherReference.child("teacherId").setValue(updatedteacherId);
        teacherReference.child("teacherEMailId").setValue(updatedteacherEmail);
        teacherReference.child("teacherMobileNo").setValue(updatedteacherMobileNo);
        teacherReference.child("teacherAadharNo").setValue(updatedteacherAadharNo);
        teacherReference.child("teacherPanNo").setValue(updatedteacherPanNo);
        teacherReference.child("teacherUsername").setValue(updatedteacherUsername);

        // Notify the user that changes have been saved
        Toast.makeText(ViewTeacherActivity.this, "teacher data updated successfully", Toast.LENGTH_SHORT).show();

        // Disable EditText fields for editing
        et_teacher_full_name.setEnabled(false);
        et_teacher_id.setEnabled(false);
        et_teacher_mail.setEnabled(false);
        et_teacher_mobile_no.setEnabled(false);
        et_teacher_aadhar_no.setEnabled(false);
        et_teacher_pan_no.setEnabled(false);
        et_teacher_username.setEnabled(false);

        // Change the button text back to "Update"
        btn_update_teacher_profile.setText("Update");

        // Set the original click listener for the "Update" button
        btn_update_teacher_profile.setOnClickListener(v -> {
            updateteacherProfile(v);
        });
    }

    public void deleteTeacherProfile(View view) {
        DatabaseReference teacherReference = FirebaseDatabase.getInstance().getReference("teacher's_Profile").child(teacherId);

        teacherReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ViewTeacherActivity.this, "Teacher profile deleted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after deletion
            } else {
                Toast.makeText(ViewTeacherActivity.this, "Failed to delete teacher profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
