package com.rahul.spbcollegeduplicate.ClerkModule.Icard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rahul.spbcollegeduplicate.R;

import java.util.List;

public class StudentIcardAdapterClass extends RecyclerView.Adapter<StudentIcardAdapterClass.LeaveRequestViewHolder> {

    private List<StudentICardModelClass> studentICardModelClassList;
    private OnItemClickListener onItemClickListener;

    public StudentIcardAdapterClass(List<StudentICardModelClass> leaveRequests) {
        this.studentICardModelClassList = leaveRequests;
    }

    public interface OnItemClickListener {
        void onItemClick(StudentICardModelClass leaveRequest);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LeaveRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_icard, parent, false);
        return new LeaveRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestViewHolder holder, int position) {
        StudentICardModelClass leaveRequest = studentICardModelClassList.get(position);

        holder.studentName.setText(leaveRequest.getStudentFullName());
        holder.studentGeneralRegNo.setText(leaveRequest.getStudentGeneralRegisterNumber());
        holder.studentSTD.setText(leaveRequest.getStudentStandard());
        holder.studentAcademicYear.setText(leaveRequest.getStudentAcademicYear());

        // Load the student image using Glide
        Context context = holder.itemView.getContext();
        Glide.with(context)
                .load(leaveRequest.getImageUrl()) // Assuming you have a method to get the image URL from the model class
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)) // To skip memory cache and avoid stale images
                .placeholder(R.drawable.profile_icon) // Placeholder image while loading
                .error(R.drawable.image_error) // Error image if loading fails
                .into(holder.studentImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(leaveRequest);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentICardModelClassList.size();
    }

    public static class LeaveRequestViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView studentGeneralRegNo;
        TextView studentSTD;
        TextView studentAcademicYear;
        ImageView studentImage;

        public LeaveRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.tv_icard_list_student_name);
            studentGeneralRegNo = itemView.findViewById(R.id.tv_icard_list_student_gernal_No);
            studentSTD = itemView.findViewById(R.id.tv_icard_list_student_stdNo);
            studentAcademicYear = itemView.findViewById(R.id.tv_icard_list_student_acdemsic_yearvalue);
            studentImage = itemView.findViewById(R.id.student_photo);
        }
    }
}
