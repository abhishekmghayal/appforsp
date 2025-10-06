package com.rahul.spbcollegeduplicate.ClerkModule.LeavingCertificate;

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

public class LeavingCertificateActivity extends AppCompatActivity {
    FloatingActionButton addnewleavingcerti;
    RelativeLayout relativeLayout;
    ListView leavingCertificateListView;
    StudentLeavingAdapaterClass studentLeavingAdapaterClass;
    ArrayList<String> studentNameList = new ArrayList<>();
    ArrayList<String> generalRegisterNoList = new ArrayList<>();
    ArrayList<String> studentSTDList = new ArrayList<>();
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_certificate);

        setTitle("Leaving Certificate's");

        addnewleavingcerti=findViewById(R.id.btn_rently_genredstudent_newleavingcertificate);
        leavingCertificateListView=findViewById(R.id.list_view_recetlygenrated_leavingcertificate);

        addnewleavingcerti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LeavingCertificateActivity.this, AddStudentLeavingCertificateActivity.class);
                startActivity(i);
                finish();
            }
        });


        studentLeavingAdapaterClass = new StudentLeavingAdapaterClass(getBaseContext(), studentNameList, generalRegisterNoList, studentSTDList, relativeLayout);
        leavingCertificateListView.setAdapter(studentLeavingAdapaterClass);
        reference = FirebaseDatabase.getInstance().getReference("Leaving_Certificate_Record");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String generalRegisterNo = snapshot.child("student_general_register_no").getValue(String.class);
                String studentFullName = snapshot.child("student_full_name").getValue(String.class);
                String studentSTD = snapshot.child("student_current_std").getValue(String.class);

                studentNameList.add(studentFullName);
                generalRegisterNoList.add(generalRegisterNo);
                studentSTDList.add(studentSTD);

                studentLeavingAdapaterClass.notifyDataSetChanged();
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

        leavingCertificateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedGeneralRegNo = generalRegisterNoList.get(position);
                Intent intent = new Intent(LeavingCertificateActivity.this, ViewStudentLeavingCertificate.class);
                intent.putExtra("GENERAL_REGISTER_NO", selectedGeneralRegNo);
                startActivity(intent);
            }
        });



    }
}