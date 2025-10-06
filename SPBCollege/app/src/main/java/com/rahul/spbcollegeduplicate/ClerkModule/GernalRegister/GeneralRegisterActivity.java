package com.rahul.spbcollegeduplicate.ClerkModule.GernalRegister;

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

public class GeneralRegisterActivity extends AppCompatActivity {
    FloatingActionButton btnstudentAdd;
    RelativeLayout relativeLayout;
    ListView registerDataListView;
    StudentGernalRegisterListAdapterClass gernalRegisterListAdapterClass;
    ArrayList<String> studentNameList = new ArrayList<>();
    ArrayList<String> generalRegisterNoList = new ArrayList<>();
    ArrayList<String> studentIdList = new ArrayList<>();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_general_register);
        setTitle("General Register");
        registerDataListView = findViewById(R.id.list_view_genral_register);
        relativeLayout = findViewById(R.id.layout_general_register);
        btnstudentAdd = findViewById(R.id.btn_sgr_add);

        btnstudentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GeneralRegisterActivity.this, AddStudentGenralRegisterActivity.class);
                startActivity(i);

            }
        });
        gernalRegisterListAdapterClass = new StudentGernalRegisterListAdapterClass(getBaseContext(), studentNameList, generalRegisterNoList, studentIdList, relativeLayout);
        registerDataListView.setAdapter(gernalRegisterListAdapterClass);

        reference = FirebaseDatabase.getInstance().getReference("GeneralRegister");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String generalRegisterNo = snapshot.child("student_general_register_no").getValue(String.class);
                String studentName = snapshot.child("student_name").getValue(String.class);
                String studentSurName = snapshot.child("student_surname").getValue(String.class);
                String studentFatherName = snapshot.child("student_father_name").getValue(String.class);
                String studentID = snapshot.child("student_id").getValue(String.class);

                studentNameList.add(studentName + " " + studentFatherName + " " + studentSurName);
                generalRegisterNoList.add(generalRegisterNo);
                studentIdList.add(studentID);

                gernalRegisterListAdapterClass.notifyDataSetChanged();
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

                registerDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedGeneralRegNo = generalRegisterNoList.get(position);
                    Intent intent = new Intent(GeneralRegisterActivity.this, ViewStudentGeneralRegActivity.class);
                    intent.putExtra("GENERAL_REGISTER_NO", selectedGeneralRegNo);
                    startActivity(intent);
                }
            });
    }
}

