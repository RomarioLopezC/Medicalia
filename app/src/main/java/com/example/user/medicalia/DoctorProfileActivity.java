package com.example.user.medicalia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    }
}
