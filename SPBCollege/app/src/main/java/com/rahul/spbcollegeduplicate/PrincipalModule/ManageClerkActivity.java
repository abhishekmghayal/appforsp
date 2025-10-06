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
import com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses.ClerkListViewAdapterClass;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class ManageClerkActivity extends AppCompatActivity {
    FloatingActionButton fbtnAddClerk;
    RelativeLayout relativeLayout;
    ListView clerkListView;
    DatabaseReference reference;
    ClerkListViewAdapterClass clerkListViewAdapterClass;
    ArrayList<String> clerkNameList = new ArrayList<>();
    ArrayList<String> clerkIdList = new ArrayList<>();
    ArrayList<String> clerkMailList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clerk);
        setTitle("Manage Clerk");

        fbtnAddClerk = findViewById(R.id.fbtn_add_clerk_create_dialog);
        clerkListView = findViewById(R.id.list_view_clerk_list);
        relativeLayout = findViewById(R.id.manage_clerk_activity_listview);

        // Initialize the adapter with empty lists
        clerkListViewAdapterClass = new ClerkListViewAdapterClass(getBaseContext(), clerkNameList, clerkMailList,clerkIdList,relativeLayout);
        clerkListView.setAdapter(clerkListViewAdapterClass);

        reference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Assuming each child has "name" and "email" fields
                String clerkName = snapshot.child("clerkFullName").getValue(String.class);
                String clerkId = snapshot.child("clerkId").getValue(String.class);
                String clerkMail = snapshot.child("clerkEMailId").getValue(String.class);

                // Add the retrieved data to the lists
                clerkNameList.add(clerkName);
                clerkMailList.add(clerkMail);
                clerkIdList.add(clerkId);

                // Notify the adapter that the data has changed
                clerkListViewAdapterClass.notifyDataSetChanged();
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

        clerkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedClerkId = clerkIdList.get(position);
                Intent intent = new Intent(ManageClerkActivity.this, ViewClerkActivity.class);
                intent.putExtra("ClerkId", selectedClerkId);

                startActivity(intent);
            }
        });

        fbtnAddClerk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClerkActivity.this, AddClerkActivity.class);
                startActivity(intent);
            }
        });

    }
}
