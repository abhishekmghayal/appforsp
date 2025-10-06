package com.rahul.spbcollegeduplicate.ClerkModule.GernalRegister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class StudentGernalRegisterListAdapterClass extends BaseAdapter {

    private Context context;
    private ArrayList<String> studentNameList;
    private ArrayList<String> generalRegisterNoList;
    private ArrayList<String> studentIdList;
    private LayoutInflater layoutInflater;

    public StudentGernalRegisterListAdapterClass(Context context, ArrayList<String> studentNameList, ArrayList<String> generalRegisterNoList, ArrayList<String> studentIdList, RelativeLayout relativeLayout) {
        this.context = context;
        this.studentNameList = studentNameList;
        this.generalRegisterNoList = generalRegisterNoList;
        this.studentIdList = studentIdList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentNameList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.listview_genral_register, null);

        TextView studentName = view.findViewById(R.id.tv_list_student_name);
        TextView studentGeneralRegisterNo = view.findViewById(R.id.tv_list_student_gernal_No);
        TextView studentId = view.findViewById(R.id.tv_list_student_Id);

        studentName.setText(studentNameList.get(i));
        studentGeneralRegisterNo.setText(generalRegisterNoList.get(i));
        studentId.setText(studentIdList.get(i));

        return view;
    }
}
