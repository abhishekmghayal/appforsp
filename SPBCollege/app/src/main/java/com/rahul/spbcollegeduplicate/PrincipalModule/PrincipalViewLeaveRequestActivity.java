package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class PrincipalViewLeaveRequestActivity extends AppCompatActivity {

    TextView tv_leave_staff_id, tv_leave_staff_name, tv_leave_staff_designation,
            tv_leave_start, tv_leave_end, tv_leave_title, tv_leave_description, tv_leave_status;
    Button btn_RejectLeave, btn_ApproveLeave;
    String staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave_request);

        setTitle("View Leave Request");

        tv_leave_staff_id = findViewById(R.id.view_leave_request_staff_id);
        tv_leave_staff_name = findViewById(R.id.view_leave_request_staff_name);
        tv_leave_staff_designation = findViewById(R.id.view_leave_request_staff_designation);
        tv_leave_start = findViewById(R.id.view_leave_request_leave_from_date);
        tv_leave_end = findViewById(R.id.view_leave_request_leave_to_date);
        tv_leave_title = findViewById(R.id.view_leave_request_staff_leave_title_value_title);
        tv_leave_description = findViewById(R.id.view_leave_request_staff_leave_description_value_title);
        tv_leave_status = findViewById(R.id.view_leave_request_staff_leave_status_value);

        btn_RejectLeave = findViewById(R.id.view_leave_request_reject_leave_btn);
        btn_ApproveLeave = findViewById(R.id.view_leave_request_approve_leave_btn);

        Intent intent = getIntent();
        if (intent != null) {
            staffId = intent.getStringExtra("staffId");
            String staffName = intent.getStringExtra("staffName");
            String staffLeaveFrom = intent.getStringExtra("staffLeaveFrom");
            String staffLeaveTo = intent.getStringExtra("staffLeaveTo");
            retrieveStaffDataOfLeave(staffId, staffName, staffLeaveFrom, staffLeaveTo);

        } else {
            Toast.makeText(this, "Error: No data received", Toast.LENGTH_SHORT).show();
            finish();
        }
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

                            btn_RejectLeave.setOnClickListener(view -> showDialogForRejectLeave(leaveSnapshot.getKey()));
                            btn_ApproveLeave.setOnClickListener(view -> showDialogForApproveLeave(leaveSnapshot.getKey()));
                        }
                    }
                } else {
                    Toast.makeText(PrincipalViewLeaveRequestActivity.this, "Leave Data Not Exist", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PrincipalViewLeaveRequestActivity.this, "Error Loading Leave data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDialogForRejectLeave(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Reject Leave ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rejectLeaveRequest(key);
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

    private void rejectLeaveRequest(String leaveRequestKey) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's")
                .child(staffId).child(leaveRequestKey);

        String rejectedLeaveStatus = "Rejected";
        clerkReference.child("staffLeaveStatus").setValue(rejectedLeaveStatus);

        Toast.makeText(PrincipalViewLeaveRequestActivity.this, "Leave Rejected successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PrincipalViewLeaveRequestActivity.this, LeaveRequestActivity.class);
        startActivity(intent);
        finish();
    }



    private void showDialogForApproveLeave(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Approve Leave ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                approveLeaveRequest(key);
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

    private void approveLeaveRequest(String leaveRequestKey) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's")
                .child(staffId).child(leaveRequestKey);

        String approvedLeaveStatus = "Approved";
        clerkReference.child("staffLeaveStatus").setValue(approvedLeaveStatus);

        Toast.makeText(PrincipalViewLeaveRequestActivity.this, "Leave Approved successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PrincipalViewLeaveRequestActivity.this, LeaveRequestActivity.class);
        startActivity(intent);
        finish();
    }
}
