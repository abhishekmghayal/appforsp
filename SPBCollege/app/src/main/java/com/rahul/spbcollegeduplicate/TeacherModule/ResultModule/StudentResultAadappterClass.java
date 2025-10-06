package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class StudentResultAadappterClass extends BaseAdapter {
    private Context context;
    private ArrayList<String> studentNameList;
    private ArrayList<String> generalRegisterNoList;
    private ArrayList<String> studentPerList;
    private LayoutInflater layoutInflater;

    public StudentResultAadappterClass(Context context, ArrayList<String> studentNameList, ArrayList<String> generalRegisterNoList, ArrayList<String> studentPerList, RelativeLayout relativeLayout) {
        this.context = context;
        this.studentNameList = studentNameList;
        this.generalRegisterNoList = generalRegisterNoList;
        this.studentPerList = studentPerList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= layoutInflater.inflate(R.layout.listview_studentresult,null);
        TextView studentName= convertView.findViewById(R.id.tv_result_list_student_name);
        TextView studentGenRegNo=convertView.findViewById(R.id.tv_result_list_student_birth_value);
        TextView studentPercentage=convertView.findViewById(R.id.tv_result_list_student_stdNo);
        studentName.setText(studentNameList.get(position));
        studentGenRegNo.setText(generalRegisterNoList.get(position));
        studentPercentage.setText(studentPerList.get(position));

        return convertView;
    }
}
