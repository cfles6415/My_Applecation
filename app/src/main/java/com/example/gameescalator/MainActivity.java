package com.example.gameescalator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signin , Register;
    private EditText SEmail, SPassword;

    private FirebaseAuth gameescalator;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Register = (Button) findViewById(R.id.Register);
        Register.setOnClickListener(this);

        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);

        SEmail = (EditText) findViewById(R.id.SEmail);
        SPassword= (EditText) findViewById(R.id.SPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        gameescalator = FirebaseAuth.getInstance();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register:
                startActivity(new Intent(this, RegisterU.class));
                break;
            case R.id.signin:
                userLogin();
                startActivity(new Intent(this, Search.class));
                break;
        }
    }

    private void userLogin() {
        String email = SEmail.getText().toString().trim();
        String password = SPassword.getText().toString().trim();

        if (email.isEmpty()) {
            SEmail.setError("Email Required!");
            SEmail.requestFocus();
            return;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SEmail.setError("The Email Is Valid");
        }
        if (password.isEmpty()) {
            SPassword.setError("Password Required!");
            SPassword.requestFocus();
            return;
        }
        if (password.length() < 5) {
            SPassword.setError("The Password Should Be More Than 6 Characters!");
            SPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.GONE);
        gameescalator.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this,Search.class));
                } else {
                    Toast.makeText(MainActivity.this, "Faild to Login!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}