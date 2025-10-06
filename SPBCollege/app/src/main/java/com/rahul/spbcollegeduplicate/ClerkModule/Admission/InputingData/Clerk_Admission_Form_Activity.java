package com.rahul.spbcollegeduplicate.ClerkModule.Admission.InputingData;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rahul.spbcollegeduplicate.R;

public class Clerk_Admission_Form_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    LinearLayout fragememt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_admission_form);
        setTitle("Admission Form");
       /* tabLayout=findViewById(R.id._tablayout_acdf);
        viewPager=findViewById(R.id.viewpager_acdf);
        viewPageAdapater= new ViewPagerAdapterForAdmissionForm(getSupportFragmentManager());
        viewPageAdapater.addFragment(new Clerk_Admission_Student_Info_Fragment(),"Student Info");
        viewPageAdapater.addFragment(new Clerk_Admission_Parent_Info_Fragment(),"Parent Info");

        viewPager.setAdapter(viewPageAdapater);
        tabLayout.setupWithViewPager(viewPager);
*/
        // Create an instance of the fragment
        Clerk_Admission_Student_Info_Fragment exampleFragment = new Clerk_Admission_Student_Info_Fragment();

        // Get FragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the fragment to the 'fragment_container' FrameLayout
        fragmentTransaction.add(R.id.admissionLayout_fragement, exampleFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }
}