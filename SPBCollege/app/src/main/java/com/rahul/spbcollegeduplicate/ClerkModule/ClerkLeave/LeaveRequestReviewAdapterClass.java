package com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahul.spbcollegeduplicate.R;

import java.util.List;

public class LeaveRequestReviewAdapterClass extends RecyclerView.Adapter<LeaveRequestReviewAdapterClass.LeaveRequestViewHolder> {

    private List<LeaveRequestReviewModelClass> leaveRequestReviewModelClassList;
    private OnItemClickListener onItemClickListener;

    public LeaveRequestReviewAdapterClass(List<LeaveRequestReviewModelClass> leaveRequests) {
        this.leaveRequestReviewModelClassList = leaveRequests;
    }

    // Define the interface for the item click listener
    public interface OnItemClickListener {
        void onItemClick(LeaveRequestReviewModelClass leaveRequest);
    }

    // Set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LeaveRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_clerk_leave_review, parent, false);
        return new LeaveRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestViewHolder holder, int position) {
        LeaveRequestReviewModelClass leaveRequest = leaveRequestReviewModelClassList.get(position);


        holder.clerkLeaveReviewStatus.setText(leaveRequest.getClerkLeaveReviewStatus());
        holder.clerkNameLeaveReview.setText(leaveRequest.getClerkNameLeaveReview());
        holder.clerkStartFromLeaveReview.setText(leaveRequest.getClerkStartFromLeaveReview());
        holder.clerkEndToLeaveReview.setText(leaveRequest.getClerkEndToLeaveReview());

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
        return leaveRequestReviewModelClassList.size();
    }

    public static class LeaveRequestViewHolder extends RecyclerView.ViewHolder {
        TextView clerkLeaveReviewStatus;
        TextView clerkNameLeaveReview;
        TextView clerkEndToLeaveReview;
        TextView clerkStartFromLeaveReview;

        public LeaveRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            clerkLeaveReviewStatus = itemView.findViewById(R.id.tv__llrc_Clerk_requst_status);
            clerkNameLeaveReview = itemView.findViewById(R.id.tv_llrc_Clerk_name);
            clerkStartFromLeaveReview = itemView.findViewById(R.id.tv_llrc_Clerk_fromDateleave);
            clerkEndToLeaveReview = itemView.findViewById(R.id.tv_llrc_Clerk_todateleave);
        }
    }
}
