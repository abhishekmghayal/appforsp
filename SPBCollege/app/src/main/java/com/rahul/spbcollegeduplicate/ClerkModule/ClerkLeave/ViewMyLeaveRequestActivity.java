package com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

public class ViewMyLeaveRequestActivity extends AppCompatActivity {

    TextView tv_leave_staff_id, tv_leave_staff_name, tv_leave_staff_designation,
            tv_leave_start, tv_leave_end, tv_leave_title, tv_leave_description, tv_leave_status;
    Button btn_close_leave;
    String clerkName,fromDate,toDate,status,clerkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_leave_request);

        setTitle("My Leave Request");

        Intent intent = getIntent();
        if (intent != null) {
             clerkName = intent.getStringExtra("clerkNameForLeave");
             fromDate = intent.getStringExtra("clerkStartLeaveFrom");
             toDate = intent.getStringExtra("clerkLeaveEndTo");
             status = intent.getStringExtra("clerkLeaveStatus");
             clerkId = intent.getStringExtra("clerkId");
        }else {
            Toast.makeText(this, "Error: No data received", Toast.LENGTH_SHORT).show();
            finish();
        }

        tv_leave_staff_id = findViewById(R.id.view_leave_request_staff_id);
        tv_leave_staff_name = findViewById(R.id.view_leave_request_staff_name);
        tv_leave_staff_designation = findViewById(R.id.view_leave_request_staff_designation);
        tv_leave_start = findViewById(R.id.view_leave_request_leave_from_date);
        tv_leave_end = findViewById(R.id.view_leave_request_leave_to_date);
        tv_leave_title = findViewById(R.id.view_leave_request_staff_leave_title_value_title);
        tv_leave_description = findViewById(R.id.view_leave_request_staff_leave_description_value_title);
        tv_leave_status = findViewById(R.id.view_leave_request_staff_leave_status_value);

        btn_close_leave = findViewById(R.id.view_leave_request_close_leave_btn);

        btn_close_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

      //  Toast.makeText(ViewMyLeaveRequestActivity.this,clerkName+fromDate+toDate+status,Toast.LENGTH_SHORT).show();

        retrieveStaffDataOfLeave(clerkId,clerkName,fromDate,toDate);
    }

    private void retrieveStaffDataOfLeave(String staffId, String staffName, String staffLeaveFrom, String staffLeaveTo) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's")
                .child(staffId);

        clerkReference.orderByChild("staffName").equalTo(staffName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot leaveSnapshot : snapshot.getChildren()) {
                        String leaveStart = leaveSnapshot.child("staffLeaveStartDate").getValue(String.class);
                        String leaveEnd = leaveSnapshot.child("staffLeaveEndDate").getValue(String.class);

                        if (leaveStart != null && leaveEnd != null && leaveStart.equals(staffLeaveFrom) && leaveEnd.equals(staffLeaveTo)) {
                            // Data found, now you can use the retrieved data
                            String staffFullName = leaveSnapshot.child("staffName").getValue(String.class);
                            String staffDesignation = leaveSnapshot.child("staffDesignationOfLeave").getValue(String.class);
                            String staffLeaveTitle = leaveSnapshot.child("staffLeaveTitle").getValue(String.class);
                            String staffLeaveDescription = leaveSnapshot.child("staffLeaveDescription").getValue(String.class);
                            String staffLeaveStatus = leaveSnapshot.child("staffLeaveStatus").getValue(String.class);

                            if (staffLeaveStatus == null) {
                                staffLeaveStatus = "Pending";
                            }

                            // Now you can use the retrieved data as needed
                            tv_leave_staff_id.setText(staffId);
                            tv_leave_staff_name.setText(staffFullName);
                            tv_leave_staff_designation.setText(staffDesignation);
                            tv_leave_start.setText(leaveStart);
                            tv_leave_end.setText(leaveEnd);
                            tv_leave_title.setText(staffLeaveTitle);
                            tv_leave_description.setText(staffLeaveDescription);
                            tv_leave_status.setText(staffLeaveStatus);

                            btn_close_leave.setOnClickListener(view -> closeLeaveRequest(leaveSnapshot.getKey()));

                        }
                    }
                } else {
                    Toast.makeText(ViewMyLeaveRequestActivity.this, "Leave Data Not Exist", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewMyLeaveRequestActivity.this, "Error Loading Leave data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void closeLeaveRequest(String key) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's")
                .child(clerkId)
                .child(key);
        clerkReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ViewMyLeaveRequestActivity.this, "Leave Request Closed Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ViewMyLeaveRequestActivity.this, "Failed to Close Leave Request", Toast.LENGTH_SHORT).show();
                });
    }



}