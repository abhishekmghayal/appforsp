package com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

import java.util.Calendar;

public class ClerkApplyLeaveRequestActivity extends AppCompatActivity {

    EditText et_clerk_id, et_clerk_name, et_clerk_leave_designation, et_clerk_leave_start, et_clerk_leave_end, et_clerk_leave_title, et_clerk_leave_description;
    Button btn_apply_leave;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clek_leave_request);

        setTitle("Leave Request");

        et_clerk_id = findViewById(R.id.et__aclr_id);
        et_clerk_name = findViewById(R.id.et__aclr_Name);
        et_clerk_leave_designation = findViewById(R.id.et__aclr_designation);
        et_clerk_leave_start = findViewById(R.id.et__aclr_lestartdate);
        et_clerk_leave_end = findViewById(R.id.et__aclr_leenddate);
        et_clerk_leave_title = findViewById(R.id.et__aclr_letitle);
        et_clerk_leave_description = findViewById(R.id.et__aclr_leDescription);
        btn_apply_leave = findViewById(R.id.btn__aclr_Apply);

        et_clerk_leave_start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Calendar calendar=Calendar.getInstance();
            final  int year=calendar.get(Calendar.YEAR);
            final  int month=calendar.get(Calendar.MONTH);
            final  int day=calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ClerkApplyLeaveRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            et_clerk_leave_start.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

        et_clerk_leave_end.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Calendar calendar=Calendar.getInstance();
            final  int year=calendar.get(Calendar.YEAR);
            final  int month=calendar.get(Calendar.MONTH);
            final  int day=calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ClerkApplyLeaveRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            et_clerk_leave_end.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });



        btn_apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();

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
                applyClerkLeave();            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void applyClerkLeave() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Clerk's_Leave_Request's");

        final String clerkIdForLeave = et_clerk_id.getText().toString().trim();
        final String clerkLeaveStartDateForLeave = et_clerk_leave_start.getText().toString().trim();
        final String clerkLeaveEndDateForLeave = et_clerk_leave_end.getText().toString().trim();

        reference.child(clerkIdForLeave).orderByChild("staffLeaveStartDate").equalTo(clerkLeaveStartDateForLeave)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean alreadyAppliedForThisLeave = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String existingStaffLeaveEndDateForLeave = snapshot.child("staffLeaveEndDate").getValue(String.class);

                            // Check if the clerk has already applied for leave with the same staffID, leaveStartDate, and leaveEndDate
                            if (existingStaffLeaveEndDateForLeave != null && existingStaffLeaveEndDateForLeave.equals(clerkLeaveEndDateForLeave)) {
                                alreadyAppliedForThisLeave = true;
                                break;
                            }
                        }

                        if (alreadyAppliedForThisLeave) {
                            Toast.makeText(ClerkApplyLeaveRequestActivity.this, "You have already applied for leave with the same details", Toast.LENGTH_SHORT).show();
                        } else {
                            storeClerkLeaveData();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if any
                        Toast.makeText(ClerkApplyLeaveRequestActivity.this, "Error checking for existing clerk: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeClerkLeaveData() {
        String clerkIdForLeave = et_clerk_id.getText().toString().trim();
        String clerkNameForLeave = et_clerk_name.getText().toString();
        String clerkDesignationForLeave = et_clerk_leave_designation.getText().toString();
        String clerkLeaveStartDateForLeave = et_clerk_leave_start.getText().toString().trim();
        String clerkLeaveEndDateForLeave = et_clerk_leave_end.getText().toString().trim();
        String clerkLeaveTitleForLeave = et_clerk_leave_title.getText().toString();
        String clerkLeaveDescriptionForLeave = et_clerk_leave_description.getText().toString();

        DatabaseReference staffReference = reference.child(clerkIdForLeave);
        String newEntryKey = staffReference.push().getKey();

        staffReference.child(newEntryKey).child("staffId").setValue(clerkIdForLeave);
        staffReference.child(newEntryKey).child("staffName").setValue(clerkNameForLeave);
        staffReference.child(newEntryKey).child("staffDesignationOfLeave").setValue(clerkDesignationForLeave);
        staffReference.child(newEntryKey).child("staffLeaveStartDate").setValue(clerkLeaveStartDateForLeave);
        staffReference.child(newEntryKey).child("staffLeaveEndDate").setValue(clerkLeaveEndDateForLeave);
        staffReference.child(newEntryKey).child("staffLeaveTitle").setValue(clerkLeaveTitleForLeave);
        staffReference.child(newEntryKey).child("staffLeaveDescription").setValue(clerkLeaveDescriptionForLeave);

        Toast.makeText(this, "Successfully Applied For Leave", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ClerkApplyLeaveRequestActivity.this, ClerkLeaveReviewActivity.class);
        startActivity(intent);
        finish();
    }
}
