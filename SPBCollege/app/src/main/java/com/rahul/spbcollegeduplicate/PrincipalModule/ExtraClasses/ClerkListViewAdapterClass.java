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

public class ClerkListViewAdapterClass extends BaseAdapter {

    private Context context;
    private ArrayList<String> clerkNameList;
    private ArrayList<String> clerkIdList;
    private ArrayList<String> clerkMailList;
    private LayoutInflater layoutInflater;

    public ClerkListViewAdapterClass(Context context, ArrayList<String> clerkNameList, ArrayList<String> clerkMailList, ArrayList<String> clerkIdList,RelativeLayout relativeLayout) {
        this.context = context;
        this.clerkNameList = clerkNameList;
        this.clerkIdList = clerkIdList;
        this.clerkMailList = clerkMailList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return clerkNameList.size();
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

        TextView clerkName = convertView.findViewById(R.id.tv_list_staff_name);
        TextView clerkMail = convertView.findViewById(R.id.tv_list_staff_mail);
//        TextView clerkId = convertView.findViewById(R.id.tv_list_staff_id);


        clerkName.setText(clerkNameList.get(position));
        clerkMail.setText(clerkMailList.get(position));
        //clerkId.setText(clerkIdList.get(position));
        return convertView;
    }
}
