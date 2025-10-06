package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rahul.spbcollegeduplicate.R;

public class PrincipalViewServiceBookActivity extends AppCompatActivity {

    Button btn_principal_book,btn_clerk_book,btn_teacher_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_view_service_book);

        setTitle("Service Book");

        btn_principal_book = findViewById(R.id.btn_principal_view_service_principal);
        btn_clerk_book = findViewById(R.id.btn_principal_view_service_clerk);
        btn_teacher_book = findViewById(R.id.btn_principal_view_service_teacher);

        btn_principal_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalViewServiceBookActivity.this, PrincipalServiceBookActivity.class);
                startActivity(intent);
            }
        });

        btn_clerk_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalViewServiceBookActivity.this, ClerkServiceBookActivity.class);
                startActivity(intent);
            }
        });

        btn_teacher_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalViewServiceBookActivity.this, TeacherServiceBookActivity.class);
                startActivity(intent);
            }
        });
    }
}