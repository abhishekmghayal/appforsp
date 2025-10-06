package com.rahul.spbcollegeduplicate.ClerkModule.Admission.InputingData;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rahul.spbcollegeduplicate.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Clerk_Admission_Student_Info_Fragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1999;

    EditText et_stud_add_class, et_stud_add_no, et_stud_add_date, et_stud_id, et_stud_UDI, et_stud_full_name, et_stud_mother_full_name,
            et_stud_cast, et_stud_religion, et_stud_birth_place, et_stud_DOB_no, et_stud_DOB_latter, et_stud_age, et_stud_mother_tung,
            et_stud_class_passed, et_stud_pass_grade, et_stud_last_school_name, et_stud_bank_name, et_stud_bank_IFSC, et_stud_bank_acco_no;
    RadioGroup radioGroup;
    RadioButton rb_male, rb_female;
    Button btn_submit;
    String selectedGender;
    ImageView student_photo;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clerk__admission__student__info_, container, false);


        et_stud_add_class = view.findViewById(R.id.et__aS_admission_class);
        et_stud_add_no = view.findViewById(R.id.et__aS_admission_no);
        et_stud_add_date = view.findViewById(R.id.et__aS_admission_date);
        et_stud_id = view.findViewById(R.id.et__aS_admission_id);
        et_stud_UDI = view.findViewById(R.id.et__aS_admission_AdharNO);
        et_stud_full_name = view.findViewById(R.id.et__aS_admission_sname);
        et_stud_mother_full_name = view.findViewById(R.id.et__aS_admission_MotherName);
        et_stud_cast = view.findViewById(R.id.et__aS_admission_cast);
        et_stud_religion = view.findViewById(R.id.et__aS_admission_religon);
        et_stud_birth_place = view.findViewById(R.id.et__aS_admission_BirthPlace);
        et_stud_DOB_no = view.findViewById(R.id.et__aS_admission_DOBno);
        et_stud_DOB_latter = view.findViewById(R.id.et__aS_admission_DOBinletter);
        et_stud_age = view.findViewById(R.id.et__aS_admission_sAge);
        et_stud_mother_tung = view.findViewById(R.id.et__aS_admission_mothertung);
        et_stud_class_passed = view.findViewById(R.id.et__aS_admission_SclassPassed);
        et_stud_pass_grade = view.findViewById(R.id.et__aS_admission_SPassingGrade);
        et_stud_last_school_name = view.findViewById(R.id.et__aS_admission_SlastSchoolname);
        et_stud_bank_name = view.findViewById(R.id.et__aS_admission_bankname);
        et_stud_bank_IFSC = view.findViewById(R.id.et__aS_admission_bankIFsC);
        et_stud_bank_acco_no = view.findViewById(R.id.et__aS_admission_bankNo);
        rb_male = view.findViewById(R.id.radioButtonMale);
        rb_female = view.findViewById(R.id.radioButtonFemale);

        student_photo = view.findViewById(R.id.student_photo);

        btn_submit = view.findViewById(R.id.btn_submit_admission);
        radioGroup = view.findViewById(R.id.radioGroupGender);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                if (radioButton != null) {
                    selectedGender = radioButton.getText().toString();
                }
            }
        });

        student_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        et_stud_add_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    showDatePicker(et_stud_add_date);
                }
            }
        });
        et_stud_DOB_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()) {
                    showDatePicker(et_stud_DOB_no);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_stud_add_class.getText().toString().isEmpty()) {
                    et_stud_add_class.setError("Enter Admission Class");
                } else if (et_stud_add_no.getText().toString().isEmpty()) {
                    et_stud_add_no.setError("Enter Admission No");
                } else if (et_stud_add_date.getText().toString().isEmpty()) {
                    et_stud_add_date.setError("Enter Admission Date");
                } else if (et_stud_id.getText().toString().isEmpty()) {
                    et_stud_id.setError("Enter Student Id");
                } else if (et_stud_full_name.getText().toString().isEmpty()) {
                    et_stud_full_name.setError("Enter Student Full Name");
                } else if (et_stud_mother_full_name.getText().toString().isEmpty()) {
                    et_stud_mother_full_name.setError("Enter Mother Full Name");
                } else if (et_stud_cast.getText().toString().isEmpty()) {
                    et_stud_cast.setError("Enter Caste");
                } else if (et_stud_religion.getText().toString().isEmpty()) {
                    et_stud_religion.setError("Enter Religion");
                } else if (et_stud_birth_place.getText().toString().isEmpty()) {
                    et_stud_birth_place.setError("Enter Birth Place");
                } else if (et_stud_DOB_no.getText().toString().isEmpty()) {
                    et_stud_DOB_no.setError("Enter DOB");
                } else if (et_stud_DOB_latter.getText().toString().isEmpty()) {
                    et_stud_DOB_latter.setError("Enter DOB In Latter");
                } else if (et_stud_age.getText().toString().isEmpty()) {
                    et_stud_age.setError("Enter Age");
                } else if (et_stud_mother_tung.getText().toString().isEmpty()) {
                    et_stud_mother_tung.setError("Enter Mother Tung");
                } else if (et_stud_class_passed.getText().toString().isEmpty()) {
                    et_stud_class_passed.setError("Enter Passed Class");
                } else if (et_stud_pass_grade.getText().toString().isEmpty()) {
                    et_stud_pass_grade.setError("Enter Passed Grade");
                } else if (et_stud_last_school_name.getText().toString().isEmpty()) {
                    et_stud_last_school_name.setError("Enter Last School Name");
                } else if (et_stud_bank_name.getText().toString().isEmpty()) {
                    et_stud_bank_name.setError("Enter Back Name");
                } else if (et_stud_bank_IFSC.getText().toString().isEmpty()) {
                    et_stud_bank_IFSC.setError("Enter IFSC No");
                } else if (et_stud_bank_acco_no.getText().toString().isEmpty()) {
                    et_stud_bank_acco_no.setError("Enter Account NO");
                } else if (imageUri == null) {
                    // No image selected
                    showMessageDialog("Please select an image.");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("admission_class", et_stud_add_class.getText().toString());
                    bundle.putString("admission_no", et_stud_add_no.getText().toString());
                    bundle.putString("admission_date", et_stud_add_date.getText().toString());
                    bundle.putString("student_id", et_stud_id.getText().toString());
                    bundle.putString("AdharNO", et_stud_UDI.getText().toString());
                    bundle.putString("sname", et_stud_full_name.getText().toString());
                    bundle.putString("MotherName", et_stud_mother_full_name.getText().toString());
                    bundle.putString("cast", et_stud_cast.getText().toString());
                    bundle.putString("religon", et_stud_religion.getText().toString());
                    bundle.putString("BirthPlace", et_stud_birth_place.getText().toString());
                    bundle.putString("DOBno", et_stud_DOB_no.getText().toString());
                    bundle.putString("DOBinletter", et_stud_DOB_latter.getText().toString());
                    bundle.putString("sAge", et_stud_age.getText().toString());
                    bundle.putString("mothertung", et_stud_mother_tung.getText().toString());
                    bundle.putString("SclassPassed", et_stud_class_passed.getText().toString());
                    bundle.putString("SPassingGrade", et_stud_pass_grade.getText().toString());
                    bundle.putString("SlastSchoolname", et_stud_last_school_name.getText().toString());
                    bundle.putString("bankname", et_stud_bank_name.getText().toString());
                    bundle.putString("bankIFsC", et_stud_bank_IFSC.getText().toString());
                    bundle.putString("bankNo", et_stud_bank_acco_no.getText().toString());
                    bundle.putString("gender", selectedGender);
                    bundle.putParcelable("imageUri", imageUri);

                    Clerk_Admission_Parent_Info_Fragment clerkAdmissionParentInfoFragment = new Clerk_Admission_Parent_Info_Fragment();
                    clerkAdmissionParentInfoFragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.admissionLayout_fragement, clerkAdmissionParentInfoFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    private void openImageChooser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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

    private void showDatePicker(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                student_photo.setImageBitmap(photo);
                imageUri = getImageUri(requireContext(), photo);
            } else if (requestCode == GALLERY_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
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

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing
                    }
                });
        builder.create().show();
    }
}
