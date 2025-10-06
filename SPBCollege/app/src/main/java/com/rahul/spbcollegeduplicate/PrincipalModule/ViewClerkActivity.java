package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ViewClerkActivity extends AppCompatActivity {
    EditText et_clerk_full_name, et_clerk_id, et_clerk_mail, et_clerk_mobile_no, et_clerk_aadhar_no, et_clerk_pan_no, et_clerk_username;
    Button btn_update_clerk_profile, btn_delete_clerk_profile;
    String clerkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clerk);
        setTitle("View Clerk");

        et_clerk_full_name = findViewById(R.id.view_clerk_clerk_name);
        et_clerk_id = findViewById(R.id.view_clerk_clerk_id);
        et_clerk_mail = findViewById(R.id.view_clerk_clerk_email_id);
        et_clerk_mobile_no = findViewById(R.id.view_clerk_clerk_mobile_no);
        et_clerk_aadhar_no = findViewById(R.id.view_clerk_clerk_aadhar_no);
        et_clerk_pan_no = findViewById(R.id.view_clerk_clerk_pan_no);
        et_clerk_username = findViewById(R.id.view_clerk_clerk_username);
        btn_update_clerk_profile = findViewById(R.id.view_clerk_update_clerk_btn);
        btn_delete_clerk_profile = findViewById(R.id.view_clerk_delete_clerk);

        et_clerk_full_name.setEnabled(false);
        et_clerk_id.setEnabled(false);
        et_clerk_mobile_no.setEnabled(false);
        et_clerk_mail.setEnabled(false);
        et_clerk_aadhar_no.setEnabled(false);
        et_clerk_pan_no.setEnabled(false);
        et_clerk_username.setEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            clerkId = intent.getStringExtra("ClerkId");
            retrieveClerkData(clerkId);
        }

        btn_update_clerk_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClerkProfile(view);
            }
        });

        btn_delete_clerk_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClerkProfile();
            }
        });
    }

    private void retrieveClerkData(String clerkId) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile").child(clerkId);

        clerkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String clerkFullName = snapshot.child("clerkFullName").getValue(String.class);
                    String clerkId = snapshot.child("clerkId").getValue(String.class);
                    String clerkEmail = snapshot.child("clerkEMailId").getValue(String.class);
                    String clerkMobileNo = snapshot.child("clerkMobileNo").getValue(String.class);
                    String clerkAadharNo = snapshot.child("clerkAadharNo").getValue(String.class);
                    String clerkPanNo = snapshot.child("clerkPanNo").getValue(String.class);
                    String clerkUsername = snapshot.child("clerkUsername").getValue(String.class);

                    et_clerk_full_name.setText(clerkFullName);
                    et_clerk_id.setText(clerkId);
                    et_clerk_mail.setText(clerkEmail);
                    et_clerk_mobile_no.setText(clerkMobileNo);
                    et_clerk_aadhar_no.setText(clerkAadharNo);
                    et_clerk_pan_no.setText(clerkPanNo);
                    et_clerk_username.setText(clerkUsername);
                } else {
                    Toast.makeText(ViewClerkActivity.this, "Clerk Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewClerkActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateClerkProfile(View view) {
        // Enable EditText fields for editing
        et_clerk_full_name.setEnabled(true);
        et_clerk_id.setEnabled(true);
        et_clerk_mail.setEnabled(true);
        et_clerk_mobile_no.setEnabled(true);
        et_clerk_aadhar_no.setEnabled(true);
        et_clerk_pan_no.setEnabled(true);
        et_clerk_username.setEnabled(true);

        // Change the button text to "Save Changes"
        btn_update_clerk_profile.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        btn_update_clerk_profile.setOnClickListener(v -> {
            saveChangesToFirebase();
        });
    }

    private void saveChangesToFirebase() {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile").child(clerkId);

        // Retrieve updated data from EditText fields
        String updatedClerkFullName = et_clerk_full_name.getText().toString();
        String updatedClerkId = et_clerk_id.getText().toString();
        String updatedClerkEmail = et_clerk_mail.getText().toString();
        String updatedClerkMobileNo = et_clerk_mobile_no.getText().toString();
        String updatedClerkAadharNo = et_clerk_aadhar_no.getText().toString();
        String updatedClerkPanNo = et_clerk_pan_no.getText().toString();
        String updatedClerkUsername = et_clerk_username.getText().toString();

        // Update the clerk data in the Firebase Realtime Database
        clerkReference.child("clerkFullName").setValue(updatedClerkFullName);
        clerkReference.child("clerkId").setValue(updatedClerkId);
        clerkReference.child("clerkEMailId").setValue(updatedClerkEmail);
        clerkReference.child("clerkMobileNo").setValue(updatedClerkMobileNo);
        clerkReference.child("clerkAadharNo").setValue(updatedClerkAadharNo);
        clerkReference.child("clerkPanNo").setValue(updatedClerkPanNo);
        clerkReference.child("clerkUsername").setValue(updatedClerkUsername);

        Toast.makeText(ViewClerkActivity.this, "Clerk data updated successfully", Toast.LENGTH_SHORT).show();

        et_clerk_full_name.setEnabled(false);
        et_clerk_id.setEnabled(false);
        et_clerk_mail.setEnabled(false);
        et_clerk_mobile_no.setEnabled(false);
        et_clerk_aadhar_no.setEnabled(false);
        et_clerk_pan_no.setEnabled(false);
        et_clerk_username.setEnabled(false);
        btn_update_clerk_profile.setText("Update");

        // Set the original click listener for the "Update" button
        btn_update_clerk_profile.setOnClickListener(v -> {
            updateClerkProfile(v);
        });
    }

    private void deleteClerkProfile() {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile").child(clerkId);

        clerkReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Clerk profile deleted successfully
                    Toast.makeText(ViewClerkActivity.this, "Clerk profile deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ViewClerkActivity.this, "Failed to delete clerk profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
