package com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave;

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
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;
import java.util.List;

public class ClerkLeaveReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaveRequestReviewAdapterClass adapter;
    private List<LeaveRequestReviewModelClass> leaveRequestReviewModelClassList;
    private DatabaseReference leaveRequestReference;
    String clerkIdInLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_leave_review);

        setTitle("Leave Request's");

        recyclerView = findViewById(R.id.rcy_leave_review_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (intent != null) {
            clerkIdInLeave = intent.getStringExtra("clerkId");
        }

        leaveRequestReviewModelClassList = new ArrayList<>();
        adapter = new LeaveRequestReviewAdapterClass(leaveRequestReviewModelClassList);
        recyclerView.setAdapter(adapter);

        leaveRequestReference = FirebaseDatabase.getInstance().getReference("Clerk's_Leave_Request's");
        retrieveLeaveRequests(clerkIdInLeave);
    }

    private void retrieveLeaveRequests(final String clerkId) {
        leaveRequestReference.child(clerkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaveRequestReviewModelClassList.clear();

                for (DataSnapshot leaveSnapshot : dataSnapshot.getChildren()) {
                    String clerkLeaveStatus = leaveSnapshot.child("staffLeaveStatus").getValue(String.class);
                    String clerkNameForLeave = leaveSnapshot.child("staffName").getValue(String.class);
                    String clerkStartLeaveFrom = leaveSnapshot.child("staffLeaveStartDate").getValue(String.class);
                    String clerkLeaveEndTo = leaveSnapshot.child("staffLeaveEndDate").getValue(String.class);

                    if (clerkNameForLeave != null) {
                        LeaveRequestReviewModelClass leaveRequest = new LeaveRequestReviewModelClass(clerkLeaveStatus, clerkNameForLeave, clerkStartLeaveFrom, clerkLeaveEndTo);
                        leaveRequestReviewModelClassList.add(leaveRequest);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ClerkLeaveReviewActivity.this, "Error retrieving leave requests", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new LeaveRequestReviewAdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(LeaveRequestReviewModelClass leaveRequest) {
                Intent intent = new Intent(ClerkLeaveReviewActivity.this, ViewMyLeaveRequestActivity.class);
                intent.putExtra("clerkLeaveStatus", leaveRequest.getClerkLeaveReviewStatus());
                intent.putExtra("clerkNameForLeave", leaveRequest.getClerkNameLeaveReview());
                intent.putExtra("clerkStartLeaveFrom", leaveRequest.getClerkStartFromLeaveReview());
                intent.putExtra("clerkLeaveEndTo", leaveRequest.getClerkEndToLeaveReview());
                intent.putExtra("clerkId",clerkIdInLeave );
                startActivity(intent);
            }
        });
    }
}
