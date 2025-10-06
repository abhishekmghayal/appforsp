package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses.LeaveRequestAdapterClass;
import com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses.LeaveRequestModelClass;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;
import java.util.List;
public class LeaveRequestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaveRequestAdapterClass adapter;
    private List<LeaveRequestModelClass> leaveRequests;
    private DatabaseReference leaveRequestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        setTitle("Leave Request's");

        recyclerView = findViewById(R.id.rcy_leave_lequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        leaveRequests = new ArrayList<>();
        adapter = new LeaveRequestAdapterClass(leaveRequests);
        recyclerView.setAdapter(adapter);

        leaveRequestReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's");
        retrieveLeaveRequests();
    }

    private void retrieveLeaveRequests() {
        leaveRequestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaveRequests.clear();

                for (DataSnapshot clerkSnapshot : dataSnapshot.getChildren()) {
                    String clerkId = clerkSnapshot.getKey();
                    for (DataSnapshot leaveSnapshot : clerkSnapshot.getChildren()) {
                        String staffId = leaveSnapshot.child("staffId").getValue(String.class);
                        String staffName = leaveSnapshot.child("staffName").getValue(String.class);
                        String staffLeaveFrom = leaveSnapshot.child("staffLeaveStartDate").getValue(String.class);
                        String staffLeaveTo = leaveSnapshot.child("staffLeaveEndDate").getValue(String.class);

                        if (staffId != null && staffName != null) {
                            LeaveRequestModelClass leaveRequest = new LeaveRequestModelClass(staffId, staffName,staffLeaveFrom,staffLeaveTo);
                            leaveRequests.add(leaveRequest);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if needed
                Toast.makeText(LeaveRequestActivity.this, "Error retrieving leave requests", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new LeaveRequestAdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(LeaveRequestModelClass leaveRequest) {
                // Handle item click, for example, start a new activity
                Intent intent = new Intent(LeaveRequestActivity.this, PrincipalViewLeaveRequestActivity.class);
                intent.putExtra("staffId", leaveRequest.getStaffId());
                intent.putExtra("staffName", leaveRequest.getStaffName());
                intent.putExtra("staffLeaveFrom", leaveRequest.getStaffLeaveFrom());
                intent.putExtra("staffLeaveTo", leaveRequest.getStaffLeaveTo());
                startActivity(intent);
            }
        });

    }
}
