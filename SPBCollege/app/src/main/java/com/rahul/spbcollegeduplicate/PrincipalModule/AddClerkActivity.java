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
import com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses.AddClerkProfileToDatabaseHelperClass;
import com.rahul.spbcollegeduplicate.R;

public class AddClerkActivity extends AppCompatActivity {

    EditText et_clerk_full_name,et_clerk_Id,et_clerk_mo_no,et_clerk_mail,et_clerk_aadhar_no,et_clerk_pan_no,et_clerk_username,et_clerk_password;
    CheckBox addClerkPassShow;
    Button btn_add_clerk;

    String clerkFullName, clerkId, clerkMobileNo,clerkEmailId,clerkAadharNo,clerkPanNo,clerkUsername,clerkPassword;


    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clerk);

        et_clerk_full_name = findViewById(R.id.add_clerk_et_clerk_name);
        et_clerk_Id = findViewById(R.id.add_clerk_et_clerk_id);
        et_clerk_mo_no = findViewById(R.id.add_clerk_et_clerk_mo_no);
        et_clerk_mail = findViewById(R.id.add_clerk_et_clerk_email);
        et_clerk_aadhar_no = findViewById(R.id.add_clerk_et_clerk_aadhar_no);
        et_clerk_pan_no = findViewById(R.id.add_clerk_et_clerk_pan_no);
        et_clerk_username = findViewById(R.id.add_clerk_et_clerk_username);
        et_clerk_password = findViewById(R.id.add_clerk_et_clerk_password);
        btn_add_clerk = findViewById(R.id.add_clerk_btn_add_profile);
        addClerkPassShow = findViewById(R.id.add_clerk_chk_show_pass);



        setTitle("Add Clerk Profile");

         btn_add_clerk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (et_clerk_full_name.getText().toString().isEmpty()){
                     et_clerk_full_name.setError("Enter Full Name");
                 } else if (et_clerk_Id.getText().toString().isEmpty()) {
                     et_clerk_Id.setError("Enter Id");
                 } else if (et_clerk_mo_no.getText().toString().isEmpty()) {
                     et_clerk_mo_no.setError("Enter Mobile No");
                 } else if (et_clerk_mail.getText().toString().isEmpty()) {
                     et_clerk_mail.setError("Enter Email");
                 } else if (et_clerk_aadhar_no.getText().toString().isEmpty()) {
                     et_clerk_aadhar_no.setError("Enter Aadhar No");
                 } else if (et_clerk_pan_no.getText().toString().isEmpty()) {
                     et_clerk_pan_no.setError("Enter Pan No");
                 } else if (et_clerk_username.getText().toString().isEmpty()) {
                     et_clerk_username.setError("Enter Username");
                 } else if (et_clerk_password.getText().toString().isEmpty()) {
                     et_clerk_password.setError("Enter Password");
                 }else {
                     addClerkProfileToDatabase();
                 }
             }
         });

    }

    private void addClerkProfileToDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Clerk's_Profile");

        String clerkFullName = et_clerk_full_name.getText().toString();
        String clerkId = et_clerk_Id.getText().toString();
        String clerkMobileNo = et_clerk_mo_no.getText().toString();
        String clerkEmailId = et_clerk_mail.getText().toString();
        String clerkAadharNo = et_clerk_aadhar_no.getText().toString();
        String clerkPanNo = et_clerk_pan_no.getText().toString();
        String clerkUsername = et_clerk_username.getText().toString();
        String clerkPassword = et_clerk_password.getText().toString();

        AddClerkProfileToDatabaseHelperClass addClerkProfileToDatabaseHelperClass = new AddClerkProfileToDatabaseHelperClass(clerkFullName, clerkId, clerkMobileNo, clerkEmailId, clerkAadharNo, clerkPanNo, clerkUsername, clerkPassword);
        reference.child(clerkId).setValue(addClerkProfileToDatabaseHelperClass);

        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddClerkActivity.this, ManageClerkActivity.class);
        startActivity(intent);

    }
}