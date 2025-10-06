package com.rahul.spbcollegeduplicate.ClerkModule.Admission.ViewData;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.ClerkModule.Admission.InputingData.Clerk_Admission_Form_Activity;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class ActivityViewRecentlyAdmissionForm extends AppCompatActivity {

    FloatingActionButton btn_newBonafide;
    ListView admissionDataListView;
    ArrayList<String> studentNameList = new ArrayList<>();
    ArrayList<String> studentAdmissionNoList = new ArrayList<>();
    ArrayList<String> studentPhotoList = new ArrayList<>();
    ArrayList<String> studentSTDList = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recently_admission_form);

        setTitle("Admission Form's");

        btn_newBonafide = findViewById(R.id.btn_rently_genred_admiision_form_student_new_student);
        admissionDataListView = findViewById(R.id.list_view_recetly_admission_form_genrated_icard);

        databaseReference = FirebaseDatabase.getInstance().getReference("admissionData");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AdmissionAdapterClass adapter = new AdmissionAdapterClass(this, studentNameList, studentAdmissionNoList, studentPhotoList, studentSTDList);
        admissionDataListView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear existing data
                studentNameList.clear();
                studentAdmissionNoList.clear();
                studentPhotoList.clear();
                studentSTDList.clear();

                // Iterate through each child node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get student data
                    String studentName = snapshot.child("student_full_name").getValue(String.class);
                    String studentAdmissionNo = snapshot.child("stud_admission_no").getValue(String.class);
                    String studentSTD = snapshot.child("stud_admission_class").getValue(String.class);

                    // Add data to respective lists
                    studentNameList.add(studentName);
                    studentAdmissionNoList.add(studentAdmissionNo);
                    studentSTDList.add(studentSTD);

                    // Retrieve image URL from Firebase Storage
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                    studentPhotoList.add(imageUrl);
                    progressDialog.dismiss();
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                progressDialog.dismiss();
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });

        btn_newBonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityViewRecentlyAdmissionForm.this, Clerk_Admission_Form_Activity.class);
                startActivity(i);
            }
        });

        admissionDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAdmissionNo = studentAdmissionNoList.get(position);
                Intent intent = new Intent(ActivityViewRecentlyAdmissionForm.this, ActivityVIewStudentAdmissionForm.class);
                intent.putExtra("admissionNo", selectedAdmissionNo);
                startActivity(intent);
            }
        });    }
}
