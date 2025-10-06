package com.rahul.spbcollegeduplicate.PrincipalModule;

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
import com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses.TeacherListViewAdapterClass;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class ManageTeacherActivity extends AppCompatActivity {
    FloatingActionButton fbtnAddteacher;
    RelativeLayout relativeLayout;
    ListView teacherListView;
    DatabaseReference reference;
    TeacherListViewAdapterClass teacherListViewAdapterClass;
    ArrayList<String> teacherNameList = new ArrayList<>();
    ArrayList<String> teacherIdList = new ArrayList<>();
    ArrayList<String> teacherMailList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teacher);
        setTitle("Manage teacher");

        fbtnAddteacher = findViewById(R.id.fbtn_add_teacher_create_dialog);
        teacherListView = findViewById(R.id.list_view_teacher_list);
        relativeLayout = findViewById(R.id.manage_teacher_activity_listview);

        // Initialize the adapter with empty lists
        teacherListViewAdapterClass = new TeacherListViewAdapterClass(getBaseContext(), teacherNameList, teacherMailList,teacherIdList,relativeLayout);
        teacherListView.setAdapter(teacherListViewAdapterClass);

        reference = FirebaseDatabase.getInstance().getReference("teacher's_Profile");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Assuming each child has "name" and "email" fields
                String teacherName = snapshot.child("teacherFullName").getValue(String.class);
                String teacherId = snapshot.child("teacherId").getValue(String.class);
                String teacherMail = snapshot.child("teacherEMailId").getValue(String.class);

                // Add the retrieved data to the lists
                teacherNameList.add(teacherName);
                teacherMailList.add(teacherMail);
                teacherIdList.add(teacherId);


                // Notify the adapter that the data has changed
                teacherListViewAdapterClass.notifyDataSetChanged();
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

            // ... other overridden methods

        });

        teacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected data from the adapter
//              
                String selectedteacherId = teacherIdList.get(position);


                // Create an Intent to pass data to the second activity
                Intent intent = new Intent(ManageTeacherActivity.this, ViewTeacherActivity.class);

                // Pass the selected data as extras in the Intent
                intent.putExtra("teacherId", selectedteacherId);


                // Start the second activity
                startActivity(intent);
            }
        });



        // Rest of your code...

        fbtnAddteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageTeacherActivity.this, AddTeacherActivity.class);
                startActivity(intent);
            }
        });

    }
}
