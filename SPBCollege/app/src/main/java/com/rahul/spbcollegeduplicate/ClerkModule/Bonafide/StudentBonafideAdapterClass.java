package com.rahul.spbcollegeduplicate.ClerkModule.Bonafide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class StudentBonafideAdapterClass extends BaseAdapter {
    Context context;
    private ArrayList<String> bonafideStudNameList;
    private ArrayList<String> bonafideStudLearningYearList;
    private ArrayList<String> bonafideStudSTDList;
    private RelativeLayout relativeLayout;
    private LayoutInflater layoutInflater;

    public StudentBonafideAdapterClass(Context context, ArrayList<String> bonafideStudNameList, ArrayList<String> bonafideStudLearningYearList, ArrayList<String> bonafideStudSTDList, RelativeLayout relativeLayout) {
        this.context = context;
        this.bonafideStudNameList = bonafideStudNameList;
        this.bonafideStudLearningYearList = bonafideStudLearningYearList;
        this.bonafideStudSTDList = bonafideStudSTDList;
        this.relativeLayout = relativeLayout;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bonafideStudNameList.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.listview_bonafide, null);
        TextView studentName = view.findViewById(R.id.tv_bonafide_list_student_name);
        TextView studentDOB = view.findViewById(R.id.tv_bonafide_list_student_birth_value);
        TextView studentSTD = view.findViewById(R.id.tv_bonafide_list_student_stdNo);

        studentName.setText(bonafideStudNameList.get(position));
        studentDOB.setText(bonafideStudLearningYearList.get(position));
        studentSTD.setText(bonafideStudSTDList.get(position));
        return view;
    }
}
