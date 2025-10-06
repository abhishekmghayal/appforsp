package com.rahul.spbcollegeduplicate.PrincipalModule.ExtraClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahul.spbcollegeduplicate.R;

import java.util.List;

public class LeaveRequestAdapterClass extends RecyclerView.Adapter<LeaveRequestAdapterClass.LeaveRequestViewHolder> {

    private List<LeaveRequestModelClass> leaveRequests;
    private OnItemClickListener onItemClickListener;

    public LeaveRequestAdapterClass(List<LeaveRequestModelClass> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    // Define the interface for the item click listener
    public interface OnItemClickListener {
        void onItemClick(LeaveRequestModelClass leaveRequest);
    }

    // Set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LeaveRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_leave_request_card_view, parent, false);
        return new LeaveRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestViewHolder holder, int position) {
        LeaveRequestModelClass leaveRequest = leaveRequests.get(position);

        // Bind leave request data to ViewHolder
        holder.staffIdTextView.setText("Staff Id : " + leaveRequest.getStaffId());
        holder.staffNameTextView.setText(leaveRequest.getStaffName());
        holder.staffLeaveFromTextView.setText("Leave From : " + leaveRequest.getStaffLeaveFrom());
        holder.staffLeaveToTextView.setText("Leave To : " + leaveRequest.getStaffLeaveTo());

        // Set click listener for the item
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
        return leaveRequests.size();
    }

    public static class LeaveRequestViewHolder extends RecyclerView.ViewHolder {
        TextView staffIdTextView;
        TextView staffNameTextView;
        TextView staffLeaveToTextView;
        TextView staffLeaveFromTextView;

        public LeaveRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            staffIdTextView = itemView.findViewById(R.id.tv_rcy_staff_id);
            staffNameTextView = itemView.findViewById(R.id.tv_rcy_staff_name);
            staffLeaveFromTextView = itemView.findViewById(R.id.tv_rcy_request_date_from);
            staffLeaveToTextView = itemView.findViewById(R.id.tv_rcy_request_date_to);
        }
    }
}
