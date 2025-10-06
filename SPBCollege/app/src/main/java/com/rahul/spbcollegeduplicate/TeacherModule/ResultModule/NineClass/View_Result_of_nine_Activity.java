package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.NineClass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

public class View_Result_of_nine_Activity extends AppCompatActivity {

    EditText stud_UIDNO,stud_Gen_reg_no,stud_name,stud_std,stud_marathi_mark,stud_hindi_mark,stud_english_mark,stud_math_part_I_II_mark,
            stud_science_part_I_II_mark,stud_social_sci_mark,stud_phy_edu_grad,stud_conser_scie_grad,stud_self_dev_grade;
    EditText stud_year_total, stud_per, stud_final_category, stud_grade, stud_year_presenty, stud_result;
    private Button updateButton;
    private Button deleteButton;
    String GenRegNo;
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    ImageButton createPDF;
    String[] totalMarkscol;
    String[] subjectOfNine;
    EditText[] obtainedMarksEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_of_nine);

        setTitle("View 9th Student Result");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("NineClassResult");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Intent intent = getIntent();
        if (intent != null) {
            GenRegNo = intent.getStringExtra("GeneralRegNo");
        }

        fetchDataFromFirebase();

        updateButton = findViewById(R.id.btn__avron_update_ninereuslt);
        deleteButton = findViewById(R.id.btn__avron_delete_ninereuslt);

        createPDF = findViewById(R.id.pdf);
        stud_UIDNO = findViewById(R.id.et__avron_stdadharno);
        stud_Gen_reg_no = findViewById(R.id.et__avron_stdgenralno);
        stud_name = findViewById(R.id.et__avron_stdname);
        stud_std = findViewById(R.id.et__avron_stdstandard);
        stud_marathi_mark = findViewById(R.id.et__avron_stdmarathisub);
        stud_hindi_mark = findViewById(R.id.et__avron_stdhindisub);
        stud_english_mark = findViewById(R.id.et__avron_stdmenglishsub);
        stud_math_part_I_II_mark = findViewById(R.id.et__avron_stdmath);
        stud_science_part_I_II_mark = findViewById(R.id.et__avron_stdsciencesub);
        stud_social_sci_mark = findViewById(R.id.et__avron_stdsocialscienesub);
        stud_year_total = findViewById(R.id.et__avron_stdyeartotal);
        stud_per = findViewById(R.id.et__avron_stdpercentage);
        stud_final_category = findViewById(R.id.et__avron_stdfinalcategory);
        stud_phy_edu_grad = findViewById(R.id.et__avron_stdphyscailedusub);
        stud_conser_scie_grad = findViewById(R.id.et__avron_stdconservaionsciencesub);
        stud_self_dev_grade = findViewById(R.id.et__avron_stdselfdevelopmentandarticsub);
        stud_grade = findViewById(R.id.et__avron_stdgradee);
        stud_year_presenty = findViewById(R.id.et__avron_stdyearpresent);
        stud_result = findViewById(R.id.et__avron_stdreuslt);

        createPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });

        // Disable EditText fields initially
        disableEditTexts();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if EditText fields are enabled
                if (stud_UIDNO.isEnabled()) {
                    // EditText fields are enabled, save changes
                    updateDataInFirebase();
                    disableEditTexts(); // Disable EditText fields
                    updateButton.setText("Update"); // Change button text to "Update"
                } else {
                    // EditText fields are disabled, enable for editing
                    enableEditTexts();
                    updateButton.setText("Save Changes"); // Change button text to "Save Changes"
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("Do you want to delete?");
            }
        });
    }

    private void fetchDataFromFirebase() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        mDatabase.child(GenRegNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data as HashMap
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    // Set data to EditText fields
                    if (dataMap != null) {
                        stud_UIDNO.setText(String.valueOf(dataMap.get("UID")));
                        stud_Gen_reg_no.setText(String.valueOf(dataMap.get("GeneralRegNo")));
                        stud_name.setText(String.valueOf(dataMap.get("Name")));
                        stud_std.setText(String.valueOf(dataMap.get("Standard")));
                        stud_marathi_mark.setText(String.valueOf(dataMap.get("MarathiMarks")));
                        stud_hindi_mark.setText(String.valueOf(dataMap.get("HindiMarks")));
                        stud_english_mark.setText(String.valueOf(dataMap.get("EnglishMarks")));
                        stud_math_part_I_II_mark.setText(String.valueOf(dataMap.get("MathPart_I_II_Marks")));
                        stud_science_part_I_II_mark.setText(String.valueOf(dataMap.get("SciencePart_I_II_Marks")));
                        stud_social_sci_mark.setText(String.valueOf(dataMap.get("SocialScienceMarks")));
                        stud_year_total.setText(String.valueOf(dataMap.get("TotalMarks")));
                        stud_per.setText(String.valueOf(dataMap.get("Percentage")));
                        stud_final_category.setText(String.valueOf(dataMap.get("FinalCategory")));
                        stud_phy_edu_grad.setText(String.valueOf(dataMap.get("PhysicalEducationGrade")));
                        stud_conser_scie_grad.setText(String.valueOf(dataMap.get("ConservationScienceGrade")));
                        stud_self_dev_grade.setText(String.valueOf(dataMap.get("SelfDevelopmentGrade")));
                        stud_grade.setText(String.valueOf(dataMap.get("OverallGrade")));
                        stud_year_presenty.setText(String.valueOf(dataMap.get("YearPersenty")));
                        stud_result.setText(String.valueOf(dataMap.get("Result")));
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    // Handle if data doesn't exist for the given GenRegNo
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                // Handle error
            }
        });
    }

    private void showConfirmationDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (message.equals("Do you want to update?")) {
                    // Handle update action
                    if (stud_UIDNO.isEnabled()) {
                        updateDataInFirebase();
                        disableEditTexts();
                        updateButton.setText("Update");
                    } else {
                        enableEditTexts();
                        updateButton.setText("Save Changes");
                    }
                } else if (message.equals("Do you want to delete?")) {
                    // Handle delete action
                    deleteDataFromFirebase();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(View_Result_of_nine_Activity.this, "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    private void updateDataInFirebase() {
        progressDialog.setMessage("Updating Result...");
        progressDialog.show();
        // Get updated data from EditText fields
        String updatedUID = stud_UIDNO.getText().toString();
        String updatedGenRegNo = stud_Gen_reg_no.getText().toString();
        String updatedName = stud_name.getText().toString();
        String updatedStd = stud_std.getText().toString();
        double updatedMarathiMarks = Double.parseDouble(stud_marathi_mark.getText().toString());
        double updatedHindiMarks = Double.parseDouble(stud_hindi_mark.getText().toString());
        double updatedEnglishMarks = Double.parseDouble(stud_english_mark.getText().toString());
        double updatedMathMarks = Double.parseDouble(stud_math_part_I_II_mark.getText().toString());
        double updatedScienceMarks = Double.parseDouble(stud_science_part_I_II_mark.getText().toString());
        double updatedSocialScienceMarks = Double.parseDouble(stud_social_sci_mark.getText().toString());
        double updatedTotalMarks = Double.parseDouble(stud_year_total.getText().toString());
        String updatedPercentage = stud_per.getText().toString();
        String updatedFinalCategory = stud_final_category.getText().toString();
        String updatedPhyEduGrade = stud_phy_edu_grad.getText().toString();
        String updatedConservationScienceGrade = stud_conser_scie_grad.getText().toString();
        String updatedSelfDevelopmentGrade = stud_self_dev_grade.getText().toString();
        String updatedGrade = stud_grade.getText().toString();
        String updatedYearPresenty = stud_year_presenty.getText().toString();
        String updatedResult = stud_result.getText().toString();

        // Update data in Firebase
        HashMap<String, Object> newData = new HashMap<>();
        newData.put("UID", updatedUID);
        newData.put("GeneralRegNo", updatedGenRegNo);
        newData.put("Name", updatedName);
        newData.put("Standard", updatedStd);
        newData.put("MarathiMarks", updatedMarathiMarks);
        newData.put("HindiMarks", updatedHindiMarks);
        newData.put("EnglishMarks", updatedEnglishMarks);
        newData.put("MathPart_I_II_Marks", updatedMathMarks);
        newData.put("SciencePart_I_II_Marks", updatedScienceMarks);
        newData.put("SocialScienceMarks", updatedSocialScienceMarks);
        newData.put("TotalMarks", updatedTotalMarks);
        newData.put("Percentage", updatedPercentage);
        newData.put("FinalCategory", updatedFinalCategory);
        newData.put("PhysicalEducationGrade", updatedPhyEduGrade);
        newData.put("ConservationScienceGrade", updatedConservationScienceGrade);
        newData.put("SelfDevelopmentGrade", updatedSelfDevelopmentGrade);
        newData.put("OverallGrade", updatedGrade);
        newData.put("YearPersenty", updatedYearPresenty);
        newData.put("Result", updatedResult);

        mDatabase.child(GenRegNo).setValue(newData);

        progressDialog.dismiss();
        // Display toast message
        Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void deleteDataFromFirebase() {
        progressDialog.setMessage("Deleting Result...");
        progressDialog.show();
        // Delete data from Firebase
        mDatabase.child(GenRegNo).removeValue();

        // Display toast message
        progressDialog.dismiss();
        Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(View_Result_of_nine_Activity.this,Nine_Class_Result.class);
        startActivity(intent);
        finish();
        // Optionally, navigate to another activity or perform any other action after deletion
    }

    private void enableEditTexts() {
        // Enable all EditText fields
        stud_UIDNO.setEnabled(true);
        stud_Gen_reg_no.setEnabled(true);
        stud_name.setEnabled(true);
        stud_std.setEnabled(true);
        stud_marathi_mark.setEnabled(true);
        stud_hindi_mark.setEnabled(true);
        stud_english_mark.setEnabled(true);
        stud_math_part_I_II_mark.setEnabled(true);
        stud_science_part_I_II_mark.setEnabled(true);
        stud_social_sci_mark.setEnabled(true);
        stud_year_total.setEnabled(true);
        stud_per.setEnabled(true);
        stud_final_category.setEnabled(true);
        stud_phy_edu_grad.setEnabled(true);
        stud_conser_scie_grad.setEnabled(true);
        stud_self_dev_grade.setEnabled(true);
        stud_grade.setEnabled(true);
        stud_year_presenty.setEnabled(true);
        stud_result.setEnabled(true);
    }

    private void disableEditTexts() {
        // Disable all EditText fields
        stud_UIDNO.setEnabled(false);
        stud_Gen_reg_no.setEnabled(false);
        stud_name.setEnabled(false);
        stud_std.setEnabled(false);
        stud_marathi_mark.setEnabled(false);
        stud_hindi_mark.setEnabled(false);
        stud_english_mark.setEnabled(false);
        stud_math_part_I_II_mark.setEnabled(false);
        stud_science_part_I_II_mark.setEnabled(false);
        stud_social_sci_mark.setEnabled(false);
        stud_year_total.setEnabled(false);
        stud_per.setEnabled(false);
        stud_final_category.setEnabled(false);
        stud_phy_edu_grad.setEnabled(false);
        stud_conser_scie_grad.setEnabled(false);
        stud_self_dev_grade.setEnabled(false);
        stud_grade.setEnabled(false);
        stud_year_presenty.setEnabled(false);
        stud_result.setEnabled(false);
    }


    private void generatePDF() {
        Document document = new Document();

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "student_report_NineMarksheet_.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();



            //Fonts
            Font timesnewRoman=new Font(Font.FontFamily.TIMES_ROMAN);
            Font timesnewBoldRoman=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewBoldRomansub=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Font timesnewBoldRomansmall=new Font(Font.FontFamily.TIMES_ROMAN,15);
            Font timesnewRomanitelic=new Font(Font.FontFamily.TIMES_ROMAN,13,Font.ITALIC);
            Font heleticaFont=new Font(Font.FontFamily.HELVETICA,25,Font.BOLD);


            // Add background image
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
            para1.getFont().setColor(255, 0, 0); // Red color
            para1.getFont().setSize(15);
            para1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para1);


            Paragraph para2 = new Paragraph("S.P. BAKLIWAL VIDYALAY,Theragon",heleticaFont);
            para2.getFont().setColor(255, 0, 0); // Red color

            para2.getFont().setSize(23); // Text size 25
            para2.setAlignment(Paragraph.ALIGN_CENTER);
            para2.getFont().setStyle("bold"); // Bold
            document.add(para2);


            Paragraph para3 = new Paragraph("Theragon Tal Paithan Dist. Chha. Sambhajinagar",timesnewRomanitelic);
            para3.getFont().setColor(255, 0, 0); // Red color
            para3.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para3);
            Paragraph para4 = new Paragraph(" Compiled Result Letter ",timesnewBoldRoman);
            para4.getFont().setColor(0, 0, 0); // Black
            para4.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para4);
            Paragraph para5=new Paragraph(" ");
            para5.getFont().setSize(10);
            document.add(para5);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(80f);
            lineSeparator.setAlignment(Element.ALIGN_CENTER);
            lineSeparator.setLineColor(BaseColor.RED); // Set color to black
            document.add(lineSeparator);

            Paragraph para6=new Paragraph("           Aadhar Id : "+stud_UIDNO.getText().toString()+"                             General Reg. No. : "+stud_Gen_reg_no.getText().toString(),timesnewBoldRomansmall);
            document.add(para6);
            document.add(para5);
            Paragraph para7=new Paragraph("             Student Name : "+stud_name.getText().toString()+".",timesnewBoldRomansmall);
            para7.getFont().setStyle(Font.BOLD);
            document.add(para7);
            Paragraph para8=new Paragraph("             Student Standard : "+stud_std.getText().toString()+".",timesnewBoldRomansmall);
            para8.getFont().setStyle(Font.BOLD);
            document.add(para8);

            document.add(new Paragraph("\n"));
            //table Of Percentage and subject
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(90);
            // Add table headers'


            PdfPCell subjcell=new PdfPCell(new Paragraph("Subject",timesnewBoldRomansmall));
            subjcell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            subjcell.setPadding(5);
            subjcell.setBackgroundColor(BaseColor.GRAY);
            table.addCell(subjcell);

            PdfPCell totalmarcell=new PdfPCell(new Paragraph("Total Marks",timesnewBoldRomansmall));
            totalmarcell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            totalmarcell.setPadding(5);
            totalmarcell.setBackgroundColor(BaseColor.GRAY);
            table.addCell(totalmarcell);

            PdfPCell obmarcell=new PdfPCell(new Paragraph("Obtained Marks",timesnewBoldRomansmall));
            obmarcell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            obmarcell.setPadding(5);
            obmarcell.setBackgroundColor(BaseColor.GRAY);
            table.addCell(obmarcell);


            subjectOfNine=new String[]{"Marathi","Hindi","English","Math \nPart I-II","Science \nPart I-II","Social Science \nPart I-II","Annual Total","Hundred Points","Final Category","Physical Education","Self Development \n And Artistic Appreciation","Conservation Science","      ","Annual Hundred \n Attendance","Result"};


            totalMarkscol= new String[]{"100","100","100","100","100","100","600","   ","   ","Grade","Grade","Grade","Grade","   ","   ",};
            obtainedMarksEditTexts=new EditText[]{findViewById(R.id.et__avron_stdmarathisub),findViewById(R.id.et__avron_stdhindisub)
                    ,
                    findViewById(R.id.et__avron_stdmenglishsub),findViewById(R.id.et__avron_stdmath)
                    ,        findViewById(R.id.et__avron_stdsciencesub),findViewById(R.id.et__avron_stdsocialscienesub),
                    findViewById(R.id.et__avron_stdyeartotal),findViewById(R.id.et__avron_stdpercentage),
                    findViewById(R.id.et__avron_stdfinalcategory),findViewById(R.id.et__avron_stdphyscailedusub),
                    findViewById(R.id.et__avron_stdconservaionsciencesub),findViewById(R.id.et__avron_stdselfdevelopmentandarticsub),
                    findViewById(R.id.et__avron_stdgradee),findViewById(R.id.et__avron_stdyearpresent),findViewById(R.id.et__avron_stdreuslt)};
            // Add rows for each subject
            for (int i = 0; i < totalMarkscol.length; i++) {
                PdfPCell subjectCell = new PdfPCell(new Paragraph(subjectOfNine[i], timesnewBoldRomansmall));
                subjectCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(subjectCell);

                PdfPCell totalMarksCell = new PdfPCell(new Paragraph(totalMarkscol[i], timesnewBoldRomansmall));
                totalMarksCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(totalMarksCell);

                PdfPCell obtainedMarksCell = new PdfPCell(new Paragraph(obtainedMarksEditTexts[i].getText().toString(), timesnewBoldRomansmall));
                obtainedMarksCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(obtainedMarksCell);
            }
            document.add(table);

            Paragraph para23=new Paragraph("\n     Class Teacher Sign.                    Clerk Sign.              Head Master Sign.",timesnewBoldRomansmall);
            para23.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(para23);

            document.add(para5);
            LineSeparator lineSeparator1 = new LineSeparator();
            lineSeparator1.setPercentage(80f);
            lineSeparator1.setAlignment(Element.ALIGN_CENTER);
            lineSeparator1.setLineColor(BaseColor.BLACK); // Set color to black
            document.add(lineSeparator1);









            document.close();

            // PDF generation completed
            Toast.makeText(this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
