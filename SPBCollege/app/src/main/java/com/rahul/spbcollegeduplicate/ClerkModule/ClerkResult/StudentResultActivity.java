package com.rahul.spbcollegeduplicate.ClerkModule.ClerkResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rahul.spbcollegeduplicate.ClerkModule.ClerkResult.EightClassClerk.EightClassVIew;
import com.rahul.spbcollegeduplicate.ClerkModule.ClerkResult.NineClassClerk.NineClassView;
import com.rahul.spbcollegeduplicate.R;

public class StudentResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_result_view);

        setTitle("Result");
    }
    public void eightClass(View v)
    {
        Intent i=new Intent(StudentResultActivity.this, EightClassVIew.class);
        startActivity(i);
    }
    public void nineClass(View v)
    {
        Intent i=new Intent(StudentResultActivity.this, NineClassView.class);
        startActivity(i);
    }

}