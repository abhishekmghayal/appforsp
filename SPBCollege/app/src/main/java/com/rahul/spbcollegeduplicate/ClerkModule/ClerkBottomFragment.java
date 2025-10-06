package com.rahul.spbcollegeduplicate.ClerkModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class ClerkBottomFragment extends BottomSheetDialogFragment {

    EditText et_clerk_username, et_clerk_password;
    AppCompatButton btn_clerk_login;
    CheckBox checkBoxClerk;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "ClerkPrefs";
    private static final String KEY_LOGIN_STATUS = "isClerkLoggedIn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clerk_bottom_, container, false);

        et_clerk_username = view.findViewById(R.id.et_username_clerk);
        et_clerk_password = view.findViewById(R.id.et_password_clerk);
        btn_clerk_login = view.findViewById(R.id.btn_clerk_login);
        checkBoxClerk = view.findViewById(R.id.chk_pass_show_clerk);

        checkBoxClerk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxClerk.isChecked()) {
                    et_clerk_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_clerk_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_clerk_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkClerkProfileData();
                }
            }
        });
        return view;
    }

    public Boolean validateUsername() {
        String val = et_clerk_username.getText().toString();
        if (val.isEmpty()) {
            et_clerk_username.setError("Username cannot be empty");
            return false;
        } else {
            et_clerk_username.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = et_clerk_password.getText().toString();
        if (val.isEmpty()) {
            et_clerk_password.setError("Password cannot be empty");
            return false;
        } else {
            et_clerk_password.setError(null);
            return true;
        }
    }

    public void checkClerkProfileData() {
        String clerkUsername = et_clerk_username.getText().toString().trim();
        String clerkPassword = et_clerk_password.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile");
        Query checkUserDatabase = reference.orderByChild("clerkUsername").equalTo(clerkUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Retrieve the password from the snapshot
                        String passwordFromDB = userSnapshot.child("clerkPassword").getValue(String.class);

                        if (passwordFromDB.equals(clerkPassword)) {
                            et_clerk_username.setError(null);
                            et_clerk_password.setError(null);

                            // Retrieve other information from the snapshot
                            String clerkUsernameFromDB = userSnapshot.child("clerkUsername").getValue(String.class);

                            // Save login status
                            SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(KEY_LOGIN_STATUS, true);
                            editor.apply();

                            Intent intent = new Intent(requireContext(), ClerkHomeActivity.class);
                            intent.putExtra("clerkLoginUsername", clerkUsernameFromDB);
                            startActivity(intent);

                            // Close the fragment after successful login
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Clerk Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}