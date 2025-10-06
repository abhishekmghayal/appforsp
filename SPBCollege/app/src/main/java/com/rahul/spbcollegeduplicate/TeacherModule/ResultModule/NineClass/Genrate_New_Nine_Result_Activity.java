package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.NineClass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahul.spbcollegeduplicate.R;

import java.util.HashMap;

public class Genrate_New_Nine_Result_Activity extends AppCompatActivity{

    EditText stud_UIDNO,stud_Gen_reg_no,stud_name,stud_std,stud_marathi_mark,stud_hindi_mark,stud_english_mark,stud_math_part_I_II_mark,
            stud_science_part_I_II_mark,stud_social_sci_mark,stud_phy_edu_grad,stud_conser_scie_grad,stud_self_dev_grade,stud_year_presenty;
    TextView stud_year_total,stud_per,stud_final_category,stud_grade,stud_result;

    private Button submitButton;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrate_new_nine__result);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Result...");
        progressDialog.setCancelable(false);

        setTitle("Add 9th Student Result");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("NineClassResult");

        submitButton = findViewById(R.id.btn_submit_resultofnine);

        stud_UIDNO = findViewById(R.id.et__ager_adharno);
        stud_Gen_reg_no = findViewById(R.id.et__ager_stdgernaralno);
        stud_name = findViewById(R.id.et__ager_stdname);
        stud_std = findViewById(R.id.et__ager_stdstandard);
        stud_marathi_mark = findViewById(R.id.et__ager_stdresultmarathi);
        stud_hindi_mark = findViewById(R.id.et__ager_stdresulthindi);
        stud_english_mark = findViewById(R.id.et__ager_stdresultenglish);
        stud_math_part_I_II_mark = findViewById(R.id.et__ager_stdresultMath);
        stud_science_part_I_II_mark = findViewById(R.id.et__ager_stdresultscience);
        stud_social_sci_mark = findViewById(R.id.et__ager_stdresultsocialscience);
        stud_year_total = findViewById(R.id.tv__ager_stdresultfinal_val);
        stud_per = findViewById(R.id.tv__ager_stdresultpercent_value);
        stud_final_category = findViewById(R.id.tv__ager_stdresultfinalcatg_value);
        stud_phy_edu_grad = findViewById(R.id.et__ager_stdresult_phy_edu);
        stud_conser_scie_grad = findViewById(R.id.et__ager_stdresultconservation_scie);
        stud_self_dev_grade = findViewById(R.id.et__ager_stdresultself_dev);
        stud_grade = findViewById(R.id.tv__ager_stdresultgrade_value);
        stud_year_presenty = findViewById(R.id.tv__ager_stdresullyearpresent_value);
        stud_result = findViewById(R.id.tv__ager_stdresullfinalresult_value);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditTextEmpty(stud_UIDNO)) {
                    stud_UIDNO.setError("Please Enter UID this field");
                } else if (isEditTextEmpty(stud_Gen_reg_no)) {
                    stud_Gen_reg_no.setError("Please Enter General Reg No");
                } else if (isEditTextEmpty(stud_name)) {
                    stud_name.setError("Please Name this");
                } else if (isEditTextEmpty(stud_std)) {
                    stud_std.setError("Please Enter Standard");
                } else if (isEditTextEmpty(stud_marathi_mark)) {
                    stud_marathi_mark.setError("Please Enter Marathi Subject Marks");
                } else if (isEditTextEmpty(stud_hindi_mark)) {
                    stud_hindi_mark.setError("Please Enter Hindi Subject Marks");
                } else if (isEditTextEmpty(stud_english_mark)) {
                    stud_english_mark.setError("Please Enter English Subject Marks");
                } else if (isEditTextEmpty(stud_math_part_I_II_mark)) {
                    stud_math_part_I_II_mark.setError("Please Enter Math Part I II Subject Marks");
                } else if (isEditTextEmpty(stud_science_part_I_II_mark)) {
                    stud_science_part_I_II_mark.setError("Please Enter Science Part I II Subject Marks");
                } else if (isEditTextEmpty(stud_social_sci_mark)) {
                    stud_social_sci_mark.setError("Please Enter Social Science Subject Marks");
                } else if (isEditTextEmpty(stud_phy_edu_grad)) {
                    stud_phy_edu_grad.setError("Please Enter Physical Education Grad Marks");
                } else if (isEditTextEmpty(stud_conser_scie_grad)) {
                    stud_conser_scie_grad.setError("Please Enter Conservation Science Experience Grad");
                } else if (isEditTextEmpty(stud_self_dev_grade)) {
                    stud_self_dev_grade.setError("Please Enter Self Development And Artistic Grad");
                }else if (isEditTextEmpty(stud_year_presenty)) {
                    stud_year_presenty.setError("Please Enter Student Presenty");
                } else {
                    submitData();
                }
            }
        });
    }

    private void submitData() {
        // All fields are filled
        double marathiMarks = Double.parseDouble(stud_marathi_mark.getText().toString());
        double hindiMarks = Double.parseDouble(stud_hindi_mark.getText().toString());
        double englishMarks = Double.parseDouble(stud_english_mark.getText().toString());
        double mathMarks = Double.parseDouble(stud_math_part_I_II_mark.getText().toString());
        double scienceMarks = Double.parseDouble(stud_science_part_I_II_mark.getText().toString());
        double socialScieMarks = Double.parseDouble(stud_social_sci_mark.getText().toString());

        double totalMarks = marathiMarks + hindiMarks + englishMarks + mathMarks + scienceMarks + socialScieMarks;
        double percentage = (totalMarks / 600) * 100;

        stud_year_total.setText(String.valueOf(totalMarks));
        stud_per.setText(String.valueOf(percentage));

        String finalCategory;
        if (percentage >= 90) {
            finalCategory = "A+";
        } else if (percentage >= 80) {
            finalCategory = "A";
        } else if (percentage >= 70) {
            finalCategory = "B+";
        } else if (percentage >= 60) {
            finalCategory = "B";
        } else if (percentage >= 50) {
            finalCategory = "C";
        } else {
            finalCategory = "D";
        }
        stud_final_category.setText(finalCategory);
        String artGrade = stud_phy_edu_grad.getText().toString();
        String workExpGrade = stud_conser_scie_grad.getText().toString();
        String phyGrade = stud_self_dev_grade.getText().toString();
        String overallGrade = calculateOverallGrade(artGrade, workExpGrade, phyGrade);
        stud_grade.setText(overallGrade);

        // Set result based on percentage
        String result;
        if (percentage >= 40) {
            result = "Pass";
        } else {
            result = "Fail";
        }
        stud_result.setText(result);

        // Show confirmation dialog
        showConfirmationDialog();
    }

    private String calculateOverallGrade(String artGrade, String workExpGrade, String phyGrade) {
        if (artGrade.equals("A+") || workExpGrade.equals("A+") || phyGrade.equals("A+")) {
            return "A+";
        } else if (artGrade.equals("A") || workExpGrade.equals("A") || phyGrade.equals("A")) {
            return "A";
        } else if (artGrade.equals("B+") || workExpGrade.equals("B+") || phyGrade.equals("B+")) {
            return "B+";
        } else if (artGrade.equals("B") || workExpGrade.equals("B") || phyGrade.equals("B")) {
            return "B";
        } else if (artGrade.equals("C") || workExpGrade.equals("C") || phyGrade.equals("C")) {
            return "C";
        } else {
            return "D"; // Default grade
        }
    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Store data to Firebase
                storeDataToFirebase();
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

    private void storeDataToFirebase() {
        progressDialog.show();
        // Create a HashMap to hold the data
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("UID", stud_UIDNO.getText().toString());
        dataMap.put("GeneralRegNo", stud_Gen_reg_no.getText().toString());
        dataMap.put("Name", stud_name.getText().toString());
        dataMap.put("Standard", stud_std.getText().toString());
        dataMap.put("MarathiMarks", Double.parseDouble(stud_marathi_mark.getText().toString()));
        dataMap.put("HindiMarks", Double.parseDouble(stud_hindi_mark.getText().toString()));
        dataMap.put("EnglishMarks", Double.parseDouble(stud_english_mark.getText().toString()));
        dataMap.put("MathPart_I_II_Marks", Double.parseDouble(stud_science_part_I_II_mark.getText().toString()));
        dataMap.put("SciencePart_I_II_Marks", Double.parseDouble(stud_science_part_I_II_mark.getText().toString()));
        dataMap.put("SocialScienceMarks", Double.parseDouble(stud_social_sci_mark.getText().toString()));
        dataMap.put("TotalMarks", Double.parseDouble(stud_year_total.getText().toString()));
        dataMap.put("Percentage", stud_per.getText().toString());
        dataMap.put("FinalCategory", stud_final_category.getText().toString());
        dataMap.put("PhysicalEducationGrade", stud_phy_edu_grad.getText().toString());
        dataMap.put("ConservationScienceGrade", stud_conser_scie_grad.getText().toString());
        dataMap.put("SelfDevelopmentGrade", stud_self_dev_grade.getText().toString());
        dataMap.put("OverallGrade", stud_grade.getText().toString());
        dataMap.put("YearPersenty", stud_year_presenty.getText().toString());
        dataMap.put("Result", stud_result.getText().toString());

        String genregNo = stud_Gen_reg_no.getText().toString().trim();
        // Push the HashMap to the Firebase Realtime Database
        mDatabase.child(genregNo).setValue(dataMap);

        progressDialog.dismiss();
        Toast.makeText(Genrate_New_Nine_Result_Activity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Genrate_New_Nine_Result_Activity.this, Nine_Class_Result.class);
        startActivity(intent);
        finish();
    }
}
