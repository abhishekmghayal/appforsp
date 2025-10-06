package com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class TeacherListViewAdapterClass extends BaseAdapter {

    private Context context;
    private ArrayList<String> teacherNameList;
    private ArrayList<String> teacherIdList;
    private ArrayList<String> teacherMailList;
    private LayoutInflater layoutInflater;

    public TeacherListViewAdapterClass(Context context, ArrayList<String> teacherNameList, ArrayList<String> teacherMailList, ArrayList<String> teacherIdList, RelativeLayout relativeLayout) {
        this.context = context;
        this.teacherNameList = teacherNameList;
        this.teacherIdList = teacherIdList;
        this.teacherMailList = teacherMailList;
        this.layoutInflater = LayoutInflater.from(context);  // Correct assignment for layoutInflater
    }

    @Override
    public int getCount() {
        return teacherNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_card_view, null);

        TextView teacherName = convertView.findViewById(R.id.tv_list_staff_name);
        TextView teacherMail = convertView.findViewById(R.id.tv_list_staff_mail);
       // TextView teacherId = convertView.findViewById(R.id.tv_list_staff_id);


        teacherName.setText(teacherNameList.get(position));
        teacherMail.setText(teacherMailList.get(position));
        //teacherId.setText(teacherIdList.get(position));


        return convertView;
    }
}
