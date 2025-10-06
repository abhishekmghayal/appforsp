package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahul.spbcollegeduplicate.LoginActivity;
import com.rahul.spbcollegeduplicate.PrincipalModule.FireBaseExtraClasses.RegisterPrincipalHelperClass;
import com.rahul.spbcollegeduplicate.R;

public class RegisterPrincipalActivity extends AppCompatActivity {

    EditText et_reg_principal_name, et_reg_principal_mo_no, et_reg_principal_mail,
            et_reg_principal_aadhar_no, et_reg_principal_pan_no, et_reg_principal_username, et_reg_principal_password;
    Button btn_principal_reg;
    CheckBox chk_show_password;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_principal);

        setTitle("Principal Registration");

        et_reg_principal_name = findViewById(R.id.reg_principal_et_principal_name);
        et_reg_principal_mo_no = findViewById(R.id.reg_principal_et_principal_mo_no);
        et_reg_principal_mail = findViewById(R.id.reg_principal_et_principal_email);
        et_reg_principal_aadhar_no = findViewById(R.id.reg_principal_et_principal_aadhar_no);
        et_reg_principal_pan_no = findViewById(R.id.reg_principal_et_principal_pan_no);
        et_reg_principal_username = findViewById(R.id.reg_principal_et_principal_username);
        et_reg_principal_password = findViewById(R.id.reg_principal_et_principal_password);
        btn_principal_reg = findViewById(R.id.reg_principal_btn_register);
        chk_show_password = findViewById(R.id.reg_principal_chk_show_pass);

        chk_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_show_password.isChecked()) {
                    et_reg_principal_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_reg_principal_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_principal_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPrincipal();
                Intent intent = new Intent(RegisterPrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerPrincipal() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("principal's_profile");

        String fullName = et_reg_principal_name.getText().toString();
        String mobileNo = et_reg_principal_mo_no.getText().toString();
        String emailId = et_reg_principal_mail.getText().toString();
        String aadharNo = et_reg_principal_aadhar_no.getText().toString();
        String panNo = et_reg_principal_pan_no.getText().toString();
        String username = et_reg_principal_username.getText().toString();
        String password = et_reg_principal_password.getText().toString();

        // Use a unique identifier data will store on that basis
        RegisterPrincipalHelperClass registerPrincipalHelperClass = new RegisterPrincipalHelperClass(fullName,mobileNo,emailId,aadharNo,panNo,username,password);
        reference.child(username).setValue(registerPrincipalHelperClass);

        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterPrincipalActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
