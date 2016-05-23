package com.example.user.medicalia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.user.medicalia.models.Doctor;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoctorProfileActivity extends AppCompatActivity {
    private static final String TAG = "DoctorProfileActivity";

    @Bind(R.id.user_profile_name) TextView _doctorName;
    @Bind(R.id.user_profile_short_bio) TextView _doctorBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        ButterKnife.bind(this);

        Doctor currentDoctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        _doctorName.setText(currentDoctor.getUserAttributes().getName());
        _doctorBio.setText(currentDoctor.getSpecialty());
    }
}
