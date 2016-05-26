package com.example.user.medicalia;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.user.medicalia.models.Doctor;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoctorProfileActivity extends AppCompatActivity {
    private static final String TAG = "DoctorProfileActivity";

    @Bind(R.id.user_profile_name) TextView _doctorName;
    @Bind(R.id.user_profile_short_bio) TextView _doctorBio;
    @Bind(R.id.Hospital) TextView _hostpital;
    @Bind(R.id.address) TextView _address;
    @Bind(R.id.office) TextView _office;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        ButterKnife.bind(this);

        Doctor currentDoctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        _doctorName.setText(currentDoctor.getUserAttributes().getName());
        _doctorBio.setText(currentDoctor.getSpecialty());
        _hostpital.setText(currentDoctor.getHospital());
        _address.setText(currentDoctor.getAddress());
        _office.setText(currentDoctor.getOffice());
    }
}
