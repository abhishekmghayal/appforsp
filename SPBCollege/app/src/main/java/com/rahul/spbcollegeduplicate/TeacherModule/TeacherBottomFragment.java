package com.rahul.spbcollegeduplicate.TeacherModule;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

public class TeacherBottomFragment extends BottomSheetDialogFragment {
    EditText et_teacher_username,et_teacher_password;
    AppCompatButton btn_teacher_login;
    CheckBox checkBox_teacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_bottom_, container, false);

        et_teacher_username = view.findViewById(R.id.et_username_teacher);
        et_teacher_password = view.findViewById(R.id.et_password_teacher);
        checkBox_teacher = view.findViewById(R.id.chk_pass_show_teacher);
        btn_teacher_login = view.findViewById(R.id.btn_teacher_login);

        checkBox_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox_teacher.isChecked()){
                    et_teacher_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    et_teacher_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        btn_teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkTeacherProfileData();
                }
            }
        });
        return view;
    }

    public Boolean validateUsername() {
        String val = et_teacher_username.getText().toString();
        if (val.isEmpty()) {
            et_teacher_username.setError("Username cannot be empty");
            return false;
        } else {
            et_teacher_username.setError(null);
            return true;
        }
    }


    public Boolean validatePassword(){
        String val = et_teacher_password.getText().toString();
        if (val.isEmpty()) {
            et_teacher_password.setError("Password cannot be empty");
            return false;
        } else {
            et_teacher_password.setError(null);
            return true;
        }
    }

    public void checkTeacherProfileData() {
        String clerkUsername = et_teacher_username.getText().toString().trim();
        String clerkPassword = et_teacher_password.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("teacher's_Profile");
        Query checkUserDatabase = reference.orderByChild("teacherUsername").equalTo(clerkUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Retrieve the password from the snapshot
                        String passwordFromDB = userSnapshot.child("teacherPassword").getValue(String.class);


                        if (passwordFromDB.equals(clerkPassword)) {
                            et_teacher_username.setError(null);
                            et_teacher_password.setError(null);

                            // Retrieve other information from the snapshot
                            String clerkUsernameFromDB = userSnapshot.child("teacherUsername").getValue(String.class);

                            Intent intent = new Intent(getContext(), TeacherHomeActivity.class);
                            intent.putExtra("teacherLoginUsername", clerkUsernameFromDB);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Teacher Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}