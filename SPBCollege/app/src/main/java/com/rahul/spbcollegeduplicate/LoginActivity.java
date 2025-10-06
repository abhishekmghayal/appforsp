package com.rahul.spbcollegeduplicate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rahul.spbcollegeduplicate.ClerkModule.ClerkBottomFragment;
import com.rahul.spbcollegeduplicate.PrincipalModule.PrincipalBottomFragment;
import com.rahul.spbcollegeduplicate.TeacherModule.TeacherBottomFragment;

public class LoginActivity extends AppCompatActivity {
    Button btn_principal,btn_clerk,btn_teacher;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();



        btn_principal = findViewById(R.id.btn_principle);
        btn_clerk = findViewById(R.id.btn_clerk);
        btn_teacher = findViewById(R.id.btn_teacher);

        btn_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrincipalBottomFragment principle_bottom_fragment = new PrincipalBottomFragment();
                principle_bottom_fragment.show(getSupportFragmentManager(),principle_bottom_fragment.getTag());
            }
        });

        btn_clerk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClerkBottomFragment clerk_bottom_fragment = new ClerkBottomFragment();
                clerk_bottom_fragment.show(getSupportFragmentManager(),clerk_bottom_fragment.getTag());
            }
        });

        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TeacherBottomFragment teacher_bottom_fragment = new TeacherBottomFragment();
                teacher_bottom_fragment.show(getSupportFragmentManager(),teacher_bottom_fragment.getTag());
            }
        });
    }
}