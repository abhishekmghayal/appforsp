package com.rahul.spbcollegeduplicate.ClerkModule.LeavingCertificate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ViewStudentLeavingCertificate extends AppCompatActivity {
    EditText et_general_register_no, et_studentUID, et_student_full_name, et_student_mother_full_name, et_student_nationality,et_student_mother_tung, et_student_caste,
            et_student_religion, et_student_sub_caste, et_student_village, et_student_taluqa, et_student_district, et_student_state, et_studentDOB, et_student_DOB_in_latter,
            et_student_last_school, et_student_last_std, et_student_admission_date, et_student_adde_STD, et_student_progress_Study, et_student_behavior,et_student_leave_date,
            et_student_current_STD,et_student_long_study,et_student_leave_reason,et_student_grade;
    DatePickerDialog.OnDateSetListener setListener;
    Button printButton,deleteButton;
    String selectedGeneralRegisterNo;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_leaving_certificate);

        progressDialog = new ProgressDialog(this);

        setTitle("View Leaving Certificate");

        et_general_register_no = findViewById(R.id.et__avslc_stdgenralno);
        et_studentUID = findViewById(R.id.et__avslc_stdadharno);
        et_student_full_name = findViewById(R.id.et__avslc_stdname);
        et_student_mother_full_name = findViewById(R.id.et__avslc_stdmothername);
        et_student_nationality = findViewById(R.id.et__avslc_stdnationality);
        et_student_mother_tung = findViewById(R.id.et__avslc_stdmothertung);
        et_student_religion = findViewById(R.id.et__avslc_stdreligion);
        et_student_caste = findViewById(R.id.et__avslc_stdcast);
        et_student_sub_caste = findViewById(R.id.et__avslc_stdsubcast);
        et_student_village = findViewById(R.id.et__avslc_stdbirthvillage);
        et_student_taluqa = findViewById(R.id.et__avslc_stdbirthtaluqa);
        et_student_district = findViewById(R.id.et__avslc_stdbirthdist);
        et_student_state = findViewById(R.id.et__avslc_stdbirthstate);
        et_studentDOB = findViewById(R.id.et__avslc_stddateofbirth);
        et_student_DOB_in_latter = findViewById(R.id.et__avslc_stddateofbirthinletter);
        et_student_last_school = findViewById(R.id.et__avslc_stdlastschoolname);
        et_student_last_std = findViewById(R.id.et__avslc_stdlaststd);
        et_student_admission_date = findViewById(R.id.et__avslc_stdadissiondate);
        et_student_adde_STD = findViewById(R.id.et__avslc_stdaddstd);
        et_student_progress_Study = findViewById(R.id.et__avslc_stdprogressinstydy);
        et_student_behavior = findViewById(R.id.et__avslc_stdbheviour);
        et_student_leave_date = findViewById(R.id.et__avslc_stdleavedate);
        et_student_current_STD = findViewById(R.id.et__avslc_stdcurrntclass);
        et_student_long_study = findViewById(R.id.et__avslc_stdholongsudy);
        et_student_leave_reason = findViewById(R.id.et__avslc_stdreasonofleaving);
        et_student_grade = findViewById(R.id.et__avslc_stdgrade);

        printButton = findViewById(R.id.btn__avslc_print_leavingcertificate);
        deleteButton = findViewById(R.id.btn__avslc_delete_leavingcertificate);


        et_general_register_no.setEnabled(false);
        et_studentUID.setEnabled(false);
        et_student_full_name.setEnabled(false);
        et_student_mother_full_name.setEnabled(false);
        et_student_nationality.setEnabled(false);
        et_student_mother_tung.setEnabled(false);
        et_student_caste.setEnabled(false);
        et_student_religion.setEnabled(false);
        et_student_sub_caste.setEnabled(false);
        et_student_village.setEnabled(false);
        et_student_taluqa.setEnabled(false);
        et_student_district.setEnabled(false);
        et_student_state.setEnabled(false);
        et_studentDOB.setEnabled(false);
        et_student_DOB_in_latter.setEnabled(false);
        et_student_last_school.setEnabled(false);
        et_student_last_std.setEnabled(false);
        et_student_admission_date.setEnabled(false);
        et_student_adde_STD.setEnabled(false);
        et_student_progress_Study.setEnabled(false);
        et_student_behavior.setEnabled(false);
        et_student_leave_date.setEnabled(false);
        et_student_current_STD.setEnabled(false);
        et_student_long_study.setEnabled(false);
        et_student_leave_reason.setEnabled(false);
        et_student_grade.setEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            selectedGeneralRegisterNo = intent.getStringExtra("GENERAL_REGISTER_NO");
            retrieveLeaveCertificateData(selectedGeneralRegisterNo);
        }


        et_student_admission_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_student_admission_date);
                }
            }
        });

        et_studentDOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_studentDOB);
                }
            }
        });

        et_student_leave_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    showDatePicker(et_student_leave_date);
                }
            }
        });


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogDelete();
            }
        });
    }

    private void showDatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ViewStudentLeavingCertificate.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    private void retrieveLeaveCertificateData(String studGeneralRegisterNo) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Leaving_Certificate_Record").child(studGeneralRegisterNo);

        clerkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String studGenRegNo = snapshot.child("student_general_register_no").getValue(String.class);
                    String studUID = snapshot.child("studentUID").getValue(String.class);
                    String studFullName = snapshot.child("student_full_name").getValue(String.class);
                    String StudMotherFullName = snapshot.child("student_mother_full_name").getValue(String.class);
                    String StudNationality = snapshot.child("student_nationality").getValue(String.class);
                    String StudReligion = snapshot.child("student_religion").getValue(String.class);
                    String StudCaste = snapshot.child("student_caste").getValue(String.class);
                    String StudSubCaste = snapshot.child("student_sub_caste").getValue(String.class);
                    String StudVillage = snapshot.child("student_village").getValue(String.class);
                    String StudTaluqa = snapshot.child("student_taluqa").getValue(String.class);
                    String StudDist = snapshot.child("student_district").getValue(String.class);
                    String StudState = snapshot.child("student_state").getValue(String.class);
                    String StudDOBInNO = snapshot.child("studentDOB").getValue(String.class);
                    String StudDOBInLatter = snapshot.child("student_DOB_in_latter").getValue(String.class);
                    String StudLastSchool = snapshot.child("student_last_school").getValue(String.class);
                    String StudLastSTD = snapshot.child("student_last_std").getValue(String.class);
                    String StudAddedSTD = snapshot.child("student_new_STD").getValue(String.class);
                    String StudCurrentAddDate = snapshot.child("student_admission_date").getValue(String.class);
                    String StudProgressInStudy = snapshot.child("student_progress_Study").getValue(String.class);
                    String StudBehavior = snapshot.child("student_behavior").getValue(String.class);
                    String StudLeaveDate = snapshot.child("student_leave_date").getValue(String.class);
                    String StudCurrentSTD = snapshot.child("student_current_std").getValue(String.class);
                    String StudHowLongStudy = snapshot.child("student_studying_long_year").getValue(String.class);
                    String StudLeaveReason = snapshot.child("student_leave_reason").getValue(String.class);
                    String StudGrade = snapshot.child("student_grade").getValue(String.class);


                    et_general_register_no.setText(studGenRegNo);
                    et_studentUID.setText(studUID);
                    et_student_full_name.setText(studFullName);
                    et_student_mother_full_name.setText(StudMotherFullName);
                    et_student_nationality.setText(StudNationality);
                    et_student_religion.setText(StudReligion);
                    et_student_caste.setText(StudCaste);
                    et_student_sub_caste.setText(StudSubCaste);
                    et_student_village.setText(StudVillage);
                    et_student_taluqa.setText(StudTaluqa);
                    et_student_district.setText(StudDist);
                    et_student_state.setText(StudState);
                    et_studentDOB.setText(StudDOBInNO);
                    et_student_DOB_in_latter.setText(StudDOBInLatter);
                    et_student_last_school.setText(StudLastSchool);
                    et_student_last_std.setText(StudLastSTD);
                    et_student_admission_date.setText(StudCurrentAddDate);
                    et_student_adde_STD.setText(StudAddedSTD);
                    et_student_progress_Study.setText(StudProgressInStudy);
                    et_student_behavior.setText(StudBehavior);
                    et_student_leave_date.setText(StudLeaveDate);
                    et_student_current_STD.setText(StudCurrentSTD);
                    et_student_long_study.setText(StudHowLongStudy);
                    et_student_leave_reason.setText(StudLeaveReason);
                    et_student_grade.setText(StudGrade);
                } else {
                    Toast.makeText(ViewStudentLeavingCertificate.this, "Leave Certificate Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewStudentLeavingCertificate.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showConfirmationDialogDelete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete Leaving Certificate ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteLeavingCertificate();
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


    private void deleteLeavingCertificate() {

        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Leaving_Certificate_Record").child(selectedGeneralRegisterNo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting Data");
        progressDialog.show();
        clerkReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(ViewStudentLeavingCertificate.this,LeavingCertificateActivity.class);
                    progressDialog.dismiss();
                    startActivity(i);
                    finish();
                    Toast.makeText(ViewStudentLeavingCertificate.this, "Leaving Certificate Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewStudentLeavingCertificate.this, "Failed to Delete Leaving Certificate", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void generatePDF() {
        Document document = new Document();

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Student_Leaving_Ceritificate.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            //Fonts
            Font timesnewRoman=new Font(Font.FontFamily.TIMES_ROMAN);
            Font timesnewBoldRoman=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewBoldRomansub=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewBoldRomansmall=new Font(Font.FontFamily.TIMES_ROMAN,13);
            Font timesnewRomanitelic=new Font(Font.FontFamily.TIMES_ROMAN,13,Font.ITALIC);
            Font heleticaFont=new Font(Font.FontFamily.HELVETICA,25,Font.BOLD);
            cell.setBorderWidth(1); // Set border width

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
            Paragraph para1 = new Paragraph("Tarai Shikshan Sansth's",timesnewRoman);
            para1.getFont().setColor(0, 0, 0); // Red color
            para1.getFont().setSize(15);
            para1.setAlignment(Paragraph.ALIGN_CENTER);
            // document.add(para1);
            cell.addElement(para1);

            Paragraph para2 = new Paragraph("S.P. BAKLIWAL VIDYALAY,Theragon",heleticaFont);
            para2.getFont().setColor(0, 0, 0); // Red color

            para2.getFont().setSize(23); // Text size 25
            para2.setAlignment(Paragraph.ALIGN_CENTER);
            para2.getFont().setStyle("bold"); // Bold
            /* document.add(para2);*/
            cell.addElement(para2);

            Paragraph para3 = new Paragraph("Theragon Tal Paithan Dist. Chha. Sambhajinagar",timesnewRomanitelic);
            para3.getFont().setColor(0, 0, 0); // Red color
            para3.setAlignment(Paragraph.ALIGN_CENTER);
            /* document.add(para3);*/
            cell.addElement(para3);

            Paragraph para4=new Paragraph("                                                                                                      General Reg. No.: "+et_general_register_no.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para4);
            Paragraph para5 = new Paragraph("Leaving Certificate",timesnewBoldRoman);
            para5.getFont().setColor(0, 0, 0); // Black
            para5.setAlignment(Paragraph.ALIGN_CENTER);
            /*document.add(para5);*/
            cell.addElement(para5);
            Paragraph para6=new Paragraph("                                                                                                          Aaddar Id: "+et_studentUID.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para6);
            Paragraph para7=new Paragraph(" \n");
            para7.getFont().setSize(5);
            cell.addElement(para7 );
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(80f);
            lineSeparator.setAlignment(Element.ALIGN_CENTER);
            lineSeparator.setLineColor(BaseColor.BLACK); // Set color to black
            cell.addElement(lineSeparator);
            cell.addElement(spacing);

            Paragraph para8=new Paragraph("          1). Full Name Of Student : "+et_student_full_name.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para8);
            cell.addElement(spacing);

            Paragraph para9=new Paragraph("          2). Name Of Student's Mother : "+et_student_mother_full_name.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para9);
            cell.addElement(spacing);
            Paragraph para10=new Paragraph("          3). Nationality : "+et_student_nationality.getText().toString()+"                              4). Mother Tung : "+et_student_mother_tung.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para10);
            cell.addElement(spacing);
            Paragraph para11=new Paragraph("          5). Religion : "+et_student_religion.getText().toString()+"               Caste : "+et_student_caste.getText().toString()+"               Sub-Cast : "+et_student_sub_caste.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para11);
            cell.addElement(spacing);
            Paragraph para12=new Paragraph("          6). Birthplace (Village) : "+et_student_village.getText().toString()+".   Tal. : "+et_student_taluqa.getText().toString()+".                                                                                                                                                                                                                                                  Dist. : "+et_student_district.getText().toString()+".   State : "+et_student_state.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para12);
            cell.addElement(spacing);
            Paragraph para13=new Paragraph("          7). Birth Date : "+et_studentDOB.getText().toString()+"     And In Letters : "+et_student_DOB_in_latter.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para13);
            cell.addElement(spacing);
            Paragraph para14=new Paragraph("          8). Previous School And Standard : "+et_student_last_school.getText().toString()+"   And "+et_student_last_std.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para14);
            cell.addElement(spacing);
            Paragraph para15=new Paragraph("          9). Date Of Admission To This School : "+et_student_admission_date.getText().toString()+"      Standard: "+et_student_adde_STD.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para15);
            cell.addElement(spacing);
            Paragraph para16=new Paragraph("          10). Progress In Studies : "+et_student_progress_Study.getText().toString()+"                            11). Behaviour : "+et_student_behavior.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para16);
            cell.addElement(spacing);
            Paragraph para17=new Paragraph("          12). Leave Date : "+et_student_leave_date.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para17);
            cell.addElement(spacing);
            Paragraph para18=new Paragraph("          13). In Which Class Studied And Since When : "+et_student_current_STD.getText().toString()+"   And  "+et_student_long_study.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para18);
            cell.addElement(spacing);
            Paragraph para19=new Paragraph("          14). Reason For Dropping out of School : "+et_student_leave_reason.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para19);
            cell.addElement(spacing);
            Paragraph para20=new Paragraph("          15). Shera : "+et_student_grade.getText().toString()+".",timesnewBoldRomansmall);
            cell.addElement(para20);
            cell.addElement(spacing);
            Paragraph para21=new Paragraph("         It is certified that the above information in the school's General Register No. as it like.",timesnewBoldRomansmall);
            cell.addElement(para21);
            cell.addElement(spacing);
            Paragraph para22=new Paragraph("         Date: "+et_student_leave_date.getText().toString(),timesnewBoldRomansmall);
            cell.addElement(para22);
            cell.addElement(spacing);
            Paragraph para23=new Paragraph("\n\n\n       Class Teacher Sign.                                Clerk Sign.                       Head Master Sign.",timesnewBoldRomansmall);
            para23.setAlignment(Paragraph.ALIGN_CENTER);
            cell.addElement(para23);
            cell.addElement(para7 );
            LineSeparator lineSeparator1 = new LineSeparator();
            lineSeparator1.setPercentage(80f);
            lineSeparator1.setAlignment(Element.ALIGN_CENTER);
            lineSeparator1.setLineColor(BaseColor.BLACK); // Set color to black
            cell.addElement(lineSeparator1);
            cell.addElement(new Paragraph("Tips : 1). Unauthorized alteration of School Leaving Certificate will result in legal action against concered."));
            cell.addElement(spacing);

           /* Paragraph para4 = new Paragraph("                       UDISE NO. " + editTextGeneralNo.getText().toString()+"                         INDEX NO. " + editTextStand.getText().toString(),timesnewRoman);
            para4.getFont().setColor(0, 0, 255); // Blue
            para4.getFont().setSize(15);
            *//*document.add(para4);*//*
            cell.addElement(para4);

            Paragraph para5 = new Paragraph("I-CARD 2023-24",timesnewBoldRoman);
            para5.getFont().setColor(0, 0, 0); // Black
            para5.setAlignment(Paragraph.ALIGN_CENTER);
            *//*document.add(para5);*//*
            cell.addElement(para5);
            cell.addElement(new Paragraph("\n"));
            *//*document.add(new Paragraph(""));*//*

            Paragraph para11=new Paragraph("\n\n\n"+"         Student Sign "+"                                     Collage Stamp",timesnewRomanitelic);
            para11.setAlignment(Paragraph.ALIGN_CENTER);
            *//*document.add(para11);*//*
            cell.addElement(para11);
            cell.addElement(new Paragraph("\n"));
*/

            table.addCell(cell);
            document.add(table);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);


            document.close();

            // PDF generation completed
            Toast.makeText(this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}