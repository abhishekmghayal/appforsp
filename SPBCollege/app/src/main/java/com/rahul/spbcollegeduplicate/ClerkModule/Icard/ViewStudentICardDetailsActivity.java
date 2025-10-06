package com.rahul.spbcollegeduplicate.ClerkModule.Icard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rahul.spbcollegeduplicate.ClerkModule.FirebaseExtraClasses.AddStudentIcardDatabaseHelperClass;
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ViewStudentICardDetailsActivity extends AppCompatActivity {
    EditText et_studentNameForICard, et_studentDOBForICard, et_studentAcademicYearForICard,
            et_studentGeneralREGNoForICard, et_studentClassForICard, et_studentAddressForICard;
    Button btn_update_ICard, btn_delete_ICard;
    ImageButton ibtn_print;
    ImageView imageView;

    String studentFullName, studentGeneralREGNo, studentSTD, studentAcademicYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_icard_details);

        setTitle("Students ICard");

        et_studentNameForICard = findViewById(R.id.tv_icard_name);
        et_studentDOBForICard = findViewById(R.id.tv_icard_student_DOB);
        et_studentAcademicYearForICard = findViewById(R.id.tv_icard_student_academic_year);
        et_studentGeneralREGNoForICard = findViewById(R.id.tv_icard_student_generalREGNO);
        et_studentClassForICard = findViewById(R.id.tv_icard_student_STD);
        et_studentAddressForICard = findViewById(R.id.tv_icard_student_address);
        ibtn_print = findViewById(R.id.btn_print_icard);
        imageView = findViewById(R.id.view_Icard_student_photo);

        et_studentNameForICard.setEnabled(false);
        et_studentDOBForICard.setEnabled(false);
        et_studentAcademicYearForICard.setEnabled(false);
        et_studentGeneralREGNoForICard.setEnabled(false);
        et_studentClassForICard.setEnabled(false);
        et_studentAddressForICard.setEnabled(false);

        btn_update_ICard = findViewById(R.id.btn_icard_update);
        btn_delete_ICard = findViewById(R.id.btn_icard_delete);

        Intent intent = getIntent();
        if (intent != null) {
            studentFullName = intent.getStringExtra("studentFullName");
            studentGeneralREGNo = intent.getStringExtra("studentGeneralREGNo");
            studentSTD = intent.getStringExtra("studentSTD");
            studentAcademicYear = intent.getStringExtra("studentAcademicYear");
            retrieveICardData();
        }

        btn_update_ICard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogForUpdate();
            }
        });

        btn_delete_ICard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogForDelete();
            }
        });

        ibtn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });

    }

    private void retrieveICardData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student's_ICard_Record")
                .child(studentSTD); // Assuming studentSTD is the node where iCard data is stored

        databaseReference.orderByChild("studentFullName").equalTo(studentFullName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot icardSnapshot : dataSnapshot.getChildren()) {
                                // Retrieve iCard data including the image URL
                                AddStudentIcardDatabaseHelperClass iCardData = icardSnapshot.getValue(AddStudentIcardDatabaseHelperClass.class);
                                String imageUrl = iCardData.getImageUrl(); // Assuming you have a method to get the image URL from the model class
                                // Update UI with iCard data and load the image
                                updateUI(iCardData, imageUrl);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error, if any
                    }
                });
    }
    private void updateUI(AddStudentIcardDatabaseHelperClass iCardData, String imageUrl) {
        // Update your UI components with the retrieved iCard data
        et_studentNameForICard.setText(iCardData.getStudentFullName());
        et_studentDOBForICard.setText(iCardData.getStudentDOB());
        et_studentAcademicYearForICard.setText(iCardData.getStudentAcademicYear());
        et_studentGeneralREGNoForICard.setText(iCardData.getStudentGeneralRegisterNumber());
        et_studentClassForICard.setText(iCardData.getStudentStandard());
        et_studentAddressForICard.setText(iCardData.getStudentAddress());

        // Load the image using Glide
        Glide.with(ViewStudentICardDetailsActivity.this)
                .load(imageUrl)
                .placeholder(R.drawable.profile_icon) // Placeholder image while loading
                .error(R.drawable.image_error) // Error image if loading fails
                .into(imageView);
    }

    private void showConfirmationDialogForUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Update ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateICardData();
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

    private void updateICardData() {

        Calendar calendar=Calendar.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final  int month=calendar.get(Calendar.MONTH);
        final  int day=calendar.get(Calendar.DAY_OF_MONTH);
        // Enable EditText fields for editing
        et_studentNameForICard.setEnabled(true);
        et_studentDOBForICard.setEnabled(true);
        et_studentAcademicYearForICard.setEnabled(true);
        et_studentGeneralREGNoForICard.setEnabled(true);
        et_studentClassForICard.setEnabled(true);
        et_studentAddressForICard.setEnabled(true);

        et_studentDOBForICard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ViewStudentICardDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        // Change the button text to "Save Changes"
        btn_update_ICard.setText("Save Changes");

        // Set a new click listener for the "Save Changes" button
        btn_update_ICard.setOnClickListener(v -> {
            showConfirmationDialogForConformUpdate();
        });
    }

    private void showConfirmationDialogForConformUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you Conform To Update ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveICardChangesToFirebase();
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


    private void saveICardChangesToFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student's_ICard_Record")
                .child(studentSTD);

        // Retrieve updated data from EditText fields
        String updatedStudentName = et_studentNameForICard.getText().toString();
        String updatedStudentDOB = et_studentDOBForICard.getText().toString();
        String updatedStudentAcademicYear = et_studentAcademicYearForICard.getText().toString();
        String updatedStudentGeneralREGNo = et_studentGeneralREGNoForICard.getText().toString();
        String updatedStudentClass = et_studentClassForICard.getText().toString();
        String updatedStudentAddress = et_studentAddressForICard.getText().toString();

        // Update the iCard data in the Firebase Realtime Database
        databaseReference.orderByChild("studentFullName").equalTo(studentFullName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot icardSnapshot : dataSnapshot.getChildren()) {
                                icardSnapshot.getRef().child("studentFullName").setValue(updatedStudentName);
                                icardSnapshot.getRef().child("studentDOB").setValue(updatedStudentDOB);
                                icardSnapshot.getRef().child("studentAcademicYear").setValue(updatedStudentAcademicYear);
                                icardSnapshot.getRef().child("studentGeneralRegisterNumber").setValue(updatedStudentGeneralREGNo);
                                icardSnapshot.getRef().child("studentStandard").setValue(updatedStudentClass);
                                icardSnapshot.getRef().child("studentAddress").setValue(updatedStudentAddress);
                            }

                            // Notify the user that changes have been saved
                            Toast.makeText(ViewStudentICardDetailsActivity.this, "ICard updated successfully", Toast.LENGTH_SHORT).show();

                            // Disable EditText fields for editing
                            et_studentNameForICard.setEnabled(false);
                            et_studentDOBForICard.setEnabled(false);
                            et_studentAcademicYearForICard.setEnabled(false);
                            et_studentGeneralREGNoForICard.setEnabled(false);
                            et_studentClassForICard.setEnabled(false);
                            et_studentAddressForICard.setEnabled(false);

                            // Change the button text back to "Update"
                            btn_update_ICard.setText("Update");

                            // Set the original click listener for the "Update" button
                            btn_update_ICard.setOnClickListener(v -> {
                                updateICardData();
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error, if any
                    }
                });
    }

    private void showConfirmationDialogForDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteICardData();
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


    private void deleteICardData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student's_ICard_Record")
                .child(studentSTD);

        databaseReference.orderByChild("studentFullName").equalTo(studentFullName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot icardSnapshot : dataSnapshot.getChildren()) {
                                icardSnapshot.getRef().removeValue();
                                Toast.makeText(ViewStudentICardDetailsActivity.this, "ICard deleted successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ViewStudentICardDetailsActivity.this, "Error deleting iCard data", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void generatePDF() {
        Document document = new Document();

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Student_ICard.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            Font timesnewRoman=new Font(Font.FontFamily.TIMES_ROMAN);
            Font timesnewBoldRoman=new Font(Font.FontFamily.TIMES_ROMAN,25,Font.BOLD);
            Font timesnewBoldRomansub=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewRomanitelic=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.ITALIC);
            Font heleticaFont=new Font(Font.FontFamily.HELVETICA,35,Font.BOLD);
            cell.setBorderWidth(1); // Set border width

            // Add background image
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pdf_logo);
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] background = stream.toByteArray();
            Image backgroundImage = Image.getInstance(background);
            backgroundImage.scaleAbsolute(250,250);
            backgroundImage.setAbsolutePosition(180, 150);
            backgroundImage.setAlignment(Image.ALIGN_CENTER);
            document.add(backgroundImage);
            // Adding content to PDF
            Paragraph para1 = new Paragraph("Tarai Shikshan Sansth's",timesnewRoman);
            para1.getFont().setColor(255, 0, 0); // Red color
            para1.getFont().setSize(25);
            para1.setAlignment(Paragraph.ALIGN_CENTER);
            // document.add(para1);
            cell.addElement(para1);

            Paragraph para2 = new Paragraph("S.P. BAKLIWAL VIDYALAY",heleticaFont);
            para2.getFont().setColor(255, 0, 0); // Red color

            para2.getFont().setSize(35); // Text size 25
            para2.setAlignment(Paragraph.ALIGN_CENTER);
            para2.getFont().setStyle("bold"); // Bold
            /* document.add(para2);*/
            cell.addElement(para2);

            Paragraph para3 = new Paragraph("Theragon Tal Paithan Dist. Chha. Sambhajinagar",timesnewRomanitelic);
            para3.getFont().setColor(255, 0, 0); // Red color
            para3.setAlignment(Paragraph.ALIGN_CENTER);
            /* document.add(para3);*/
            cell.addElement(para3);


            Paragraph para4 = new Paragraph("      " + "                 UDISE NO. " + et_studentGeneralREGNoForICard.getText().toString()+timesnewRoman);
            para4.getFont().setColor(0, 0, 255); // Blue
            para4.getFont().setSize(15);
            /*document.add(para4);*/
            cell.addElement(para4);

            Paragraph para5 = new Paragraph("I-CARD 2023-24",timesnewBoldRoman);
            para5.getFont().setColor(0, 0, 0); // Black
            para5.setAlignment(Paragraph.ALIGN_CENTER);
            /*document.add(para5);*/
            cell.addElement(para5);
            cell.addElement(new Paragraph("\n"));
            /*document.add(new Paragraph(""));*/

            // Convert ImageView to Bitmap
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmapq = imageView.getDrawingCache();

            // Convert Bitmap to iText Image
            ByteArrayOutputStream strea1 = new ByteArrayOutputStream();
            bitmapq.compress(Bitmap.CompressFormat.PNG, 100, strea1);
            Image image = Image.getInstance(strea1.toByteArray());
            image.scaleToFit(200, 200);
            image.setAlignment(Image.ALIGN_CENTER);
            /*document.add(image);*/
            cell.addElement(image);
            cell.addElement(new Paragraph("\n\n"));
            Paragraph para6=new Paragraph("              Name: " + et_studentNameForICard.getText().toString(),timesnewBoldRomansub);

            /*document.add(para6);*/
            cell.addElement(para6);
            Paragraph para7=new Paragraph("              Standard: " + et_studentClassForICard.getText().toString()+"("+et_studentAcademicYearForICard.getText().toString()+")",timesnewBoldRomansub);
            /*document.add(para7);*/
            cell.addElement(para7);
            Paragraph para8=new Paragraph("              General No: " + et_studentGeneralREGNoForICard.getText().toString(),timesnewBoldRomansub);
            /*document.add(para8);*/
            cell.addElement(para8);
            Paragraph para9=new Paragraph("              Date of Birth: " + et_studentDOBForICard.getText().toString(),timesnewBoldRomansub);
            /*document.add(para9);*/
            cell.addElement(para9);
            Paragraph para10=new Paragraph("             Address: " + et_studentAddressForICard.getText().toString(),timesnewBoldRomansub);
            /*document.add(para10);*/

            cell.addElement(para10);

            Paragraph para11=new Paragraph("\n\n\n"+"         Student Sign "+"                                     Collage Stamp",timesnewRomanitelic);
            para11.setAlignment(Paragraph.ALIGN_CENTER);
            /*document.add(para11);*/
            cell.addElement(para11);
            cell.addElement(new Paragraph("\n"));


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
