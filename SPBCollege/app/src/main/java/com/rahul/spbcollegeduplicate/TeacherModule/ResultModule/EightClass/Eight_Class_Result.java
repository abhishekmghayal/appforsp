package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.EightClass;

import android.app.ProgressDialog;
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
import com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.StudentResultAadappterClass;

import java.util.ArrayList;

public class Eight_Class_Result extends AppCompatActivity {
    ListView studentList;
    RelativeLayout relativeLayout;
    StudentResultAadappterClass studentResultAadappterClass;
    ArrayList<String> studentNameList = new ArrayList<>();
    ArrayList<String> generalRegisterNoList = new ArrayList<>();
    ArrayList<String> studentPerList = new ArrayList<>();
    DatabaseReference reference;
    FloatingActionButton fab;
    String generalRegisterNo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight_class_result);

        setTitle("Manage 8th Result");

        studentList=findViewById(R.id.list_view_eigtht_recetlymarksheet);
        fab=findViewById(R.id.btn_gernate_marksheet_eight_Class);
        studentResultAadappterClass = new StudentResultAadappterClass(getBaseContext(), studentNameList, generalRegisterNoList, studentPerList, relativeLayout);
        studentList.setAdapter(studentResultAadappterClass);

        reference = FirebaseDatabase.getInstance().getReference("EightclassResult");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                 generalRegisterNo = snapshot.child("GeneralRegNo").getValue(String.class);
                String studentFullName = snapshot.child("Name").getValue(String.class);
                String studentPer = snapshot.child("Percentage").getValue(String.class);

                if (studentPer != null) {
                    try {
                        Double per = Double.parseDouble(studentPer);
                        studentNameList.add(studentFullName);
                        generalRegisterNoList.add(generalRegisterNo);
                        studentPerList.add(String.valueOf(per));
                    } catch (NumberFormatException e) {
                        // Handle the case where Percentage value is not a valid Double
                        // For example, log the error or display a message
                    }
                } else {
                    // Handle the case where Percentage value is null
                    // For example, set a default value or display a message
                }

                studentResultAadappterClass.notifyDataSetChanged();

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
                Intent i=new Intent(Eight_Class_Result.this, View_Result_of_eight_Activity.class);
                i.putExtra("GeneralRegNo",generalRegisterNo);
                startActivity(i);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Eight_Class_Result.this, Genrate_New_Eight_Activity.class);
                startActivity(i);
            }
        });
    }
}