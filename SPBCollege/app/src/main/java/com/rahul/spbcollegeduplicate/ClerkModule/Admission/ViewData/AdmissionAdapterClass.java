package com.rahul.spbcollegeduplicate.ClerkModule.Admission.ViewData;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rahul.spbcollegeduplicate.R;

import java.util.ArrayList;

public class AdmissionAdapterClass extends BaseAdapter {

    private Context context;
    private ArrayList<String> studentNameList;
    private ArrayList<String> studentAdmissionNoList;
    private ArrayList<String> studentPhotoList;
    private ArrayList<String> studentSTDList;
    private LayoutInflater layoutInflater;

    public AdmissionAdapterClass(Context context, ArrayList<String> studentNameList, ArrayList<String> studentAdmissionNoList, ArrayList<String> studentPhotoList, ArrayList<String> studentSTDList) {
        this.context = context;
        this.studentNameList = studentNameList;
        this.studentAdmissionNoList = studentAdmissionNoList;
        this.studentPhotoList = studentPhotoList;
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
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_admission, viewGroup, false);
            holder = new ViewHolder();
            holder.studentName = view.findViewById(R.id.tv_admission_list_student_name);
            holder.studentAdmissionNo = view.findViewById(R.id.tv_admission_list_student_add_no_value);
            holder.studentSTD = view.findViewById(R.id.tv_admission_list_student_stdNo);
            holder.studentPhoto = view.findViewById(R.id.student_photo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.studentName.setText(studentNameList.get(i));
        holder.studentAdmissionNo.setText(studentAdmissionNoList.get(i));
        holder.studentSTD.setText(studentSTDList.get(i));

        if (isNetworkAvailable(context)) {
            // Load student photo using Glide
            Glide.with(context)
                    .load(studentPhotoList.get(i))
                    .placeholder(R.drawable.profile_icon) // Placeholder image resource
                    .error(R.drawable.image_error) // Error image resource
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("Glide", "Error loading image: " + e.getMessage());
                            return false; // Return false to allow Glide to handle the error image
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.studentPhoto);
        } else {
            // Display a message to the user indicating no internet connection
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    static class ViewHolder {
        TextView studentName;
        TextView studentAdmissionNo;
        TextView studentSTD;
        ImageView studentPhoto;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
