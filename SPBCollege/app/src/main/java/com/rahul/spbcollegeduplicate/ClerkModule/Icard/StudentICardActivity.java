package com.rahul.spbcollegeduplicate.ClerkModule.Icard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rahul.spbcollegeduplicate.ClerkModule.FirebaseExtraClasses.AddStudentIcardDatabaseHelperClass;
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class StudentICardActivity extends AppCompatActivity {

    EditText et_studentNameForICard, et_studentDOBForICard, et_studentAcademicYearForICard,
            et_studentGeneralREGNoForICard, et_studentClassForICard, et_studentAddressForICard;
    Button btn_SubmitICard;
    FirebaseDatabase database;
    StorageReference storageRef;
    ImageView student_photo;
    Uri imageUri;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1999;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_student_icard);

        storageRef = FirebaseStorage.getInstance().getReference();

        setTitle("Create Student ICard");

        student_photo = findViewById(R.id.Icard_student_photo);
        et_studentNameForICard = findViewById(R.id.et__aS_idCard_Name);
        et_studentDOBForICard = findViewById(R.id.et__aS_idCard_DOB);
        et_studentAcademicYearForICard = findViewById(R.id.et__aS_idCard_acdYear);
        et_studentGeneralREGNoForICard = findViewById(R.id.et__aS_idCard_genralno);
        et_studentClassForICard = findViewById(R.id.et__aS_idCard_class);
        et_studentAddressForICard = findViewById(R.id.et__aS_idCard_adress);
        btn_SubmitICard = findViewById(R.id.btn_submit_card);

        et_studentDOBForICard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Calendar calendar=Calendar.getInstance();
            final  int year=calendar.get(Calendar.YEAR);
            final  int month=calendar.get(Calendar.MONTH);
            final  int day=calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(StudentICardActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            et_studentDOBForICard.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });



        btn_SubmitICard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_studentNameForICard.getText().toString().isEmpty()) {
                    et_studentNameForICard.setError("Please Enter Student Full Name");
                } else if (et_studentDOBForICard.getText().toString().isEmpty()) {
                    et_studentDOBForICard.setError("Please Enter Student DOB");
                } else if (et_studentAcademicYearForICard.getText().toString().isEmpty()) {
                    et_studentAcademicYearForICard.setError("Please Enter Student Academic Year");
                } else if (et_studentGeneralREGNoForICard.getText().toString().isEmpty()) {
                    et_studentGeneralREGNoForICard.setError("Please Enter Student General Register Number");
                } else if (et_studentClassForICard.getText().toString().isEmpty()) {
                    et_studentClassForICard.setError("Please Enter Student Standard");
                } else if (et_studentAddressForICard.getText().toString().isEmpty()) {
                    et_studentAddressForICard.setError("Please Enter Student Address");
                } else {
                    showConfirmationDialog();

                }
            }
        });

        student_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

    }


    private void openImageChooser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Open camera to capture image
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        break;
                    case 1:
                        // Open gallery to select image
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, GALLERY_REQUEST);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                student_photo.setImageBitmap(photo);
                imageUri = getImageUri(this, photo);
            } else if (requestCode == GALLERY_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    student_photo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (imageUri != null) {
                    uploadPhoto(imageUri);
                } else {
                    createOrUpdateStudentICard(null);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentICardActivity.this, "Submission cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void uploadPhoto(Uri imageUri) {
        StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString());

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Image download URL generated, save it to the database
                        String imageUrl = uri.toString();
                        createOrUpdateStudentICard(imageUrl);
                    }).addOnFailureListener(e -> {
                        // Handle failure to get download URL
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to get image download URL", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle unsuccessful image upload
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void createOrUpdateStudentICard(String imageUrl) {
        database = FirebaseDatabase.getInstance();

        String studentName = et_studentNameForICard.getText().toString();
        String studentDOB = et_studentDOBForICard.getText().toString();
        String academicYear = et_studentAcademicYearForICard.getText().toString();
        String generalREGNo = et_studentGeneralREGNoForICard.getText().toString();
        String studentClass = et_studentClassForICard.getText().toString();
        String studentAddress = et_studentAddressForICard.getText().toString();

        DatabaseReference standardRef = database.getReference("Student's_ICard_Record").child(studentClass);

        standardRef.orderByChild("studentFullName").equalTo(studentName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Duplicate record found, show the existing icard
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                AddStudentIcardDatabaseHelperClass existingIcard = snapshot.getValue(AddStudentIcardDatabaseHelperClass.class);
                                displayExistingIcard(existingIcard);
                                Toast.makeText(StudentICardActivity.this, "Identity card For This Student for Same Class Already Exists.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            String studentKey = standardRef.push().getKey();
                            AddStudentIcardDatabaseHelperClass newIcard = new AddStudentIcardDatabaseHelperClass(studentName, studentDOB, academicYear, generalREGNo, studentClass, studentAddress,imageUrl);

                            standardRef.child(studentKey).setValue(newIcard);
                            Toast.makeText(StudentICardActivity.this, "Student ID Card Generated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentICardActivity.this, RecentlyGeneratedICardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error, if any
                        Toast.makeText(StudentICardActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void displayExistingIcard(AddStudentIcardDatabaseHelperClass existingIcard) {

    }
}
