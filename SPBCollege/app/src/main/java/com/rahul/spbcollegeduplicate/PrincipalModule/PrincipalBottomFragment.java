package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class PrincipalBottomFragment extends BottomSheetDialogFragment {

    EditText et_principal_username,et_principal_password;
    AppCompatButton btn_principal_login;
    TextView tv_register_principal;
    CheckBox checkBoxprincipal;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

//    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_principal__bottom_, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();



        et_principal_username = view.findViewById(R.id.et_username_principal);
        et_principal_password = view.findViewById(R.id.et_password_principal);
        btn_principal_login = view.findViewById(R.id.btn_principal_login);
        checkBoxprincipal = view.findViewById(R.id.chk_pass_show_principal);
        tv_register_principal = view.findViewById(R.id.tv_register_principal);

        checkBoxprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxprincipal.isChecked()){
                    et_principal_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    et_principal_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        btn_principal_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkPrincipalData();
                }
            }
        });
        tv_register_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterPrincipalActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public Boolean validateUsername() {
        String val = et_principal_username.getText().toString();
        if (val.isEmpty()) {
            et_principal_username.setError("Username cannot be empty");
            return false;
        } else {
            et_principal_username.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = et_principal_password.getText().toString();
        if (val.isEmpty()) {
            et_principal_password.setError("Password cannot be empty");
            return false;
        } else {
            et_principal_password.setError(null);
            return true;
        }
    }
    public void checkPrincipalData() {
        String principalUsername = et_principal_username.getText().toString().trim();
        String principalPassword = et_principal_password.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("principal's_profile");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(principalUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Retrieve the password from the snapshot
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        // Use .equals() to compare strings
                        if (passwordFromDB.equals
                                (principalPassword)) {
                            et_principal_username.setError(null);
                            et_principal_password.setError(null);

                            // Retrieve other information from the snapshot
                            String principalUsernameFromDB = userSnapshot.child("username").getValue(String.class);
                            //String principalUsernameFromDB = userSnapshot.child("username").getValue(String.class);

                            // Use an Intent to navigate to the next activity
                            Intent intent = new Intent(getContext(), PrincipalHomeActivity.class);
                           // intent.putExtra("principalLoginName", principalNameFromDB);
                            intent.putExtra("principalLoginUsername", principalUsernameFromDB);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Principal Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
