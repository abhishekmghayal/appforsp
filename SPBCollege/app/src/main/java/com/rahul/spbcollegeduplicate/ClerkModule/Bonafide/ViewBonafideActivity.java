package com.rahul.spbcollegeduplicate.ClerkModule.Bonafide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ViewBonafideActivity extends AppCompatActivity {

    TextView tv_Student_Name, tv_student_dob, tv_student_dob_in_letter, tv_student_learning_year,
            tv_student_cast, tv_student_learning_std, tv_student_birth_place;
    Button btn_delete_bonafide,btn_print_bonafide;
    String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_bonafide);

        setTitle("View Bonafide Certificate");

        tv_Student_Name = findViewById(R.id.tv_student_name);
        tv_student_dob = findViewById(R.id.tv_student_dob);
        tv_student_dob_in_letter = findViewById(R.id.tv_student_dob_letter);
        tv_student_learning_year = findViewById(R.id.tv_student_learning_year);
        tv_student_cast = findViewById(R.id.tv_student_cast);
        tv_student_learning_std = findViewById(R.id.tv_student_standard);
        tv_student_birth_place = findViewById(R.id.tv_student_birth_place);

        btn_delete_bonafide = findViewById(R.id.view_bonafide_delete_bonafide_btn);
        btn_print_bonafide = findViewById(R.id.view_bonafide_print_bonafide_btn);

        Intent intent = getIntent();
        if (intent != null) {
            studentName = intent.getStringExtra("StudentName");
            retriveStudentBonafide(studentName);
        }

        btn_delete_bonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDialogForDelete(view);
            }
        });
        btn_print_bonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });
    }
    public void generatePDF() {
        Document document = new Document();

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Student_Bonafide_Certificate.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setBorderWidth(1); // Set border width
            cell.setPadding(50); // Set padding to create space between border and content
            Font timesnewRoman=new Font(Font.FontFamily.TIMES_ROMAN);
            Font timesnewRomansmall=new Font(Font.FontFamily.TIMES_ROMAN,18);
            Font timesnewBoldRoman=new Font(Font.FontFamily.TIMES_ROMAN,25,Font.BOLD);
            Font timesnewBoldRomansub=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewRomanitelic=new Font(Font.FontFamily.TIMES_ROMAN,17,Font.ITALIC);
            Font heleticaFont=new Font(Font.FontFamily.HELVETICA,30,Font.BOLD);

            // Add background image
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pdf_logo);
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] background = stream.toByteArray();
            Image backgroundImage = Image.getInstance(background);
            backgroundImage.scaleAbsolute(200,200);
            backgroundImage.setAbsolutePosition(210, 400);
            backgroundImage.setAlignment(Image.ALIGN_CENTER);
            document.add(backgroundImage);
            // Adding content to PDF
            Paragraph para1 = new Paragraph("Tarai Shikshan Sansth's Paithan",timesnewRoman);
            para1.getFont().setColor(255, 0, 0); // Red color
            para1.getFont().setSize(25);
            para1.setAlignment(Paragraph.ALIGN_CENTER);
            // document.add(para1);
            cell.addElement(para1);

            Paragraph para2 = new Paragraph("S.P. BAKLIWAL VIDYALAY,Thergaon",heleticaFont);
            para2.getFont().setColor(255, 0, 0); // Red color

            para2.getFont().setSize(33); // Text size 25
            para2.setAlignment(Paragraph.ALIGN_CENTER);
            para2.getFont().setStyle("bold"); // Bold
            /* document.add(para2);*/
            cell.addElement(para2);

            Paragraph para3 = new Paragraph("Theragon Tal Paithan Dist. Chha. Sambhajinagar",timesnewRomanitelic);
            para3.getFont().setColor(255, 0, 0); // Red color
            para3.setAlignment(Paragraph.ALIGN_CENTER);
            /* document.add(para3);*/
            cell.addElement(para3);



            Paragraph para5 = new Paragraph("Bonafide Certificate",timesnewBoldRoman);
            para5.getFont().setColor(0, 0, 0); // Black
            para5.setAlignment(Paragraph.ALIGN_CENTER);
            /*document.add(para5);*/
            cell.addElement(para5);
            cell.addElement(new Paragraph("\n"));
            /*document.add(new Paragraph(""));*/
            Paragraph para6=new Paragraph("         It is Certified that, Kumar/Kumari is "+tv_Student_Name.getText().toString()+"."+" This student in this school "+tv_student_learning_year.getText().toString()+"" +
                    " in the year "+tv_student_learning_std.getText().toString()+". His/her date of birth as per school record is "+tv_student_dob.getText().toString()+
                    " literally "+tv_student_dob_in_letter.getText().toString()+" And There cast is "+tv_student_cast.getText().toString()+". ",timesnewRomansmall);



            cell.addElement(para6);
            cell.addElement(new Paragraph("Birthplace is "+tv_student_birth_place.getText().toString()+".",timesnewRomansmall));
            cell.addElement(new Paragraph("A habitation certificate is issued.",timesnewRomansmall));
            Paragraph para11=new Paragraph("\n\n"+"    College Head\n     Master Sign "+"                                        Collage Stamp",timesnewRomanitelic);
            /*document.add(para11);*/
            cell.addElement(para11);
            cell.addElement(new Paragraph("\n"));
            table.addCell(cell);
            document.add(table);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);


            document.close();

            // PDF generation completed
            Toast.makeText(this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showConfirmationDialogForDelete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewBonafideActivity.this, "Deletion confirmed", Toast.LENGTH_SHORT).show();
                deteleStudentBonafide(view);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewBonafideActivity.this, "Deletion cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void deteleStudentBonafide(View view) {
        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("Student's_Bonafide_Record").child(studentName);

        studentReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ViewBonafideActivity.this, "Student Bonafide deleted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after deletion
            } else {
                Toast.makeText(ViewBonafideActivity.this, "Failed to delete Student Bonafide", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retriveStudentBonafide(String studentName) {
        DatabaseReference clerkReference = FirebaseDatabase.getInstance().getReference("Student's_Bonafide_Record").child(studentName);

        clerkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve data and set it to TextViews
                    String studentFullName = snapshot.child("studentName").getValue(String.class);
                    String studentDOB = snapshot.child("studentDOB").getValue(String.class);
                    String studentDOBInLetter = snapshot.child("studentDOBInLetter").getValue(String.class);
                    String studentLearningYear = snapshot.child("studentLearningYear").getValue(String.class);
                    String studentCast = snapshot.child("studentCaste").getValue(String.class);
                    String studentStandard = snapshot.child("studentLearningStandard").getValue(String.class);
                    String studentBirthPlace = snapshot.child("studentBirthPlace").getValue(String.class);

                    tv_Student_Name.setText(studentFullName);
                    tv_student_dob.setText(studentDOB);
                    tv_student_dob_in_letter.setText(studentDOBInLetter);
                    tv_student_learning_year.setText(studentLearningYear);
                    tv_student_cast.setText(studentCast);
                    tv_student_learning_std.setText(studentStandard);
                    tv_student_birth_place.setText(studentBirthPlace);
                } else {
                    Toast.makeText(ViewBonafideActivity.this, "Student Bonafide Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewBonafideActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
