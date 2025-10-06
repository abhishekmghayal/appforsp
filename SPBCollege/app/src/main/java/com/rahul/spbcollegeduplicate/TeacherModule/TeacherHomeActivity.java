package com.rahul.spbcollegeduplicate.TeacherModule;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;
import com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.Teacher_student_Result_class_Activity;

public class TeacherHomeActivity extends AppCompatActivity {

    CardView card_teacher_stud_attandance,card_teacher_student_result,allCardBackground;
    TextView tv_teacher_name;
    Button btn_update_teacher_profile;
    EditText et_teacher_full_name, et_teacher_id, et_teacher_mail, et_teacher_mobile_no, et_teacher_aadhar_no, et_teacher_pan_no, et_teacher_username;
    ImageView iv_resize_card, teacherImg;
    String teacherUsername,teacherId;
    private boolean isCardDown = false;
    private long lastClickTime = 0;
    FloatingActionButton flb_leavereq,flb_previewreq;

    Boolean aBoolean=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        Intent intent = getIntent();
        if (intent != null) {
            teacherUsername = intent.getStringExtra("teacherLoginUsername");
            retrieveTeacherData(teacherUsername);
        }

            card_teacher_stud_attandance=findViewById(R.id.card_student_attandance);
            card_teacher_student_result=findViewById(R.id.card_student_result);
            allCardBackground = findViewById(R.id.all_card_background);
            iv_resize_card = findViewById(R.id.card_resize);


            teacherImg = findViewById(R.id.teacher_img);
            tv_teacher_name = findViewById(R.id.tv_teacher_name);
            et_teacher_id = findViewById(R.id.profile_teacher_teacher_id);
            et_teacher_full_name = findViewById(R.id.profile_teacher_teacher_name);
            et_teacher_mobile_no = findViewById(R.id.profile_teacher_teacher_mobile_no);
            et_teacher_mail = findViewById(R.id.profile_teacher_teacher_email_id);
            et_teacher_aadhar_no = findViewById(R.id.profile_teacher_teacher_aadhar_no);
            et_teacher_pan_no = findViewById(R.id.profile_teacher_teacher_pan_no);
            et_teacher_username = findViewById(R.id.profile_teacher_teacher_username);
            btn_update_teacher_profile = findViewById(R.id.profile_teacher_update_teacher_btn);


            et_teacher_full_name.setEnabled(false);
            et_teacher_id.setEnabled(false);
            et_teacher_mobile_no.setEnabled(false);
            et_teacher_mail.setEnabled(false);
            et_teacher_aadhar_no.setEnabled(false);
            et_teacher_pan_no.setEnabled(false);
            et_teacher_username.setEnabled(false);

            btn_update_teacher_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateTeacherProfile(view);
                }
            });

            card_teacher_student_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(TeacherHomeActivity.this, Teacher_student_Result_class_Activity.class);
                    startActivity(i);
                    finish();
                }
            });
            card_teacher_stud_attandance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            iv_resize_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isCardDown) {
                        animateCardViewDown(allCardBackground);
                        isCardDown = true;
                    } else {
                        animateCardViewUp(allCardBackground);
                        isCardDown = false;
                    }
                }
            });
    }

    private void retrieveTeacherData(String teacherUsername) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("teacher's_Profile");
        Query query = reference.orderByChild("teacherUsername").equalTo(teacherUsername);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        // Retrieve data here
                        String teacherFullName = dataSnapshot.child("teacherFullName").getValue(String.class);
                        teacherId = dataSnapshot.child("teacherId").getValue(String.class);
                        String teacherEmail = dataSnapshot.child("teacherEMailId").getValue(String.class);
                        String teacherMobileNo = dataSnapshot.child("teacherMobileNo").getValue(String.class);
                        String teacherAadharNo = dataSnapshot.child("teacherAadharNo").getValue(String.class);
                        String teacherPanNo = dataSnapshot.child("teacherPanNo").getValue(String.class);
                        String teacherUsername = dataSnapshot.child("teacherUsername").getValue(String.class);

                        // Update UI or do something with the retrieved data
                        tv_teacher_name.setText(teacherFullName);
                        et_teacher_full_name.setText(teacherFullName);
                        et_teacher_id.setText(teacherId);
                        et_teacher_mail.setText(teacherEmail);
                        et_teacher_mobile_no.setText(teacherMobileNo);
                        et_teacher_aadhar_no.setText(teacherAadharNo);
                        et_teacher_pan_no.setText(teacherPanNo);
                        et_teacher_username.setText(teacherUsername);
                    }
                } else {
                    Toast.makeText(TeacherHomeActivity.this, "teacher Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherHomeActivity.this, "Error retrieving data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateTeacherProfile(View view) {
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("teacher's_Profile");
        Query query = reference.orderByChild("teacherUsername").equalTo(teacherUsername);

        // Retrieve updated data from EditText fields
        String updatedteacherFullName = et_teacher_full_name.getText().toString();
        String updatedteacherId = et_teacher_id.getText().toString();
        String updatedteacherEmail = et_teacher_mail.getText().toString();
        String updatedteacherMobileNo = et_teacher_mobile_no.getText().toString();
        String updatedteacherAadharNo = et_teacher_aadhar_no.getText().toString();
        String updatedteacherPanNo = et_teacher_pan_no.getText().toString();
        String updatedteacherUsername = et_teacher_username.getText().toString();

        // Update the teacher data in the Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference teacherReference = snapshot.getRef();
                        teacherReference.child("teacherFullName").setValue(updatedteacherFullName);
                        teacherReference.child("teacherId").setValue(updatedteacherId);
                        teacherReference.child("teacherEMailId").setValue(updatedteacherEmail);
                        teacherReference.child("teacherMobileNo").setValue(updatedteacherMobileNo);
                        teacherReference.child("teacherAadharNo").setValue(updatedteacherAadharNo);
                        teacherReference.child("teacherPanNo").setValue(updatedteacherPanNo);
                        teacherReference.child("teacherUsername").setValue(updatedteacherUsername);
                    }

                    // Notify the user that changes have been saved
                    Toast.makeText(TeacherHomeActivity.this, "teacher data updated successfully", Toast.LENGTH_SHORT).show();

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
                        updateTeacherProfile(v);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TeacherHomeActivity.this, "Error updating data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void animateCardViewDown(CardView cardView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                cardView,
                "translationY",
                cardView.getTranslationY() + getResources().getDimension(R.dimen.translation_distance)
        );

        animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animator.start();
    }

    private void animateCardViewUp(CardView cardView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                cardView,
                "translationY",
                0f
        );

        animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animator.start();
    }
}