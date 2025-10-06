package com.rahul.spbcollegeduplicate.TeacherModule.ResultModule.EightClass;

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

public class Genrate_New_Eight_Activity extends AppCompatActivity{

    EditText stud_UIDNO,stud_Gen_reg_no,stud_name,stud_std,stud_marathi_mark,stud_hindi_mark,stud_english_mark,stud_math_mark,
            stud_science_mark,stud_history_mark,stud_art_grad,stud_workExp_grad,stud_phy_grade,stud_year_presenty;
    TextView stud_year_total,stud_per,stud_final_category,stud_grade,stud_result;

    private Button submitButton;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrate_new_eight_result);

        setTitle("Add 8th Student Result");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Result...");
        progressDialog.setCancelable(false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("EightclassResult");

        submitButton = findViewById(R.id.btn_submit_resultofEight);

        stud_UIDNO = findViewById(R.id.et__ager_adharno);
        stud_Gen_reg_no = findViewById(R.id.et__ager_stdgernaralno);
        stud_name = findViewById(R.id.et__ager_stdname);
        stud_std = findViewById(R.id.et__ager_stdstandard);
        stud_marathi_mark = findViewById(R.id.et__ager_stdresultmarathi);
        stud_hindi_mark = findViewById(R.id.et__ager_stdresulthindi);
        stud_english_mark = findViewById(R.id.et__ager_stdresultenglish);
        stud_math_mark = findViewById(R.id.et__ager_stdresultMath);
        stud_science_mark = findViewById(R.id.et__ager_stdresultscience);
        stud_history_mark = findViewById(R.id.et__ager_stdresulthistroygeo);
        stud_year_total = findViewById(R.id.tv__ager_stdresultfinal_val);
        stud_per = findViewById(R.id.tv__ager_stdresultpercent_value);
        stud_final_category = findViewById(R.id.tv__ager_stdresultfinalcatg_value);
        stud_art_grad = findViewById(R.id.et__ager_stdresultart);
        stud_workExp_grad = findViewById(R.id.et__ager_stdresultworkexpirence);
        stud_phy_grade = findViewById(R.id.et__ager_stdresultphyscailandhealt);
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
                } else if (isEditTextEmpty(stud_math_mark)) {
                    stud_math_mark.setError("Please Enter Math Subject Marks");
                } else if (isEditTextEmpty(stud_science_mark)) {
                    stud_science_mark.setError("Please Enter Science Subject Marks");
                } else if (isEditTextEmpty(stud_history_mark)) {
                    stud_history_mark.setError("Please Enter History Subject Marks");
                } else if (isEditTextEmpty(stud_art_grad)) {
                    stud_art_grad.setError("Please Enter Art Grad Marks");
                } else if (isEditTextEmpty(stud_workExp_grad)) {
                    stud_workExp_grad.setError("Please Enter Work Experience Grad");
                } else if (isEditTextEmpty(stud_phy_grade)) {
                    stud_phy_grade.setError("Please Enter Physical And Health Education Grad");
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
        double mathMarks = Double.parseDouble(stud_math_mark.getText().toString());
        double scienceMarks = Double.parseDouble(stud_science_mark.getText().toString());
        double historyMarks = Double.parseDouble(stud_history_mark.getText().toString());

        double totalMarks = marathiMarks + hindiMarks + englishMarks + mathMarks + scienceMarks + historyMarks;
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
        String artGrade = stud_art_grad.getText().toString();
        String workExpGrade = stud_workExp_grad.getText().toString();
        String phyGrade = stud_phy_grade.getText().toString();
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
        dataMap.put("MathMarks", Double.parseDouble(stud_math_mark.getText().toString()));
        dataMap.put("ScienceMarks", Double.parseDouble(stud_science_mark.getText().toString()));
        dataMap.put("HistoryMarks", Double.parseDouble(stud_history_mark.getText().toString()));
        dataMap.put("TotalMarks", Double.parseDouble(stud_year_total.getText().toString()));
        dataMap.put("Percentage", stud_per.getText().toString());
        dataMap.put("FinalCategory", stud_final_category.getText().toString());
        dataMap.put("ArtGrade", stud_art_grad.getText().toString());
        dataMap.put("WorkExpGrade", stud_workExp_grad.getText().toString());
        dataMap.put("PhyGrade", stud_phy_grade.getText().toString());
        dataMap.put("OverallGrade", stud_grade.getText().toString());
        dataMap.put("YearPersenty", stud_year_presenty.getText().toString());
        dataMap.put("Result", stud_result.getText().toString());

        String genregNo = stud_Gen_reg_no.getText().toString().trim();
        // Push the HashMap to the Firebase Realtime Database
        mDatabase.child(genregNo).setValue(dataMap);

        progressDialog.dismiss();
        Toast.makeText(Genrate_New_Eight_Activity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Genrate_New_Eight_Activity.this, Eight_Class_Result.class);
        startActivity(intent);
        finish();
    }
}
