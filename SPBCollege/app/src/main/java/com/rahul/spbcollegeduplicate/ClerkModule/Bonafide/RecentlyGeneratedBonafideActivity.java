package com.rahul.spbcollegeduplicate.ClerkModule.Bonafide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class RecentlyGeneratedBonafideActivity extends AppCompatActivity {

    FloatingActionButton btn_newBonafide;
    ListView studentList;
    ArrayList<String> studentBonafideNameList = new ArrayList<>();
    ArrayList<String> bonafideStudLearningYearList = new ArrayList<>();
    ArrayList<String> studentBonafideSTDList = new ArrayList<>();
    RelativeLayout relativeLayout;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_genrated_bonafide);

        setTitle("Student Bonafide Record");

        btn_newBonafide = findViewById(R.id.btn_rently_genredstudent_newbonafide);
        studentList = findViewById(R.id.list_view_recetlygenrated_bonafide);

        btn_newBonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecentlyGeneratedBonafideActivity.this, BonafideCertificateActivity.class);
                startActivity(i);
            }
        });

        StudentBonafideAdapterClass studentBonafideAdapterClass = new StudentBonafideAdapterClass(
                getBaseContext(),
                studentBonafideNameList,
                bonafideStudLearningYearList,
                studentBonafideSTDList,
                relativeLayout
        );
        studentList.setAdapter(studentBonafideAdapterClass);

        reference = FirebaseDatabase.getInstance().getReference("Student's_Bonafide_Record");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String studentName = snapshot.child("studentName").getValue(String.class);
               // String studentDOB = snapshot.child("studentDOB").getValue(String.class);
                String studentSTD = snapshot.child("studentLearningStandard").getValue(String.class);
                String studentLearningYear = snapshot.child("studentLearningYear").getValue(String.class);

                // Add the retrieved data to the lists
                studentBonafideNameList.add(studentName);
                bonafideStudLearningYearList.add(studentLearningYear);
                studentBonafideSTDList.add(studentSTD);

                // Notify the adapter that the data has changed
                studentBonafideAdapterClass.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected data from the adapter
                String selectedStudentName = studentBonafideNameList.get(position);
                String selectedStudentLearningYear = bonafideStudLearningYearList.get(position);

                Intent intent = new Intent(RecentlyGeneratedBonafideActivity.this, ViewBonafideActivity.class);
                intent.putExtra("StudentName", selectedStudentName+"_"+selectedStudentLearningYear);
                startActivity(intent);
            }
        });
    }
}
