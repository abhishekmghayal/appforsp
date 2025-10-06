package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rahul.spbcollegeduplicate.R;
import com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.EightClass.Eight_Class_Result;
import com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.NineClass.Nine_Class_Result;

public class Teacher_student_Result_class_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_result_class);

        setTitle("Result ");

    }
    public void nineClass(View v){
        Intent i=new Intent(Teacher_student_Result_class_Activity.this, Nine_Class_Result.class);
        startActivity(i);

    }
    public  void eightClass(View v)
    {
        Intent i=new Intent(Teacher_student_Result_class_Activity.this, Eight_Class_Result.class);
        startActivity(i);

    }
}