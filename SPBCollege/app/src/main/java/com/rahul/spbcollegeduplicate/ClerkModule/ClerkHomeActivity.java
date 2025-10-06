package com.rahul.spbcollegeduplicate.ClerkModule;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.ClerkModule.Admission.ViewData.ActivityViewRecentlyAdmissionForm;
import com.rahul.spbcollegeduplicate.ClerkModule.Bonafide.RecentlyGeneratedBonafideActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave.ClerkApplyLeaveRequestActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.ClerkLeave.ClerkLeaveReviewActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.ClerkResult.StudentResultActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.GernalRegister.GeneralRegisterActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.Icard.RecentlyGeneratedICardActivity;
import com.rahul.spbcollegeduplicate.ClerkModule.LeavingCertificate.LeavingCertificateActivity;
import com.rahul.spbcollegeduplicate.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClerkHomeActivity extends AppCompatActivity {
    CardView card_clerk_admission, card_clerk_Bonafide, card_clerk_Genral_Register, card_clerk_Leaving_Certificate, card_clerk_Student_id_card, card_clerk_Student_Result, allCardBackground;
    TextView tv_clerk_name;
    Button btn_update_clerk_profile;
    EditText et_clerk_full_name, et_clerk_id, et_clerk_mail, et_clerk_mobile_no, et_clerk_aadhar_no, et_clerk_pan_no, et_clerk_username;
    ImageView iv_resize_card, clerkImg;
    String clerkUsername, clerkId;
    private boolean isCardDown = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView resultTextView;

    // Predefined coordinates and radius
    private static final double DESTINATION_LATITUDE = 37.7749;
    private static final double DESTINATION_LONGITUDE = -122.4194;
    private static final float RADIUS = 5000; // in meters
    private String loginTime;
    private String logoutTime;
    Boolean aBoolean = true;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; //milliseconds
    private long lastClickTime = 0;
    FloatingActionButton flb_leavereq, flb_previewreq, flb_trackattendece;

    private static final String PREF_NAME = "ClerkPrefs";
    private static final String KEY_LOGIN_STATUS = "isClerkLoggedIn";
    private TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_home);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isClerkLoggedIn = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false);

        if (!isClerkLoggedIn) {
            // Clerk is not logged in, navigate to the login activity
            navigateToLoginActivity();
            return;
        }

        captureLoginTime();

        Intent intent = getIntent();
        if (intent != null) {
            clerkUsername = intent.getStringExtra("clerkLoginUsername");
            retrieveClerkData(clerkUsername);

        }

        //resultTextView = findViewById(R.id.result_text_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permission
        requestLocationPermission();

        card_clerk_admission = findViewById(R.id.card_admission_form);
        card_clerk_Bonafide = findViewById(R.id.card_Bonafide);
        card_clerk_Genral_Register = findViewById(R.id.card_Gernal_Register);
        card_clerk_Leaving_Certificate = findViewById(R.id.card_leaving_certificate);
        card_clerk_Student_id_card = findViewById(R.id.card_student_icard);
        card_clerk_Student_Result = findViewById(R.id.card_Student_Result);
        allCardBackground = findViewById(R.id.all_card_background);
        iv_resize_card = findViewById(R.id.card_resize);
        flb_leavereq = findViewById(R.id.actbtn_add);
        flb_previewreq = findViewById(R.id.actbtn_review);

        clerkImg = findViewById(R.id.clerk_img);
        tv_clerk_name = findViewById(R.id.tv_clerk_name);
        et_clerk_id = findViewById(R.id.profile_clerk_clerk_id);
        et_clerk_full_name = findViewById(R.id.profile_clerk_clerk_name);
        et_clerk_mobile_no = findViewById(R.id.profile_clerk_clerk_mobile_no);
        et_clerk_mail = findViewById(R.id.profile_clerk_clerk_email_id);
        et_clerk_aadhar_no = findViewById(R.id.profile_clerk_clerk_aadhar_no);
        et_clerk_pan_no = findViewById(R.id.profile_clerk_clerk_pan_no);
        et_clerk_username = findViewById(R.id.profile_clerk_clerk_username);
        btn_update_clerk_profile = findViewById(R.id.profile_clerk_update_clerk_btn);


        et_clerk_full_name.setEnabled(false);
        et_clerk_id.setEnabled(false);
        et_clerk_mobile_no.setEnabled(false);
        et_clerk_mail.setEnabled(false);
        et_clerk_aadhar_no.setEnabled(false);
        et_clerk_pan_no.setEnabled(false);
        et_clerk_username.setEnabled(false);




        btn_update_clerk_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClerkProfile(view);
            }
        });


        iv_resize_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardDown) {
                    animateCardViewDown(allCardBackground);
                    isCardDown = true;
                } else {
                    animateCardViewUp(allCardBackground);
                    isCardDown = false;
                }
            }
        });

        flb_leavereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // Double click action
                    Intent i = new Intent(ClerkHomeActivity.this, ClerkApplyLeaveRequestActivity.class);
                    startActivity(i);

                } else {
                    // Single click action
                    lastClickTime = clickTime;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (System.currentTimeMillis() - lastClickTime >= DOUBLE_CLICK_TIME_DELTA) {
                                // Single click action
                                if (aBoolean) {
                                    flb_previewreq.show();
                                    aBoolean = false;
                                } else {
                                    flb_previewreq.hide();
                                    aBoolean = true;
                                }
                            }
                        }
                    }, DOUBLE_CLICK_TIME_DELTA);
                }
            }
        });

        flb_previewreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clerkLeaveReviewIntent();
            }
        });


        card_clerk_admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, ActivityViewRecentlyAdmissionForm.class);
                startActivity(i);

            }
        });


        card_clerk_Student_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, RecentlyGeneratedICardActivity.class);
                startActivity(i);
            }
        });

        card_clerk_Bonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, RecentlyGeneratedBonafideActivity.class);
                startActivity(i);
            }
        });

        card_clerk_Leaving_Certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, LeavingCertificateActivity.class);
                startActivity(i);
            }
        });

        card_clerk_Genral_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, GeneralRegisterActivity.class);
                startActivity(i);
            }
        });

        card_clerk_Student_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClerkHomeActivity.this, StudentResultActivity.class);
                startActivity(i);
            }
        });
    }


    private void retrieveClerkData(String clerkUsername) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile");
        Query query = reference.orderByChild("clerkUsername").equalTo(clerkUsername);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        // Retrieve data here
                        String clerkFullName = dataSnapshot.child("clerkFullName").getValue(String.class);
                        clerkId = dataSnapshot.child("clerkId").getValue(String.class);
                        String clerkEmail = dataSnapshot.child("clerkEMailId").getValue(String.class);
                        String clerkMobileNo = dataSnapshot.child("clerkMobileNo").getValue(String.class);
                        String clerkAadharNo = dataSnapshot.child("clerkAadharNo").getValue(String.class);
                        String clerkPanNo = dataSnapshot.child("clerkPanNo").getValue(String.class);
                        String clerkUsername = dataSnapshot.child("clerkUsername").getValue(String.class);

                        // Update UI or do something with the retrieved data
                        tv_clerk_name.setText(clerkFullName);
                        et_clerk_full_name.setText(clerkFullName);
                        et_clerk_id.setText(clerkId);
                        et_clerk_mail.setText(clerkEmail);
                        et_clerk_mobile_no.setText(clerkMobileNo);
                        et_clerk_aadhar_no.setText(clerkAadharNo);
                        et_clerk_pan_no.setText(clerkPanNo);
                        et_clerk_username.setText(clerkUsername);
                    }
                } else {
                    Toast.makeText(ClerkHomeActivity.this, "Clerk Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClerkHomeActivity.this, "Error retrieving data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clerkLeaveReviewIntent() {
        Intent i = new Intent(ClerkHomeActivity.this, ClerkLeaveReviewActivity.class);
        i.putExtra("clerkId", clerkId);
        startActivity(i);
    }

    public void updateClerkProfile(View view) {
        // Enable EditText fields for editing
        et_clerk_full_name.setEnabled(true);
        et_clerk_id.setEnabled(true);
        et_clerk_mail.setEnabled(true);
        et_clerk_mobile_no.setEnabled(true);
        et_clerk_aadhar_no.setEnabled(true);
        et_clerk_pan_no.setEnabled(true);
        et_clerk_username.setEnabled(true);

        // Change the button text to "Save Changes"
        btn_update_clerk_profile.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        btn_update_clerk_profile.setOnClickListener(v -> {
            saveChangesToFirebase();
        });
    }

    private void saveChangesToFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clerk's_Profile");
        Query query = reference.orderByChild("clerkUsername").equalTo(clerkUsername);

        // Retrieve updated data from EditText fields
        String updatedClerkFullName = et_clerk_full_name.getText().toString();
        String updatedClerkId = et_clerk_id.getText().toString();
        String updatedClerkEmail = et_clerk_mail.getText().toString();
        String updatedClerkMobileNo = et_clerk_mobile_no.getText().toString();
        String updatedClerkAadharNo = et_clerk_aadhar_no.getText().toString();
        String updatedClerkPanNo = et_clerk_pan_no.getText().toString();
        String updatedClerkUsername = et_clerk_username.getText().toString();

        // Update the clerk data in the Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference clerkReference = snapshot.getRef();
                        clerkReference.child("clerkFullName").setValue(updatedClerkFullName);
                        clerkReference.child("clerkId").setValue(updatedClerkId);
                        clerkReference.child("clerkEMailId").setValue(updatedClerkEmail);
                        clerkReference.child("clerkMobileNo").setValue(updatedClerkMobileNo);
                        clerkReference.child("clerkAadharNo").setValue(updatedClerkAadharNo);
                        clerkReference.child("clerkPanNo").setValue(updatedClerkPanNo);
                        clerkReference.child("clerkUsername").setValue(updatedClerkUsername);
                    }

                    // Notify the user that changes have been saved
                    Toast.makeText(ClerkHomeActivity.this, "Clerk data updated successfully", Toast.LENGTH_SHORT).show();

                    // Disable EditText fields for editing
                    et_clerk_full_name.setEnabled(false);
                    et_clerk_id.setEnabled(false);
                    et_clerk_mail.setEnabled(false);
                    et_clerk_mobile_no.setEnabled(false);
                    et_clerk_aadhar_no.setEnabled(false);
                    et_clerk_pan_no.setEnabled(false);
                    et_clerk_username.setEnabled(false);

                    // Change the button text back to "Update"
                    btn_update_clerk_profile.setText("Update");

                    // Set the original click listener for the "Update" button
                    btn_update_clerk_profile.setOnClickListener(v -> {
                        updateClerkProfile(v);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ClerkHomeActivity.this, "Error updating data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void animateCardViewDown(CardView cardView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                cardView,
                "translationY",
                cardView.getTranslationY() + getResources().getDimension(R.dimen.translation_distance)
        );

        animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animator.start();
    }

    private void animateCardViewUp(CardView cardView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                cardView,
                "translationY",
                0f
        );

        animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animator.start();
    }


    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            // You can directly start requesting location updates here
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get location
                getLocation();
            } else {
                // Permission denied
                // Handle accordingly, show a message or disable functionality
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        // Use latitude and longitude
                        float[] results = new float[1];
                        Location.distanceBetween(latitude, longitude, DESTINATION_LATITUDE, DESTINATION_LONGITUDE, results);
                        float distanceInMeters = results[0];
                        if (distanceInMeters <= RADIUS) {
                            // User is within the radius
                            captureLoginTime();
                            resultTextView.setText("You are within the radius!");
                        } else {
                            resultTextView.setText("You are outside the radius.");
                        }
                    } else {
                        // Location is null, handle accordingly
                        Toast.makeText(ClerkHomeActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, e -> {
                    // Handle failure, such as permission denied or location services disabled
                    Toast.makeText(ClerkHomeActivity.this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    public void captureLoginTime() {
        // Capture the current timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String loginTime = sdf.format(new Date());
        // Store or use the login time as needed, e.g., save to SharedPreferences, database, etc.
        Toast.makeText(this, "Login time: " + loginTime, Toast.LENGTH_SHORT).show();
    }

    public void captureLogoutTime() {
        // Capture the current timestamp as logout time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        logoutTime = sdf.format(new Date());
        // Store or use the logout time as needed, e.g., save to SharedPreferences, database, etc.
        Toast.makeText(this, "Logout time: " + logoutTime, Toast.LENGTH_SHORT).show();
    }


    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, ClerkBottomFragment.class);
        startActivity(intent);
        // Finish the current activity to prevent the user from coming back to the home screen using the back button
        finish();
    }

}