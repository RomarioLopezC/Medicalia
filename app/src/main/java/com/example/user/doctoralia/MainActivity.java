package com.example.user.doctoralia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.password)
    EditText edit_password;

    @Bind(R.id.email)
    EditText edit_email;

    @Bind(R.id.RegisterBtn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        registerListeners();
    }

    @OnClick(R.id.login)
    public void login() {
        Toast.makeText(this, "Iniciando Sesión...", Toast.LENGTH_SHORT).show();
    }

    public void registerListeners(){
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

}
