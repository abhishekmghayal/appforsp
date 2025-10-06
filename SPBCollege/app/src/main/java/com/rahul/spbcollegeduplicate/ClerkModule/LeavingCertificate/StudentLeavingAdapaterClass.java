package com.rahul.spbcollegeduplicate.ClerkModule.LeavingCertificate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class StudentLeavingAdapaterClass extends BaseAdapter {
    private Context context;
    private ArrayList<String> studentNameList;
    private ArrayList<String> generalRegisterNoList;
    private ArrayList<String> studentSTDList;
    private LayoutInflater layoutInflater;

    public StudentLeavingAdapaterClass(Context context, ArrayList<String> studentNameList, ArrayList<String> generalRegisterNoList, ArrayList<String> studentSTDList, RelativeLayout relativeLayout) {
        this.context = context;
        this.studentNameList = studentNameList;
        this.generalRegisterNoList = generalRegisterNoList;
        this.studentSTDList = studentSTDList;
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
        view = layoutInflater.inflate(R.layout.listview_leavingcertificate, null);

        TextView studentName = view.findViewById(R.id.tv_list_leaving_student_name);
        TextView studentGeneralRegisterNo = view.findViewById(R.id.tv_list_student_gernal_No);
        TextView studentId = view.findViewById(R.id.tv_list_student_stdNo);

        studentName.setText(studentNameList.get(i));
        studentGeneralRegisterNo.setText(generalRegisterNoList.get(i));
        studentId.setText(studentSTDList.get(i));

        return view;
    }
}
