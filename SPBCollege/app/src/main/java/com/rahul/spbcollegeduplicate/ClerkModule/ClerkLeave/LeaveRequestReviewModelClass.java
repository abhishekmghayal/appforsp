package com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave;

public class LeaveRequestReviewModelClass {
    private String clerkLeaveReviewStatus;
    private String clerkNameLeaveReview;
    private String clerkStartFromLeaveReview;
    private String clerkEndToLeaveReview;


    public LeaveRequestReviewModelClass() {
    }

    public LeaveRequestReviewModelClass(String clerkLeaveReviewStatus, String clerkNameLeaveReview, String clerkStartFromLeaveReview, String clerkEndToLeaveReview) {
        this.clerkLeaveReviewStatus = clerkLeaveReviewStatus;
        this.clerkNameLeaveReview = clerkNameLeaveReview;
        this.clerkStartFromLeaveReview = clerkStartFromLeaveReview;
        this.clerkEndToLeaveReview = clerkEndToLeaveReview;
    }

    public String getClerkLeaveReviewStatus() {
        return clerkLeaveReviewStatus;
    }

    public void setClerkLeaveReviewStatus(String clerkLeaveReviewStatus) {
        this.clerkLeaveReviewStatus = clerkLeaveReviewStatus;
    }

    public String getClerkNameLeaveReview() {
        return clerkNameLeaveReview;
    }

    public void setClerkNameLeaveReview(String clerkNameLeaveReview) {
        this.clerkNameLeaveReview = clerkNameLeaveReview;
    }

    public String getClerkStartFromLeaveReview() {
        return clerkStartFromLeaveReview;
    }

    public void setClerkStartFromLeaveReview(String clerkStartFromLeaveReview) {
        this.clerkStartFromLeaveReview = clerkStartFromLeaveReview;
    }

    public String getClerkEndToLeaveReview() {
        return clerkEndToLeaveReview;
    }

    public void setClerkEndToLeaveReview(String clerkEndToLeaveReview) {
        this.clerkEndToLeaveReview = clerkEndToLeaveReview;
    }
}
