package com.example.gameescalator;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterU extends AppCompatActivity implements View.OnClickListener {

    private Button registeru;
    private EditText Email, Password;
    private FirebaseAuth gameescalator;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        init();

    }

    private void init(){

        gameescalator = FirebaseAuth.getInstance();

        registeru = (Button) findViewById(R.id.registeru);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registeru) {
            registeru();
        }
    }

    private void registeru() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("Email Required!");
            Email.requestFocus();
            return;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("The Email Is Valid");
        }
        if (password.isEmpty()) {
            Password.setError("Password Required!");
            Password.requestFocus();
            return;
        }
        if (password.length() < 5) {
            Password.setError("The Password Should Be More Than 6 Characters!");
            Password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        gameescalator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                 .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                                Toast.makeText(RegisterU.this , "User has been registered Successfully" ,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                        }else{

                                Toast.makeText(RegisterU.this , "Try Again!" ,Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.VISIBLE);
                        }
                      }
                   });
                  }
                }
