package com.rahul.spbcollegeduplicate.ClerkModule.Admission.InputingData;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rahul.spbcollegeduplicate.R;

import java.util.HashMap;
import java.util.UUID;

public class Clerk_Admission_Parent_Info_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText et_stud_parent_full_name, et_parent_relation, et_stud_parent_cast, et_parent_temp_address, et_parent_perm_address, et_parent_telephone_no,
            et_parent_mo_no, et_parent_business, et_parent_last_income;
    Spinner sp_minority;
    RadioGroup radioGroupMinority;
    RadioGroup radioGroupEconomical;
    RadioButton radioButtonYes;
    String selectedMinority, selected_economical_weaker;
    String minority_caste;
    Button submitButton;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Uri imageUri;
    ProgressDialog progressDialog2;
    String admissionClass, admissionNo, admissionDate, studentId, adharNo, sname, motherName, cast, religion,
            birthPlace, dobNo, dobInLetter, sAge, motherTung, sClassPassed, sPassingGrade, sLastSchoolName,
            bankName, bankIFSC, bankNo, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clerk__admission__parent__info_, container, false);

        progressDialog2 = new ProgressDialog(getContext());
        progressDialog2.setCancelable(false);
        progressDialog2.setMessage("Generating Admission Form");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admissionData");

        radioGroupMinority = view.findViewById(R.id.rdg_admision_parent_Info_minotrity);
        radioButtonYes = view.findViewById(R.id.radioButtonYes);
        radioGroupEconomical = view.findViewById(R.id.rdg_admision_parent_Info_Economical);

        et_stud_parent_full_name = view.findViewById(R.id.et_admission_Parent_Info_pfName);
        et_parent_relation = view.findViewById(R.id.et_admission_Parent_Info_relationToP);
        et_stud_parent_cast = view.findViewById(R.id.et__aS_admission_cast);
        et_parent_temp_address = view.findViewById(R.id.et_admission_Parent_Info_temp_add);
        et_parent_perm_address = view.findViewById(R.id.et_admission_Parent_Info_prem_add);
        et_parent_telephone_no = view.findViewById(R.id.et_admission_Parent_Info_tele_no);
        et_parent_mo_no = view.findViewById(R.id.et_admission_Parent_Info_Mobile_no);
        et_parent_business = view.findViewById(R.id.et_admission_Parent_Info_Bussiness);
        et_parent_last_income = view.findViewById(R.id.et_admission_Parent_Info_last_income);

        submitButton = view.findViewById(R.id.btn_std_parent_admission_submit);

        // Receive data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            admissionClass = bundle.getString("admission_class");
            admissionNo = bundle.getString("admission_no");
            admissionDate = bundle.getString("admission_date");
            studentId = bundle.getString("student_id");
            adharNo = bundle.getString("AdharNO");
            sname = bundle.getString("sname");
            motherName = bundle.getString("MotherName");
            cast = bundle.getString("cast");
            religion = bundle.getString("religon");
            birthPlace = bundle.getString("BirthPlace");
            dobNo = bundle.getString("DOBno");
            dobInLetter = bundle.getString("DOBinletter");
            sAge = bundle.getString("sAge");
            motherTung = bundle.getString("mothertung");
            sClassPassed = bundle.getString("SclassPassed");
            sPassingGrade = bundle.getString("SPassingGrade");
            sLastSchoolName = bundle.getString("SlastSchoolname");
            bankName = bundle.getString("bankname");
            bankIFSC = bundle.getString("bankIFsC");
            bankNo = bundle.getString("bankNo");
            gender = bundle.getString("gender");
            imageUri = bundle.getParcelable("imageUri");
        }

        radioGroupEconomical.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                if (radioButton != null) {
                    selected_economical_weaker = radioButton.getText().toString();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_stud_parent_full_name.getText().toString().isEmpty()) {
                    et_stud_parent_full_name.setError("Enter Parent Full Name");
                } else if (et_parent_relation.getText().toString().isEmpty()) {
                    et_parent_relation.setError("Enter Relation With Student");
                } else if (et_stud_parent_cast.getText().toString().isEmpty()) {
                    et_stud_parent_cast.setError("Enter Caste");
                } else if (et_parent_temp_address.getText().toString().isEmpty()) {
                    et_parent_temp_address.setError("Enter Temporary Address");
                } else if (et_parent_perm_address.getText().toString().isEmpty()) {
                    et_parent_perm_address.setError("Enter Permanent Address");
                } else if (et_parent_telephone_no.getText().toString().isEmpty()) {
                    et_parent_telephone_no.setError("Enter Telephone No");
                } else if (et_parent_mo_no.getText().toString().isEmpty()) {
                    et_parent_mo_no.setError("Enter Mobile No");
                } else if (et_parent_business.getText().toString().isEmpty()) {
                    et_parent_business.setError("Enter Business");
                } else if (et_parent_last_income.getText().toString().isEmpty()) {
                    et_parent_last_income.setError("Enter Last Year Income");
                } else {
                    showConfirmationDialog();
                }
            }
        });

        sp_minority = view.findViewById(R.id.sp_admission_Parent_Info_miniroity);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.miniroity_item, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        sp_minority.setAdapter(adapter);

        sp_minority.setVisibility(View.GONE);

        radioGroupMinority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonYes) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null) {
                        selectedMinority = radioButton.getText().toString();
                    }
                    sp_minority.setVisibility(View.VISIBLE);
                } else {
                    sp_minority.setVisibility(View.GONE);
                }
            }
        });

        sp_minority.setOnItemSelectedListener(this);
        return view;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (imageUri != null) {
                    uploadPhoto(imageUri);
                } else {
                    addStudentAdmission(null);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Submission cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void uploadPhoto(Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Image download URL generated, save it to the database
                        String imageUrl = uri.toString();
                        addStudentAdmission(imageUrl);
                    }).addOnFailureListener(e -> {
                        // Handle failure to get download URL
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Failed to get image download URL", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle unsuccessful image upload
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valueFromSpinner = parent.getItemAtPosition(position).toString();
        if (radioButtonYes.isChecked()) {
            minority_caste = valueFromSpinner;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addStudentAdmission(String imageUrl) {
        // Other fields...


        HashMap<String, Object> admissionData = new HashMap<>();
        admissionData.put("stud_admission_no", admissionNo);
        admissionData.put("stud_admission_class", admissionClass);
        admissionData.put("stud_admission_date", admissionDate);
        admissionData.put("student_id", studentId);
        admissionData.put("student_UID", adharNo);
        admissionData.put("student_full_name", sname);
        admissionData.put("student_mother_name", motherName);
        admissionData.put("student_caste", cast);
        admissionData.put("student_religion", religion);
        admissionData.put("student_birth_place", birthPlace);
        admissionData.put("student_DOB_No", dobNo);
        admissionData.put("student_DOB_Letter", dobInLetter);
        admissionData.put("student_gender", gender);
        admissionData.put("student_age", sAge);
        admissionData.put("student_mother_tung", motherTung);
        admissionData.put("passed_class", sClassPassed);
        admissionData.put("passed_grad", sPassingGrade);
        admissionData.put("last_school_name", sLastSchoolName);
        admissionData.put("bank_name", bankName);
        admissionData.put("bank_IFSC_No", bankIFSC);
        admissionData.put("bank_account_no", bankNo);
        admissionData.put("parent_full_name", et_stud_parent_full_name.getText().toString());
        admissionData.put("parent_relation", et_parent_relation.getText().toString());
        admissionData.put("stud_parent_cast", et_stud_parent_cast.getText().toString());
        admissionData.put("parent_temp_address", et_parent_temp_address.getText().toString());
        admissionData.put("parent_perm_address", et_parent_perm_address.getText().toString());
        admissionData.put("parent_telephone_no", et_parent_telephone_no.getText().toString());
        admissionData.put("parent_mo_no", et_parent_mo_no.getText().toString());
        admissionData.put("parent_business", et_parent_business.getText().toString());
        admissionData.put("parent_last_income", et_parent_last_income.getText().toString());
        admissionData.put("minority_caste", minority_caste);
        admissionData.put("have_minority", selectedMinority);
        admissionData.put("economical_weaker", selected_economical_weaker);
        if (imageUrl != null) {
            admissionData.put("imageUrl", imageUrl); // Add the image URL to the admission data
        }

        saveAdmissionData(admissionData);
    }

    private void saveAdmissionData(HashMap<String, Object> admissionData) {
        databaseReference.child(admissionNo).setValue(admissionData)
                .addOnSuccessListener(aVoid -> {

                    progressDialog2.show();
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                    progressDialog2.dismiss(); // Dismiss the progress dialog
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                    progressDialog2.dismiss(); // Dismiss the progress dialog even on failure
                });
    }
}
