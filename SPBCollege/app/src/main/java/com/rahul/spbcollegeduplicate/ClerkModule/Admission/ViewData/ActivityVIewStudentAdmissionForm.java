package com.rahul.spbcollegeduplicate.ClerkModule.Admission.ViewData;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ActivityVIewStudentAdmissionForm extends AppCompatActivity {

    EditText et_stud_add_class, et_stud_add_no, et_stud_add_date, et_stud_id, et_stud_UDI, et_stud_full_name, et_stud_mother_full_name,
            et_stud_cast, et_stud_religion, et_stud_birth_place, et_stud_DOB_no, et_stud_DOB_latter, et_stud_age,et_stud_gender, et_stud_mother_tung,
            et_stud_class_passed, et_stud_pass_grade, et_stud_last_school_name, et_stud_bank_name, et_stud_bank_IFSC, et_stud_bank_acco_no,
            et_stud_parent_full_name, et_parent_relation, et_stud_parent_minority,et_parent_economical, et_parent_temp_address, et_parent_perm_address, et_parent_telephone_no,
            et_parent_mo_no, et_parent_business, et_parent_last_income;
    Button btn_update,btn_delete;
    ImageButton ibtn_print;
    ImageView student_photo;
    String selectedAdmissionNo;
    ProgressDialog progressDialog;
    String admissionClass,admissionNo,admissiondate,studentUId,adharId,studentName,motherName,cast,religion,birthplace,birthDate,birthDateinLetter, studentAge,gender,mothertung,classPased,passedGrade,lastSchoolName,bankName,
            bankIFSC,bankAccNo,parentName,parentrelation,parentMinotrity,parentEconomical,preanttempaddress,parentpermentaddress,parenttelephoneno,preantmobileno,prentbussiness,prentlastyearincome;
    Drawable imageFromFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__student__admission__form);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait Loading");
        progressDialog.show();

        setTitle("View Admission Form");

        Intent intent = getIntent();
        if (intent != null) {
            selectedAdmissionNo = intent.getStringExtra("admissionNo");
            retrieveAdmissionData(selectedAdmissionNo);
        }

        et_stud_add_class = findViewById(R.id.et__fvsaf_admissionclass);
        et_stud_add_no = findViewById(R.id.et__fvsaf_admissionno);
        et_stud_add_date = findViewById(R.id.et__fvsaf_admissiondate);
        et_stud_id = findViewById(R.id.et__fvsaf_admissionsuid);
        et_stud_UDI = findViewById(R.id.et__fvsaf_admissionsadharno);
        et_stud_full_name = findViewById(R.id.et__fvsaf_admissionstdname);
        et_stud_mother_full_name = findViewById(R.id.et__fvsaf_admissionstdmothername);
        et_stud_cast = findViewById(R.id.et__fvsaf_admissionstdcast);
        et_stud_religion = findViewById(R.id.et__fvsaf_admissionstdreligion);
        et_stud_birth_place = findViewById(R.id.et__fvsaf_admissionstdbirthplace);
        et_stud_DOB_no = findViewById(R.id.et__fvsaf_admissionstdbirthdate);
        et_stud_DOB_latter = findViewById(R.id.et__fvsaf_admissionstdbirthdateinletter);
        et_stud_age = findViewById(R.id.et__fvsaf_admissionstdage);
        et_stud_gender = findViewById(R.id.et__fvsaf_admissionstdgender);
        et_stud_mother_tung = findViewById(R.id.et__fvsaf_admissionstdmothertung);
        et_stud_class_passed = findViewById(R.id.et__fvsaf_admissionstdclasspassed);
        et_stud_pass_grade = findViewById(R.id.et__fvsaf_admissionstdpassiedgrade);
        et_stud_last_school_name = findViewById(R.id.et__fvsaf_admissionstdlastschoolname);
        et_stud_bank_name = findViewById(R.id.et__fvsaf_admissionstdbankname);
        et_stud_bank_IFSC = findViewById(R.id.et__fvsaf_admissionstdbankifsc);
        et_stud_bank_acco_no = findViewById(R.id.et__fvsaf_admissionstdbankaccno);

        et_stud_parent_full_name = findViewById(R.id.et__fvpiaf_admissionprentname);
        et_parent_relation = findViewById(R.id.et__fvpiaf_admissionprentrelation);
        et_stud_parent_minority = findViewById(R.id.et__fvpiaf_admissionprentminority);
        et_parent_economical = findViewById(R.id.et__fvpiaf_admissionprenteconomical);
        et_parent_temp_address = findViewById(R.id.et__fvpiaf_admissionprenttempaddress);
        et_parent_perm_address = findViewById(R.id.et__fvpiaf_admissionprentperaddress);
        et_parent_telephone_no = findViewById(R.id.et__fvpiaf_admissionprentpernttelphoneno);
        et_parent_mo_no = findViewById(R.id.et__fvpiaf_admissionprentperntmobphoneno);
        et_parent_business = findViewById(R.id.et__fvpiaf_admissionprentperntbussiness);
        et_parent_last_income = findViewById(R.id.et__fvpiaf_admissionprentperntlastyearincome);
        student_photo = findViewById(R.id.student_photo);

        btn_update = findViewById(R.id.btn__fvsaf_update_admission);
        btn_delete = findViewById(R.id.btn__fvsaf_delete_admission);
        ibtn_print = findViewById(R.id.btn_fetch_from_gen_reg);


        student_photo.setClickable(false);
        et_stud_add_class.setEnabled(false);
        et_stud_add_no.setEnabled(false);
        et_stud_add_date.setEnabled(false);
        et_stud_id.setEnabled(false);
        et_stud_UDI.setEnabled(false);
        et_stud_full_name.setEnabled(false);
        et_stud_mother_full_name.setEnabled(false);
        et_stud_cast.setEnabled(false);
        et_stud_religion.setEnabled(false);
        et_stud_birth_place.setEnabled(false);
        et_stud_DOB_no.setEnabled(false);
        et_stud_DOB_latter.setEnabled(false);
        et_stud_age.setEnabled(false);
        et_stud_gender.setEnabled(false);
        et_stud_mother_tung.setEnabled(false);
        et_stud_class_passed.setEnabled(false);
        et_stud_pass_grade.setEnabled(false);
        et_stud_last_school_name.setEnabled(false);
        et_stud_bank_name.setEnabled(false);
        et_stud_bank_IFSC.setEnabled(false);
        et_stud_bank_acco_no.setEnabled(false);

        et_stud_parent_full_name.setEnabled(false);
        et_parent_relation.setEnabled(false);
        et_stud_parent_minority.setEnabled(false);
        et_parent_economical.setEnabled(false);
        et_parent_temp_address.setEnabled(false);
        et_parent_perm_address.setEnabled(false);
        et_parent_telephone_no.setEnabled(false);
        et_parent_mo_no.setEnabled(false);
        et_parent_business.setEnabled(false);
        et_parent_last_income.setEnabled(false);

        et_stud_add_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_stud_add_date);
                }
            }
        });

        et_stud_DOB_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_stud_DOB_no);
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogDelete();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialogUpdate(view);
            }
        });
        ibtn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });



    }
    private void showDatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityVIewStudentAdmissionForm.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showConfirmationDialogDelete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete Admission Form ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAdmissionData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showConfirmationDialogUpdate(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Update General Register Data ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateAdmissionForm(view);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void updateAdmissionForm(View view) {
        student_photo.setClickable(true);

        et_stud_add_class.setEnabled(true);
        et_stud_add_no.setEnabled(true);
        et_stud_add_date.setEnabled(true);
        et_stud_id.setEnabled(true);
        et_stud_UDI.setEnabled(true);
        et_stud_full_name.setEnabled(true);
        et_stud_mother_full_name.setEnabled(true);
        et_stud_cast.setEnabled(true);
        et_stud_religion.setEnabled(true);
        et_stud_birth_place.setEnabled(true);
        et_stud_DOB_no.setEnabled(true);
        et_stud_DOB_latter.setEnabled(true);
        et_stud_age.setEnabled(true);
        et_stud_gender.setEnabled(true);
        et_stud_mother_tung.setEnabled(true);
        et_stud_class_passed.setEnabled(true);
        et_stud_pass_grade.setEnabled(true);
        et_stud_last_school_name.setEnabled(true);
        et_stud_bank_name.setEnabled(true);
        et_stud_bank_IFSC.setEnabled(true);
        et_stud_bank_acco_no.setEnabled(true);

        et_stud_parent_full_name.setEnabled(true);
        et_parent_relation.setEnabled(true);
        et_stud_parent_minority.setEnabled(true);
        et_parent_economical.setEnabled(true);
        et_parent_temp_address.setEnabled(true);
        et_parent_perm_address.setEnabled(true);
        et_parent_telephone_no.setEnabled(true);
        et_parent_mo_no.setEnabled(true);
        et_parent_business.setEnabled(true);
        et_parent_last_income.setEnabled(true);


        // Change the button text to "Save Changes"
        btn_update.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        btn_update.setOnClickListener(v -> {
            showConfirmationDialog();

        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to save the changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveChangesToFirebase();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.show();
    }

    private void saveChangesToFirebase() {
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("admissionData").child(selectedAdmissionNo);

        ProgressDialog progressDialog = new ProgressDialog(ActivityVIewStudentAdmissionForm.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating Data");
        progressDialog.show();
        // Retrieve updated data from EditText fields
        String updatedStudAddDate = et_stud_add_date.getText().toString().trim();
        String updatedStudAddNo = et_stud_add_no.getText().toString().trim();
        String updatedStudAddClass = et_stud_add_class.getText().toString().trim();
        String updatedStudId = et_stud_id.getText().toString();
        String updatedStudUID = et_stud_UDI.getText().toString();
        String updatedStudFullName = et_stud_full_name.getText().toString();
        String updatedStudMotherFullName =et_stud_mother_full_name.getText().toString();
        String updatedStudCaste =et_stud_cast.getText().toString();
        String updatedStudMotherTung =et_stud_mother_tung.getText().toString();
        String updatedStudReligion =et_stud_religion.getText().toString();
        String updatedStudBirthPlace =et_stud_birth_place.getText().toString();
        String updatedStudDOB =et_stud_DOB_no.getText().toString();
        String updatedStudDOBLetter =et_stud_DOB_latter.getText().toString();
        String updatedStudAge =et_stud_age.getText().toString();
        String updatedStudGender =et_stud_gender.getText().toString();
        String updatedStudPassedClass =et_stud_class_passed.getText().toString();
        String updatedStudPassingGrade =et_stud_pass_grade.getText().toString();
        String updatedStudLastSchool =et_stud_last_school_name.getText().toString();
        String updatedStudBankNAme =et_stud_bank_name.getText().toString();
        String updatedStudBankIFSC = et_stud_bank_IFSC.getText().toString();
        String updatedStudBankAccoNo =et_stud_bank_acco_no.getText().toString();
        String updatedStudParentFullName =et_stud_parent_full_name.getText().toString();
        String updatedStudParentRelation =et_parent_relation.getText().toString();
        String updatedStudMinority =et_stud_parent_minority.getText().toString();
        String updatedStudEconomical =et_parent_economical.getText().toString();
        String updatedStudTempAddress =et_parent_temp_address.getText().toString();
        String updatedStudPermAddress =et_parent_perm_address.getText().toString();
        String updatedStudTeleNo =et_parent_telephone_no.getText().toString();
        String updatedStudMoNO =et_parent_mo_no.getText().toString();
        String updatedStudParentBusiness =et_parent_business.getText().toString();
        String updatedStudParentIncome = et_parent_last_income.getText().toString();



        studentRef.child("stud_admission_no").setValue(updatedStudAddNo);
        studentRef.child("stud_admission_date").setValue(updatedStudAddDate);
        studentRef.child("stud_admission_class").setValue(updatedStudAddClass);
        studentRef.child("student_id").setValue(updatedStudId);
        studentRef.child("student_UID").setValue(updatedStudUID);
        studentRef.child("student_full_name").setValue(updatedStudFullName);
        studentRef.child("student_mother_name").setValue(updatedStudMotherFullName);
        studentRef.child("student_caste").setValue(updatedStudCaste);
        studentRef.child("student_mother_tung").setValue(updatedStudMotherTung);
        studentRef.child("student_religion").setValue(updatedStudReligion);
        studentRef.child("student_birth_place").setValue(updatedStudBirthPlace);
        studentRef.child("student_DOB_No").setValue(updatedStudDOB);
        studentRef.child("student_DOB_Letter").setValue(updatedStudDOBLetter);
        studentRef.child("student_age").setValue(updatedStudAge);
        studentRef.child("student_gender").setValue(updatedStudGender);
        studentRef.child("passed_class").setValue(updatedStudPassedClass);
        studentRef.child("passed_grad").setValue(updatedStudPassingGrade);
        studentRef.child("last_school_name").setValue(updatedStudLastSchool);
        studentRef.child("bank_name").setValue(updatedStudBankNAme);
        studentRef.child("bank_IFSC_No").setValue(updatedStudBankIFSC);
        studentRef.child("bank_account_no").setValue(updatedStudBankAccoNo);
        studentRef.child("parent_full_name").setValue(updatedStudParentFullName);
        studentRef.child("parent_relation").setValue(updatedStudParentRelation);
        studentRef.child("minority_caste").setValue(updatedStudMinority);
        studentRef.child("economical_weaker").setValue(updatedStudEconomical);
        studentRef.child("parent_temp_address").setValue(updatedStudTempAddress);
        studentRef.child("parent_perm_address").setValue(updatedStudPermAddress);
        studentRef.child("parent_telephone_no").setValue(updatedStudTeleNo);
        studentRef.child("parent_mo_no").setValue(updatedStudMoNO);
        studentRef.child("parent_business").setValue(updatedStudParentBusiness);
        studentRef.child("parent_last_income").setValue(updatedStudParentIncome);



        progressDialog.dismiss();
        Toast.makeText(ActivityVIewStudentAdmissionForm.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

        student_photo.setClickable(false);
        et_stud_add_class.setEnabled(false);
        et_stud_add_no.setEnabled(false);
        et_stud_add_date.setEnabled(false);
        et_stud_id.setEnabled(false);
        et_stud_UDI.setEnabled(false);
        et_stud_full_name.setEnabled(false);
        et_stud_mother_full_name.setEnabled(false);
        et_stud_cast.setEnabled(false);
        et_stud_religion.setEnabled(false);
        et_stud_birth_place.setEnabled(false);
        et_stud_DOB_no.setEnabled(false);
        et_stud_DOB_latter.setEnabled(false);
        et_stud_age.setEnabled(false);
        et_stud_gender.setEnabled(false);
        et_stud_mother_tung.setEnabled(false);
        et_stud_class_passed.setEnabled(false);
        et_stud_pass_grade.setEnabled(false);
        et_stud_last_school_name.setEnabled(false);
        et_stud_bank_name.setEnabled(false);
        et_stud_bank_IFSC.setEnabled(false);
        et_stud_bank_acco_no.setEnabled(false);

        et_stud_parent_full_name.setEnabled(false);
        et_parent_relation.setEnabled(false);
        et_stud_parent_minority.setEnabled(false);
        et_parent_economical.setEnabled(false);
        et_parent_temp_address.setEnabled(false);
        et_parent_perm_address.setEnabled(false);
        et_parent_telephone_no.setEnabled(false);
        et_parent_mo_no.setEnabled(false);
        et_parent_business.setEnabled(false);
        et_parent_last_income.setEnabled(false);
        btn_update.setText("Update");

        // Set the original click listener for the "Update" button
        btn_update.setOnClickListener(v -> {
            updateAdmissionForm(v);
        });
    }



    private void deleteAdmissionData() {

        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("admissionData").child(selectedAdmissionNo);
        ProgressDialog progressDialog = new ProgressDialog(ActivityVIewStudentAdmissionForm.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting Data");
        progressDialog.show();
        clerkReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(ActivityVIewStudentAdmissionForm.this, ActivityViewRecentlyAdmissionForm.class);
                    progressDialog.dismiss();
                    startActivity(i);
                    finish();
                    Toast.makeText(ActivityVIewStudentAdmissionForm.this, "Admission Form Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityVIewStudentAdmissionForm.this, "Failed to Delete Admission Form", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }




    private void retrieveAdmissionData(String studAdmissionNo) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("admissionData").child(studAdmissionNo);

        clerkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve data from the snapshot
                    String studAddNo = snapshot.child("stud_admission_no").getValue(String.class);
                    String studAddDate = snapshot.child("stud_admission_date").getValue(String.class);
                    String studAddClass = snapshot.child("stud_admission_class").getValue(String.class);
                    String studId = snapshot.child("student_id").getValue(String.class);
                    String studUID = snapshot.child("student_UID").getValue(String.class);
                    String studFullName = snapshot.child("student_full_name").getValue(String.class);
                    String StudMotherFullName = snapshot.child("student_mother_name").getValue(String.class);
                    String StudReligion = snapshot.child("student_religion").getValue(String.class);
                    String StudCaste = snapshot.child("student_caste").getValue(String.class);
                    String StudDOBInNO = snapshot.child("student_DOB_No").getValue(String.class);
                    String StudDOBInLatter = snapshot.child("student_DOB_Letter").getValue(String.class);
                    String StudBirthPlace = snapshot.child("student_birth_place").getValue(String.class);
                    String StudAge = snapshot.child("student_age").getValue(String.class);
                    String StudGender = snapshot.child("student_gender").getValue(String.class);
                    String StudMother_Tung = snapshot.child("student_mother_tung").getValue(String.class);
                    String StudClassPassed = snapshot.child("passed_class").getValue(String.class);
                    String StudClassPassedGrade = snapshot.child("passed_grad").getValue(String.class);
                    String StudLastSchool = snapshot.child("last_school_name").getValue(String.class);
                    String StudBankName = snapshot.child("bank_name").getValue(String.class);
                    String StudBankIFSC = snapshot.child("bank_IFSC_No").getValue(String.class);
                    String StudAccoNo = snapshot.child("bank_account_no").getValue(String.class);
                    String StudParentFullName = snapshot.child("parent_full_name").getValue(String.class);
                    String StudParentRelation = snapshot.child("parent_relation").getValue(String.class);
                    String StudMinority = snapshot.child("minority_caste").getValue(String.class);
                    String StudEconomical = snapshot.child("economical_weaker").getValue(String.class);
                    String StudTempAddress = snapshot.child("parent_temp_address").getValue(String.class);
                    String StudPermAddress = snapshot.child("parent_perm_address").getValue(String.class);
                    String StudTeleNo = snapshot.child("parent_telephone_no").getValue(String.class);
                    String StudMoNo = snapshot.child("parent_mo_no").getValue(String.class);
                    String StudBusiness = snapshot.child("parent_business").getValue(String.class);
                    String StudParentIncome = snapshot.child("parent_last_income").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);

                    // Set retrieved data into EditText fields
                    et_stud_add_no.setText(studAddNo);
                    et_stud_add_date.setText(studAddDate);
                    et_stud_add_class.setText(studAddClass);
                    et_stud_id.setText(studId);
                    et_stud_UDI.setText(studUID);
                    et_stud_full_name.setText(studFullName);
                    et_stud_mother_full_name.setText(StudMotherFullName);
                    et_stud_cast.setText(StudCaste);
                    et_stud_religion.setText(StudReligion);
                    et_stud_birth_place.setText(StudBirthPlace);
                    et_stud_DOB_no.setText(StudDOBInNO);
                    et_stud_DOB_latter.setText(StudDOBInLatter);
                    et_stud_age.setText(StudAge);
                    et_stud_gender.setText(StudGender);
                    et_stud_mother_tung.setText(StudMother_Tung);
                    et_stud_class_passed.setText(StudClassPassed);
                    et_stud_pass_grade.setText(StudClassPassedGrade);
                    et_stud_last_school_name.setText(StudLastSchool);
                    et_stud_bank_name.setText(StudBankName);
                    et_stud_bank_IFSC.setText(StudBankIFSC);
                    et_stud_bank_acco_no.setText(StudAccoNo);
                    et_stud_parent_full_name.setText(StudParentFullName);
                    et_parent_relation.setText(StudParentRelation);
                    et_stud_parent_minority.setText(StudMinority);
                    et_parent_economical.setText(StudEconomical);
                    et_parent_temp_address.setText(StudTempAddress);
                    et_parent_perm_address.setText(StudPermAddress);
                    et_parent_telephone_no.setText(StudTeleNo);
                    et_parent_mo_no.setText(StudMoNo);
                    et_parent_business.setText(StudBusiness);
                    et_parent_last_income.setText(StudParentIncome);

                    // Load image using the retrieved image URL
                    // Here, you can use any image loading library like Glide, Picasso, etc.
                    // For example using Glide:
                    Glide.with(ActivityVIewStudentAdmissionForm.this).load(imageUrl).into(student_photo);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(ActivityVIewStudentAdmissionForm.this, "Data not found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityVIewStudentAdmissionForm.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void generatePDF() {
        admissionClass=et_stud_add_class.getText().toString();
        admissionNo=et_stud_add_no.getText().toString();
        admissiondate=et_stud_add_date.getText().toString();
        studentUId=et_stud_id.getText().toString();
        adharId=et_stud_UDI.getText().toString();
        studentName=et_stud_full_name.getText().toString();
        motherName=et_stud_mother_full_name.getText().toString();
        cast=et_stud_cast.getText().toString();
        religion=et_stud_religion.getText().toString();
        birthplace=et_stud_birth_place.getText().toString();
        birthDate=et_stud_DOB_no.getText().toString();
        birthDateinLetter=et_stud_DOB_latter.getText().toString();
        studentAge=et_stud_age.getText().toString();
        gender=et_stud_gender.getText().toString();
        mothertung=et_stud_mother_tung.getText().toString();
        classPased=et_stud_class_passed.getText().toString();
        passedGrade=et_stud_pass_grade.getText().toString();
        lastSchoolName=et_stud_last_school_name.getText().toString();
        bankName=et_stud_bank_name.getText().toString();
        bankIFSC=et_stud_bank_IFSC.getText().toString();
        bankAccNo=et_stud_bank_acco_no.getText().toString();
        imageFromFirstFragment = student_photo.getDrawable();


        parentName=et_stud_parent_full_name.getText().toString();
        parentrelation=et_parent_relation.getText().toString();
        parentMinotrity=et_stud_parent_minority.getText().toString();
        parentEconomical=et_parent_economical.getText().toString();
        preanttempaddress=et_parent_temp_address.getText().toString();
        parentpermentaddress=et_parent_perm_address.getText().toString();
        parenttelephoneno=et_parent_telephone_no.getText().toString();
        preantmobileno=et_parent_mo_no.getText().toString();
        prentbussiness=et_parent_business.getText().toString();
        prentlastyearincome=et_parent_last_income.getText().toString();





        Document document = new Document();

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Student_Admission_Form.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();



            //Fonts
            Font timesnewRoman=new Font(Font.FontFamily.TIMES_ROMAN);
            Font timesnewBoldRoman=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewBoldRomansub=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD);
            Font timesnewBoldRomansmall=new Font(Font.FontFamily.TIMES_ROMAN,15);
            Font tosmall=new Font(Font.FontFamily.TIMES_ROMAN,13);
            Font timesnewRomanitelic=new Font(Font.FontFamily.TIMES_ROMAN,13,Font.ITALIC);
            Font heleticaFont=new Font(Font.FontFamily.HELVETICA,25,Font.BOLD);


            // Add background image
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pdf_logo);
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] background = stream.toByteArray();
            Image backgroundImage = Image.getInstance(background);
            backgroundImage.scaleAbsolute(250,250);
            backgroundImage.setAbsolutePosition(180, 300);
            backgroundImage.setAlignment(Image.ALIGN_CENTER);
            document.add(backgroundImage);

            Paragraph spacing=new Paragraph(" ");
            spacing.getFont().setSize(7);

            // Adding content to PDF
            // Convert drawable to Bitmap
            //addind the border
            // Add border to the PDF
            // Add border to the PDF with padding
            Rectangle border = new Rectangle(document.getPageSize());
            border.setBorder(Rectangle.BOX);
            border.setBorderWidth(2);
            border.setBorderColor(BaseColor.BLACK);

            // Set padding (20 points) around the content
            float padding = 20;
            border.setLeft(border.getLeft() + padding);
            border.setRight(border.getRight() - padding);
            border.setTop(border.getTop() - padding);
            border.setBottom(border.getBottom() + padding);

            document.add(border);

            BitmapDrawable drawable2 = (BitmapDrawable) imageFromFirstFragment;
            Bitmap bitmap1 = drawable2.getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            byte[] background1 = stream2.toByteArray();
            Image backgroundImage1 = Image.getInstance(background1);
            backgroundImage1.scaleAbsolute(100,100);
            backgroundImage1.setAbsolutePosition(450, 700);
            backgroundImage1.setAlignment(Image.ALIGN_CENTER);
            document.add(backgroundImage1);



            Paragraph para4 = new Paragraph(" Admission Application ",timesnewBoldRoman);
            para4.getFont().setColor(0, 0, 0); // Black
            para4.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para4);

            Paragraph para1 = new Paragraph("Tarai Shikshan Sansth's Paithan",timesnewRoman);
            para1.getFont().setColor(255, 0, 0); // Red color
            para1.getFont().setSize(15);
            para1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para1);


            Paragraph para2 = new Paragraph("S.P. BAKLIWAL VIDYALAY",heleticaFont);
            para2.getFont().setColor(255, 0, 0); // Red color

            para2.getFont().setSize(23); // Text size 25
            para2.setAlignment(Paragraph.ALIGN_CENTER);
            para2.getFont().setStyle("bold"); // Bold
            document.add(para2);


            Paragraph para3 = new Paragraph("Theragon Tal Paithan Dist. Chha. Sambhajinagar",timesnewRomanitelic);
            para3.getFont().setColor(255, 0, 0); // Red color
            para3.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para3);

            Paragraph para6=new Paragraph("       Admission Class : "+admissionClass+"     Admission No. : "+admissionNo+"    Admission Date : "+admissiondate,timesnewBoldRomansmall);
            document.add(para6);
            document.add(spacing);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(95f);
            lineSeparator.setAlignment(Element.ALIGN_CENTER);
            lineSeparator.setLineColor(BaseColor.RED); // Set color to black
            document.add(lineSeparator);
            document.add(spacing);
            document.add(lineSeparator);
            Paragraph para7=new Paragraph("a) Details Of Student : Student ID. :"+studentUId+"    Aadhar Id. : "+adharId,timesnewBoldRomansub);
            document.add(para7);
            Paragraph para8=new Paragraph("  1).Full Name Of Student : "+studentName+".",timesnewBoldRomansmall);
            document.add(para8);
            Paragraph para9=new Paragraph("  2).Mother Name : "+motherName+".                                                                     3). Cast And Religion : "+cast+" & "+religion,timesnewBoldRomansmall);
            document.add(para9);
            Paragraph para99=new Paragraph("  4).Birthplace : "+birthplace,timesnewBoldRomansmall);
            document.add(para99);
            Paragraph para10=new Paragraph("  5).Birth Date : "+birthDate+".     In Letter : "+birthDateinLetter,timesnewBoldRomansmall);
            document.add(para10);
            Paragraph para11=new Paragraph("  6).Age Of Student : "+studentAge+".         7).Gender Of Student  : "+gender,timesnewBoldRomansmall);
            document.add(para11);
           // document.add(new Paragraph( "     ,timesnewBoldRomansmall));
            Paragraph para12=new Paragraph("  8).MotherTung : "+mothertung+".     9). Class Passed  "+classPased+"     10). Passed Grade ( "+passedGrade+").",timesnewBoldRomansmall);
            document.add(para12);
            Paragraph para13=new Paragraph("  11).Previous School Naame : "+lastSchoolName,timesnewBoldRomansmall);
            document.add(para13);
            Paragraph para14=new Paragraph("  12).Bank Account No. : "+bankName+".",timesnewBoldRomansmall);
            document.add(para14);
            document.add(new Paragraph("         Bank IFSC Code : "+bankIFSC+"  Account No. : "+bankAccNo,timesnewBoldRomansmall));
            document.add(spacing);
            lineSeparator.setLineColor(BaseColor.BLACK); // Set color to black
            document.add(lineSeparator);
            document.add(spacing);
            document.add(new Paragraph("b) Details About Parent :",timesnewBoldRomansub));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("             1). Full Name Of Parent : "+parentName,timesnewBoldRomansmall));
            document.add(new Paragraph("             2). Relation To Student : "+parentrelation+"        3). Is Minority Weaker ? : "+parentMinotrity,timesnewBoldRomansmall));
            document.add(new Paragraph("             4). Is Economical Weaker ? : "+parentEconomical,timesnewBoldRomansmall));
            document.add(new Paragraph("             5). Temporary Address : "+preanttempaddress,timesnewBoldRomansmall));
            document.add(new Paragraph("             6). Permanent Address : "+parentpermentaddress,timesnewBoldRomansmall));
            document.add(new Paragraph("             7). Telephone No. : "+parenttelephoneno+"                  Mobile No. : "+preantmobileno,timesnewBoldRomansmall));
            document.add(new Paragraph("             8). Bussiness : "+prentbussiness +"             Last year Income : "+prentlastyearincome,timesnewBoldRomansmall));
            document.add(spacing);
            document.add(new Paragraph("                 We Agree That If The Above Applicant Is Admitted To Your School, All Instructions And Rules Issued By The School From Time To Time Will Be Followed And They Will Be Binding On Us.",tosmall));
            document.add(new Paragraph("\nParent Sign.",timesnewBoldRomansmall));
            document.add(new Paragraph("Name Of Signatory                                                     Student Sign",timesnewBoldRomansmall));

            document.close();

            // PDF generation completed
            Toast.makeText(this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




}