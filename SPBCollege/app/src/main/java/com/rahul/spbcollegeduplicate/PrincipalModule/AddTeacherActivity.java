package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses.AddTeacherProfileToDatabaseHelperClass;
import com.rahul.spbcollegeduplicate.R;

public class AddTeacherActivity extends AppCompatActivity {

    EditText et_teacher_full_name,et_teacher_Id,et_teacher_mo_no,et_teacher_mail,et_teacher_aadhar_no,et_teacher_pan_no,et_teacher_username,et_teacher_password;
    CheckBox addteacherPassShow;
    Button btn_add_teacher;

    String teacherFullName, teacherId, teacherMobileNo,teacherEmailId,teacherAadharNo,teacherPanNo,teacherUsername,teacherPassword;


    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        et_teacher_full_name = findViewById(R.id.add_teacher_et_teacher_name);
        et_teacher_Id = findViewById(R.id.add_teacher_et_teacher_id);
        et_teacher_mo_no = findViewById(R.id.add_teacher_et_teacher_mo_no);
        et_teacher_mail = findViewById(R.id.add_teacher_et_teacher_email);
        et_teacher_aadhar_no = findViewById(R.id.add_teacher_et_teacher_aadhar_no);
        et_teacher_pan_no = findViewById(R.id.add_teacher_et_teacher_pan_no);
        et_teacher_username = findViewById(R.id.add_teacher_et_teacher_username);
        et_teacher_password = findViewById(R.id.add_teacher_et_teacher_password);
        btn_add_teacher = findViewById(R.id.add_teacher_btn_add_profile);
        addteacherPassShow = findViewById(R.id.add_teacher_chk_show_pass);



        setTitle("Add teacher Profile");

        btn_add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_teacher_full_name.getText().toString().isEmpty()){
                    et_teacher_full_name.setError("Enter Full Name");
                } else if (et_teacher_Id.getText().toString().isEmpty()) {
                    et_teacher_Id.setError("Enter Id");
                } else if (et_teacher_mo_no.getText().toString().isEmpty()) {
                    et_teacher_mo_no.setError("Enter Mobile No");
                } else if (et_teacher_mail.getText().toString().isEmpty()) {
                    et_teacher_mail.setError("Enter Email");
                } else if (et_teacher_aadhar_no.getText().toString().isEmpty()) {
                    et_teacher_aadhar_no.setError("Enter Aadhar No");
                } else if (et_teacher_pan_no.getText().toString().isEmpty()) {
                    et_teacher_pan_no.setError("Enter Pan No");
                } else if (et_teacher_username.getText().toString().isEmpty()) {
                    et_teacher_username.setError("Enter Username");
                } else if (et_teacher_password.getText().toString().isEmpty()) {
                    et_teacher_password.setError("Enter Password");
                }else {
                    addteacherProfileToDatabase();
                }
            }
        });

    }

    private void addteacherProfileToDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("teacher's_Profile");

        String teacherFullName = et_teacher_full_name.getText().toString();
        String teacherId = et_teacher_Id.getText().toString();
        String teacherMobileNo = et_teacher_mo_no.getText().toString();
        String teacherEmailId = et_teacher_mail.getText().toString();
        String teacherAadharNo = et_teacher_aadhar_no.getText().toString();
        String teacherPanNo = et_teacher_pan_no.getText().toString();
        String teacherUsername = et_teacher_username.getText().toString();
        String teacherPassword = et_teacher_password.getText().toString();

        AddTeacherProfileToDatabaseHelperClass addteacherProfileToDatabaseHelperClass = new AddTeacherProfileToDatabaseHelperClass(teacherFullName, teacherId, teacherMobileNo, teacherEmailId, teacherAadharNo, teacherPanNo, teacherUsername, teacherPassword);
        reference.child(teacherId).setValue(addteacherProfileToDatabaseHelperClass);

        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddTeacherActivity.this, ManageTeacherActivity.class);
        startActivity(intent);

    }
}