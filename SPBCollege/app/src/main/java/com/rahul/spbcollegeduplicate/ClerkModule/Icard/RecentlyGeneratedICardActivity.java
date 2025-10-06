package com.rahul.spbcollegeduplicate.ClerkModule.Icard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;
import java.util.List;

public class RecentlyGeneratedICardActivity extends AppCompatActivity {

    FloatingActionButton addnewicard;
    RecyclerView studentRecyclerView;

    List<StudentICardModelClass> studentList;
    StudentIcardAdapterClass studentIcardAdapterClass;

    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_student_recently_genrated_icard);
        addnewicard = findViewById(R.id.btn_rently_genredstudent_new_student);
        studentRecyclerView = findViewById(R.id.rcy_view_recetlygenrated_icard);

        setTitle("Students ICard's Record");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show progress dialog

        addnewicard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecentlyGeneratedICardActivity.this, StudentICardActivity.class);
                startActivity(intent);
            }
        });

        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentList = new ArrayList<>();
        studentIcardAdapterClass = new StudentIcardAdapterClass(studentList);
        studentRecyclerView.setAdapter(studentIcardAdapterClass);

        databaseReference = FirebaseDatabase.getInstance().getReference("Student's_ICard_Record");
        retrieveICardData();
    }

    private void retrieveICardData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();

                for (DataSnapshot classSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot icardSnapshot : classSnapshot.getChildren()) {
                        String studentFullName = icardSnapshot.child("studentFullName").getValue(String.class);
                        String studentGeneralREGNo = icardSnapshot.child("studentGeneralRegisterNumber").getValue(String.class);
                        String studentSTD = icardSnapshot.child("studentStandard").getValue(String.class);
                        String studentAcademicYear = icardSnapshot.child("studentAcademicYear").getValue(String.class);
                        String imageUrl = icardSnapshot.child("imageUrl").getValue(String.class); // Retrieve image URL

                        StudentICardModelClass studentICardModelClass = new StudentICardModelClass(studentFullName, studentGeneralREGNo, studentSTD, studentAcademicYear, imageUrl);
                        studentList.add(studentICardModelClass);
                    }
                }
                studentIcardAdapterClass.notifyDataSetChanged();
                progressDialog.dismiss(); // Dismiss progress dialog
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss(); // Dismiss progress dialog in case of error
                Toast.makeText(RecentlyGeneratedICardActivity.this, "Error retrieving ICard data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        studentIcardAdapterClass.setOnItemClickListener(new StudentIcardAdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(StudentICardModelClass selectedIcard) {
                Intent intent = new Intent(RecentlyGeneratedICardActivity.this, ViewStudentICardDetailsActivity.class);
                intent.putExtra("studentFullName", selectedIcard.getStudentFullName());
                intent.putExtra("studentGeneralREGNo", selectedIcard.getStudentGeneralRegisterNumber());
                intent.putExtra("studentSTD", selectedIcard.getStudentStandard());
                intent.putExtra("studentAcademicYear", selectedIcard.getStudentAcademicYear());
                intent.putExtra("imageUrl", selectedIcard.getImageUrl()); // Pass image URL to next activity
                startActivity(intent);
            }
        });
    }
}
