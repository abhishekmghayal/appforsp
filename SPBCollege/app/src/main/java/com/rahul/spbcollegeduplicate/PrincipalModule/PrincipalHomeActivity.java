package com.rahul.spbcollegeduplicate.PrincipalModule;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.spbcollegeduplicate.R;

public class PrincipalHomeActivity extends AppCompatActivity {

    CardView cd_manage_clerk, cd_manage_teacher, cd_staff_attendance, cd_leave_request, allCardBackground;
    TextView tv_principal_name;
    Button btn_update_principal_profile;
    EditText et_principal_full_name, et_principal_mail, et_principal_mobile_no, et_principal_aadhar_no, et_principal_pan_no, et_principal_username;
    ImageView iv_resize_card, principalImg;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String principalUsername;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private boolean isCardDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(PrincipalHomeActivity.this);
        editor = preferences.edit();

        Intent intent = getIntent();
        if (intent != null) {
            principalUsername = intent.getStringExtra("principalLoginUsername");
            retrievePrincipalData(principalUsername);
        }

        cd_manage_clerk = findViewById(R.id.card_manage_clerk);
        cd_manage_teacher = findViewById(R.id.card_manage_teacher);
        cd_staff_attendance = findViewById(R.id.card_staff_attendance);
        cd_leave_request = findViewById(R.id.card_leave_request);
        allCardBackground = findViewById(R.id.all_card_background);
        iv_resize_card = findViewById(R.id.card_resize);

       // principalImg = findViewById(R.id.pricipal_img);
        tv_principal_name = findViewById(R.id.tv_principal_name);
        et_principal_full_name = findViewById(R.id.view_principal_principal_name);
        et_principal_mobile_no = findViewById(R.id.view_principal_principal_mobile_no);
        et_principal_mail = findViewById(R.id.view_principal_principal_email_id);
        et_principal_aadhar_no = findViewById(R.id.view_principal_principal_aadhar_no);
        et_principal_pan_no = findViewById(R.id.view_principal_principal_pan_no);
        et_principal_username = findViewById(R.id.view_principal_principal_username);
        btn_update_principal_profile = findViewById(R.id.view_principal_update_principal_btn);
        principalImg = findViewById(R.id.principal_photo_profile);


        et_principal_full_name.setEnabled(false);
        et_principal_mobile_no.setEnabled(false);
        et_principal_mail.setEnabled(false);
        et_principal_aadhar_no.setEnabled(false);
        et_principal_pan_no.setEnabled(false);
        et_principal_username.setEnabled(false);

        btn_update_principal_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePrincipalProfile(view);
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




        cd_manage_clerk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalHomeActivity.this, ManageClerkActivity.class);
                startActivity(intent);
            }
        });

        cd_manage_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalHomeActivity.this, ManageTeacherActivity.class);
                startActivity(intent);
            }
        });

        cd_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalHomeActivity.this, LeaveRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission is already granted, start the camera activity
            startCameraActivity();
        }
    }

    private void startCameraActivity() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Set the selected image to the ImageView
            principalImg.setImageURI(uri);
            // Upload the image to Firebase Storage
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Set the captured image to the ImageView
            principalImg.setImageBitmap(imageBitmap);

        }
    }

    private void retrievePrincipalData(String principalUsername) {
        DatabaseReference principalReference = FirebaseDatabase.getInstance().getReference("principal's_profile").child(principalUsername);

        principalReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String principalFullName = snapshot.child("fullName").getValue(String.class);
                    String principalEmail = snapshot.child("eMailId").getValue(String.class);
                    String principalMobileNo = snapshot.child("mobileNo").getValue(String.class);
                    String principalAadharNo = snapshot.child("addharNo").getValue(String.class);
                    String principalPanNo = snapshot.child("panNo").getValue(String.class);
                    String principalUsername = snapshot.child("username").getValue(String.class);

                    tv_principal_name.setText(principalFullName);
                    et_principal_full_name.setText(principalFullName);
                    et_principal_mail.setText(principalEmail);
                    et_principal_mobile_no.setText(principalMobileNo);
                    et_principal_aadhar_no.setText(principalAadharNo);
                    et_principal_pan_no.setText(principalPanNo);
                    et_principal_username.setText(principalUsername);
                } else {
                    Toast.makeText(PrincipalHomeActivity.this, "Principal Data Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PrincipalHomeActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePrincipalProfile(View view) {
        // Enable EditText fields for editing
        et_principal_full_name.setEnabled(true);
        et_principal_mail.setEnabled(true);
        et_principal_mobile_no.setEnabled(true);
        et_principal_aadhar_no.setEnabled(true);
        et_principal_pan_no.setEnabled(true);
        et_principal_username.setEnabled(true);

        // Change the button text to "Save Changes"
        btn_update_principal_profile.setText("Save Changes");

        principalImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSourceDialog();
            }
        });

        // Set a new click listener for the "Save Changes" button
        btn_update_principal_profile.setOnClickListener(v -> {
            saveChangesToFirebase();
        });
    }
    public void showImageSourceDialog() {
        // Check if the button text is "Save Changes"
        if (btn_update_principal_profile.getText().toString().equals("Save Changes")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Image Source");
            builder.setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            dispatchTakePictureIntent();
                            break;
                        case 1:
                            pickImageFromGallery();
                            break;
                    }
                }
            });
            builder.show();
        }
    }


    private void saveChangesToFirebase() {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("principal's_profile").child(principalUsername);

        // Retrieve updated data from EditText fields
        String updatedClerkFullName = et_principal_full_name.getText().toString();
        String updatedClerkEmail = et_principal_mail.getText().toString();
        String updatedClerkMobileNo = et_principal_mobile_no.getText().toString();
        String updatedClerkAadharNo = et_principal_aadhar_no.getText().toString();
        String updatedClerkPanNo = et_principal_pan_no.getText().toString();
        String updatedClerkUsername = et_principal_username.getText().toString();

        // Update the clerk data in the Firebase Realtime Database
        clerkReference.child("fullName").setValue(updatedClerkFullName);
        clerkReference.child("eMailId").setValue(updatedClerkEmail);
        clerkReference.child("mobileNo").setValue(updatedClerkMobileNo);
        clerkReference.child("addharNo").setValue(updatedClerkAadharNo);
        clerkReference.child("panNo").setValue(updatedClerkPanNo);
        clerkReference.child("username").setValue(updatedClerkUsername);

        // Notify the user that changes have been saved
        Toast.makeText(PrincipalHomeActivity.this, "Principal data updated successfully", Toast.LENGTH_SHORT).show();

        // Disable EditText fields for editing
        et_principal_full_name.setEnabled(false);
        et_principal_mail.setEnabled(false);
        et_principal_mobile_no.setEnabled(false);
        et_principal_aadhar_no.setEnabled(false);
        et_principal_pan_no.setEnabled(false);
        et_principal_username.setEnabled(false);

        // Change the button text back to "Update"
        btn_update_principal_profile.setText("Update");
        // Set the original click listener for the "Update" button
        btn_update_principal_profile.setOnClickListener(v -> {
            updatePrincipalProfile(v);
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
}